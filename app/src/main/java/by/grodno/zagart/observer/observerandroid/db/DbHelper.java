package by.grodno.zagart.observer.observerandroid.db;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;
import android.util.Log;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Locale;

import by.grodno.zagart.observer.observerandroid.BuildConfig;
import by.grodno.zagart.observer.observerandroid.db.annotations.Table;
import by.grodno.zagart.observer.observerandroid.db.annotations.dbInteger;
import by.grodno.zagart.observer.observerandroid.db.annotations.dbLong;
import by.grodno.zagart.observer.observerandroid.db.annotations.dbString;

/**
 * SQLiteOpenHelper/IDbOperations implementation. Provides access to
 * SQLite database features.
 *
 * @author zagart
 */
public class DbHelper extends SQLiteOpenHelper implements IDbOperations {
    private static final String EXCEPTION_IN_INIT_MSG = "Initialization failed";
    private static final String ILLEGAL_ACCESS_MESSAGE = "Underlying field is inaccessible";
    private static final String ILLEGAL_ARGUMENT_MSG = "Not an instance of the required class";
    private static final String NULL_POINTER_MSG = "Instance is null";
    private static final String SQL_TABLE_CREATE_FIELD_TEMPLATE = "%s %s";
    private static final String SQL_TABLE_CREATE_TEMPLATE = "CREATE TABLE IF NOT EXISTS %s (%s);";
    private static String TAG = DbHelper.class.getSimpleName();

    public DbHelper(
            final Context context,
            final String name,
            final int version
    ) {
        super(context, name, null, version);
    }

    @Nullable
    public static String getTableCreateQuery(final Class<?> pClass) {
        Table table = pClass.getAnnotation(Table.class);
        if (table != null) {
            StringBuilder builder = new StringBuilder();
            try {
                final String name = table.name();
                Field[] fields = pClass.getFields();
                for (int i = 0; i < fields.length; i++) {
                    final Field field = fields[i];
                    final Annotation[] annotations = field.getAnnotations();
                    String type = null;
                    for (final Annotation annotation : annotations) {
                        if (annotation instanceof dbInteger) {
                            type = ((dbInteger) (annotation)).value();
                        } else if (annotation instanceof dbLong) {
                            type = ((dbLong) (annotation)).value();
                        } else if (annotation instanceof dbString) {
                            type = ((dbString) (annotation)).value();
                        }
                    }
                    if (type == null) {
                        return null;
                    }
                    final String value = (String) field.get(null);
                    builder.append(
                            String.format(
                                    Locale.US,
                                    SQL_TABLE_CREATE_FIELD_TEMPLATE,
                                    value,
                                    type
                            )
                    );
                    if (i < fields.length - 1) {
                        builder.append(", ");
                    }
                }
                return String.format(Locale.US, SQL_TABLE_CREATE_TEMPLATE, name, builder);
            } catch (IllegalAccessException pEx) {
                if (BuildConfig.DEBUG) {
                    Log.d(TAG, ILLEGAL_ACCESS_MESSAGE);
                }
                pEx.getCause().printStackTrace();
            } catch (IllegalArgumentException pEx) {
                if (BuildConfig.DEBUG) {
                    Log.d(TAG, ILLEGAL_ARGUMENT_MSG);
                }
                pEx.getCause().printStackTrace();
            } catch (NullPointerException pEx) {
                if (BuildConfig.DEBUG) {
                    Log.d(TAG, NULL_POINTER_MSG);
                }
                pEx.getCause().printStackTrace();
            } catch (ExceptionInInitializerError pEx) {
                if (BuildConfig.DEBUG) {
                    Log.d(TAG, EXCEPTION_IN_INIT_MSG);
                }
                pEx.getCause().printStackTrace();
            } catch (Exception pEx) {
                if (BuildConfig.DEBUG) {
                    Log.d(TAG, pEx.getMessage());
                }
                return null;
            }
        }
        return null;
    }

    @Nullable
    public static String getTableName(Class<?> pClass) {
        final Table table = pClass.getAnnotation(Table.class);
        if (table != null) {
            return table.name();
        } else {
            return null;
        }
    }

    @Override
    public int bulkInsert(final Class<?> pTable, final List<ContentValues> pValues) {
        final String name = getTableName(pTable);
        if (name != null) {
            SQLiteDatabase database = getWritableDatabase();
            int count = 0;
            try {
                database.beginTransaction();
                for (ContentValues values : pValues) {
                    database.insert(name, null, values);
                    count++;
                }
                database.setTransactionSuccessful();
            } catch (Exception pE) {
                pE.printStackTrace();
            } finally {
                database.endTransaction();
            }
            return count;
        } else {
            throw new RuntimeException();
        }
    }

    @Override
    public long delete(final Class<?> pTable, final String pSql, final String... pParams) {
        SQLiteDatabase database = getWritableDatabase();
        final String name = getTableName(pTable);
        long count = 0;
        if (name != null) {
            try {
                database.beginTransaction();
                count = database.delete(name, pSql, pParams);
                database.setTransactionSuccessful();
            } catch (Exception pE) {
                pE.printStackTrace();
            }
        }
        return count;
    }

    @Override
    public long insert(final Class<?> pTable, final ContentValues pValues) {
        final String name = getTableName(pTable);
        SQLiteDatabase database = getWritableDatabase();
        if (name != null) {
            long id = 0;
            try {
                database.beginTransaction();
                id = database.insert(name, null, pValues);
                database.setTransactionSuccessful();
            } catch (Exception pE) {
                pE.printStackTrace();
            }
            return id;
        } else {
            throw new RuntimeException();
        }
    }

    @Override
    public Cursor query(final String pSql, final String... pParams) {
        final SQLiteDatabase database = getReadableDatabase();
        return database.rawQuery(pSql, pParams);
    }

    @Override
    public void onCreate(final SQLiteDatabase pDatabase) {
        for (final Class<?> clazz : Contract.MODELS) {
            final String sql = getTableCreateQuery(clazz);
            if (sql != null) {
                pDatabase.execSQL(sql);
            }
        }
    }

    @Override
    public void onUpgrade(
            final SQLiteDatabase pDatabase,
            final int pOldVersion,
            final int pNewVersion
    ) {
    }
}

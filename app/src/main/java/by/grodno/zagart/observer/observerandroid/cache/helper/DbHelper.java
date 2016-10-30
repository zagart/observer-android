package by.grodno.zagart.observer.observerandroid.cache.helper;
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
import by.grodno.zagart.observer.observerandroid.cache.model.annotations.Id;
import by.grodno.zagart.observer.observerandroid.cache.model.annotations.NotNull;
import by.grodno.zagart.observer.observerandroid.cache.model.annotations.Table;
import by.grodno.zagart.observer.observerandroid.cache.model.annotations.dbInteger;
import by.grodno.zagart.observer.observerandroid.cache.model.annotations.dbLong;
import by.grodno.zagart.observer.observerandroid.cache.model.annotations.dbString;
import by.grodno.zagart.observer.observerandroid.cache.model.contracts.Contract;
import by.grodno.zagart.observer.observerandroid.singletons.ContextHolder;

/**
 * SQLiteOpenHelper/IDbOperations implementation. Provides access to
 * DbHelper database features and executing CRUD operations.
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
    public static final String NOT_NULL = " NOT NULL";
    public static final String AUTOINCREMENT = " AUTOINCREMENT";
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_FILE_NAME = "cache.db";
    private static String TAG = DbHelper.class.getSimpleName();
    private SQLiteDatabase mDatabase;

    private DbHelper(
            final Context context,
            final String name,
            final int version
    ) {
        super(context, name, null, version);
    }

    private static String findSecondaryAnnotations(final Annotation[] pAnnotations, String pType) {
        for (final Annotation secondaryAnnotation : pAnnotations) {
            if (secondaryAnnotation instanceof Id) {
                Id id = (Id) (secondaryAnnotation);
                pType += " " + id.value();
                if (id.autoincrement()) {
                    pType += AUTOINCREMENT;
                }
            }
            if (secondaryAnnotation instanceof NotNull) {
                NotNull id = (NotNull) (secondaryAnnotation);
                if (id.value()) {
                    pType += NOT_NULL;
                }
            }
        }
        return pType;
    }

    public static DbHelper getInstance() {
        return SingletonHolder.DB_HELPER_INSTANCE;
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
                    String type = "";
                    for (final Annotation annotation : annotations) {
                        if (annotation instanceof dbInteger) {
                            type += ((dbInteger) (annotation)).value();
                            type = findSecondaryAnnotations(annotations, type);
                        } else if (annotation instanceof dbLong) {
                            type += ((dbLong) (annotation)).value();
                            type = findSecondaryAnnotations(annotations, type);
                        } else if (annotation instanceof dbString) {
                            type += ((dbString) (annotation)).value();
                            type = findSecondaryAnnotations(annotations, type);
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
                String template = String.format(Locale.US, SQL_TABLE_CREATE_TEMPLATE, name, builder);
                if (BuildConfig.DEBUG) {
                    Log.d(TAG, template);
                }
                return template;
            } catch (IllegalAccessException pEx) {
                if (BuildConfig.DEBUG) {
                    Log.e(TAG, ILLEGAL_ACCESS_MESSAGE);
                }
                pEx.getCause().printStackTrace();
            } catch (IllegalArgumentException pEx) {
                if (BuildConfig.DEBUG) {
                    Log.e(TAG, ILLEGAL_ARGUMENT_MSG);
                }
                pEx.getCause().printStackTrace();
            } catch (NullPointerException pEx) {
                if (BuildConfig.DEBUG) {
                    Log.e(TAG, NULL_POINTER_MSG);
                }
                pEx.getCause().printStackTrace();
            } catch (ExceptionInInitializerError pEx) {
                if (BuildConfig.DEBUG) {
                    Log.e(TAG, EXCEPTION_IN_INIT_MSG);
                }
                pEx.getCause().printStackTrace();
            } catch (Exception pEx) {
                if (BuildConfig.DEBUG) {
                    Log.e(TAG, pEx.getMessage());
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
                database.close();
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
            } finally {
                database.endTransaction();
                database.close();
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
            } finally {
                database.endTransaction();
                database.close();
            }
            return id;
        } else {
            throw new RuntimeException();
        }
    }

    @Override
    public Cursor query(final String pSql, final String... pParams) {
        mDatabase = getReadableDatabase();
        return mDatabase.rawQuery(pSql, pParams);
    }

    /**
     * Method for manual closing database connection to avoid memory leak. Required when
     * finished work with cursor that returns {@link #query(String, String...)} method.
     * The rest methods close connection automatically.
     *
     * @see DbHelper#query(String, String...)
     */
    public void closeDatabase() {
        if (mDatabase.isOpen()) {
            mDatabase.close();
        }
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

    public static class SingletonHolder {
        public static final DbHelper DB_HELPER_INSTANCE = new DbHelper(
                ContextHolder.get(),
                DATABASE_FILE_NAME,
                DATABASE_VERSION
        );
    }
}

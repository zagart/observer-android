package observer.zagart.by.client.repository.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Locale;

import observer.zagart.by.client.App;
import observer.zagart.by.client.BuildConfig;
import observer.zagart.by.client.constants.Constants;
import observer.zagart.by.client.repository.entities.annotations.Table;
import observer.zagart.by.client.repository.entities.annotations.dbId;
import observer.zagart.by.client.repository.entities.annotations.dbInteger;
import observer.zagart.by.client.repository.entities.annotations.dbNotNull;
import observer.zagart.by.client.repository.entities.annotations.dbString;
import observer.zagart.by.client.repository.entities.contracts.Contracts;

/**
 * SQLiteOpenHelper/IDbOperations implementation. Provides access to
 * DbHelper database features and executing CRUD operations.
 *
 * @author zagart
 */
public class DbHelper extends SQLiteOpenHelper implements IDbOperations {

    private static final String NOT_NULL = " NOT NULL";
    private static final String AUTOINCREMENT = " AUTOINCREMENT";
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_FILE_NAME = "observer.db";
    private static final String SQL_TABLE_CREATE_FIELD_TEMPLATE = "%s %s";
    private static final String SQL_TABLE_CREATE_TEMPLATE = "CREATE TABLE IF NOT EXISTS %s (%s);";
    private static String TAG = DbHelper.class.getSimpleName();

    public DbHelper(final Context pContext) {
        super(pContext, DATABASE_FILE_NAME, null, DATABASE_VERSION);
    }

    private static String findSecondaryAnnotations(final Annotation[] pAnnotations, String pType) {
        for (final Annotation secondaryAnnotation : pAnnotations) {
            if (secondaryAnnotation instanceof dbId) {
                dbId id = (dbId) (secondaryAnnotation);
                pType += " " + id.value();
                if (id.autoincrement()) {
                    pType += AUTOINCREMENT;
                }
            }
            if (secondaryAnnotation instanceof dbNotNull) {
                dbNotNull id = (dbNotNull) (secondaryAnnotation);
                if (id.value()) {
                    pType += NOT_NULL;
                }
            }
        }
        return pType;
    }

    @SuppressWarnings("unused")
    public static DbHelper getInstance() {
        return SingletonHolder.DB_HELPER_INSTANCE;
    }

    @Nullable
    private static String getTableCreateQuery(final Class<?> pClass) {
        Table table = pClass.getAnnotation(Table.class);
        if (table != null) {
            StringBuilder builder = new StringBuilder();
            try {
                final String name = table.name();
                Field[] fields = pClass.getFields();
                for (int i = 0, hits = 0; i < fields.length; i++) {
                    final Field field = fields[i];
                    final Annotation[] annotations = field.getAnnotations();
                    String type = "";
                    for (final Annotation annotation : annotations) {
                        if (annotation instanceof dbInteger) {
                            type += ((dbInteger) (annotation)).value();
                            type = findSecondaryAnnotations(annotations, type);
                        } else if (annotation instanceof dbString) {
                            type += ((dbString) (annotation)).value();
                            type = findSecondaryAnnotations(annotations, type);
                        }
                    }
                    if (!TextUtils.isEmpty(type)) {
                        if (hits > 0) {
                            builder.append(", ");
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
                        hits++;
                    }
                }
                String template = String.format(
                        Locale.US,
                        SQL_TABLE_CREATE_TEMPLATE,
                        name,
                        builder
                );
                if (BuildConfig.DEBUG) {
                    Log.d(TAG, template);
                }
                return template;
            } catch (
                    NullPointerException |
                            ExceptionInInitializerError |
                            IllegalAccessException |
                            IllegalArgumentException pEx
                    ) {
                if (BuildConfig.DEBUG) {
                    Log.e(TAG, pEx.getMessage(), pEx);
                }
                return null;
            }
        }
        return null;
    }

    @Nullable
    private static String getTableName(Class<?> pClass) {
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
            } catch (Exception pEx) {
                throw new RuntimeException(Constants.TRANSACTION_FAILED);
            } finally {
                database.endTransaction();
                database.close();
            }
            return count;
        } else {
            throw new RuntimeException(Constants.INCORRECT_TABLE_NAME);
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
            } catch (Exception pEx) {
                pEx.printStackTrace();
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
        return getReadableDatabase().rawQuery(pSql, pParams);
    }

    @Override
    public void onCreate(final SQLiteDatabase pDatabase) {
        for (final Class<?> clazz : Contracts.MODELS) {
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

    private static class SingletonHolder {

        private static final DbHelper DB_HELPER_INSTANCE = new DbHelper(
                App.getState().getContext()
        );
    }
}

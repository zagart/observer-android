package by.grodno.zagart.observer.observerandroid.db;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Locale;

import by.grodno.zagart.observer.observerandroid.db.annotations.Table;
import by.grodno.zagart.observer.observerandroid.db.annotations.dbInteger;
import by.grodno.zagart.observer.observerandroid.db.annotations.dbLong;
import by.grodno.zagart.observer.observerandroid.db.annotations.dbString;

/**
 * SQLiteOpenHelper implementation.
 *
 * @author zagart
 */
public class DbHelper extends SQLiteOpenHelper implements IDbOperations {
    public static final String SQL_TABLE_CREATE = "CREATE TABLE IF NOT EXISTS %s (%s)";
    public static final String SQL_FIELD_TEMPLATE = "%s %s";

    public DbHelper(
            final Context context,
            final String name,
            final SQLiteDatabase.CursorFactory factory,
            final int version
    ) {
        super(context, name, factory, version);
    }

    @Nullable
    public static String getTableCreateQuery(final Class<?> clazz) {
        Table table = clazz.getAnnotation(Table.class);
        if (table != null) {
            try {
                final String name = table.name();
                StringBuilder builder = new StringBuilder();
                Field[] fields = clazz.getFields();
                for (int i = 0, fieldsLength = fields.length; i < fieldsLength; i++) {
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
                    builder.append(String.format(Locale.US, SQL_FIELD_TEMPLATE, value, type));
                    if (i < fieldsLength - 1) {
                        builder.append(",");
                    }
                    return builder.append(String.format(Locale.US, SQL_TABLE_CREATE, name, "")).toString();
                }
            } catch (SecurityException pEx) {
                pEx.printStackTrace();
            } catch (IllegalAccessException pEx) {
                pEx.printStackTrace();
            }
        } else {
        }
        return null;
    }

    public static String getTableName() {
        return null;
    }

    @Override
    public int bulkInsert(final Class<?> pTable, final List<ContentValues> pValues) {
        SQLiteDatabase database = getWritableDatabase();
        final String name = getTableName();
        int count = 0;
        if (name != null) {
            try {
                database.beginTransaction();
                for (ContentValues values : pValues) {
                    database.insert(name, null, values);
                    count++;
                }
                database.setTransactionSuccessful();
            } catch (Exception pE) {
                pE.printStackTrace();
            }
        }
        return count;
    }

    @Override
    public long delete(final Class<?> pTable, final String pSql, final String... pParams) {
        SQLiteDatabase database = getWritableDatabase();
        final String name = getTableName();
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
        SQLiteDatabase database = getWritableDatabase();
        final String name = getTableName();
        long id = 0;
        if (name != null) {
            try {
                database.beginTransaction();
                id = database.insert(name, null, pValues);
                database.setTransactionSuccessful();
            } catch (Exception pE) {
                pE.printStackTrace();
            }
        }
        return id;
    }

    @Override
    public Cursor query(final String pSql, final String... pParams) {
        final SQLiteDatabase database = getReadableDatabase();
        return database.rawQuery(pSql, pParams);
    }

    @Override
    public void onCreate(final SQLiteDatabase db) {
        for (final Class<?> clazz : Contract.MODELS) {
            final String sql = getTableCreateQuery(clazz);
            if (sql != null) {
                db.execSQL(sql);
            }
        }
    }

    @Override
    public void onUpgrade(final SQLiteDatabase db, final int oldVersion, final int newVersion) {
    }
}

package observer.zagart.by.client.application.managers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import observer.zagart.by.client.BuildConfig;
import observer.zagart.by.client.application.constants.DatabaseConstants;
import observer.zagart.by.client.application.interfaces.IDatabaseOperations;
import observer.zagart.by.client.application.utils.ReflectionUtil;
import observer.zagart.by.client.mvp.models.repository.contracts.Contracts;

/**
 * SQLiteOpenHelper/IDatabaseOperations implementation. Provides access to
 * DatabaseManager database features and executing CRUD operations.
 *
 * @author zagart
 */
public class DatabaseManager extends SQLiteOpenHelper implements IDatabaseOperations {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_FILE_NAME = "observer.db";
    private static String TAG = DatabaseManager.class.getSimpleName();

    public DatabaseManager(final Context pContext) {
        super(pContext, DATABASE_FILE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public int bulkInsert(final Class<?> pTable, final ContentValues[] pValues) {
        final String name = ReflectionUtil.getTableName(pTable);
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
                Log.e(TAG, DatabaseConstants.TRANSACTION_FAILED, pEx);
            } finally {
                database.endTransaction();
            }
            return count;
        } else {
            throw new IllegalStateException(DatabaseConstants.INCORRECT_TABLE_NAME);
        }
    }

    @Override
    public long delete(final Class<?> pTable, final String pSql, final String... pParams) {
        final SQLiteDatabase database = getWritableDatabase();
        final String name = ReflectionUtil.getTableName(pTable);
        long count = 0;
        if (name != null) {
            try {
                database.beginTransaction();
                count = database.delete(name, pSql, pParams);
                database.setTransactionSuccessful();
            } catch (Exception pEx) {
                if (BuildConfig.DEBUG) {
                    Log.e(TAG, DatabaseConstants.DELETE_FAILED, pEx);
                }
            } finally {
                database.endTransaction();
            }
        }
        return count;
    }

    @Override
    public long insert(final Class<?> pTable, final ContentValues pValues) {
        final String name = ReflectionUtil.getTableName(pTable);
        final SQLiteDatabase database = getWritableDatabase();
        if (name != null) {
            long id = 0;
            try {
                database.beginTransaction();
                id = database.insert(name, null, pValues);
                database.setTransactionSuccessful();
            } catch (Exception pEx) {
                if (BuildConfig.DEBUG) {
                    Log.e(TAG, DatabaseConstants.INSERT_FAILED, pEx);
                }
            } finally {
                database.endTransaction();
            }
            return id;
        } else {
            throw new IllegalStateException(DatabaseConstants.TABLE_NAME_NULL_EXCEPTION);
        }
    }

    @Override
    public Cursor query(final String pSql, final String... pParams) {
        final SQLiteDatabase database = getReadableDatabase();
        return database.rawQuery(pSql, pParams);
    }

    @Override
    public void onCreate(final SQLiteDatabase pDatabase) {
        for (final Class<?> clazz : Contracts.MODELS) {
            final String sql = ReflectionUtil.getTableCreateQuery(clazz);
            if (sql != null) {
                pDatabase.execSQL(sql);
            }
        }
    }

    @Override
    public void onUpgrade(
            final SQLiteDatabase pDatabase,
            final int pOldVersion,
            final int pNewVersion) {
    }
}

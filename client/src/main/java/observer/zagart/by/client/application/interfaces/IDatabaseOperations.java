package observer.zagart.by.client.application.interfaces;

import android.content.ContentValues;
import android.database.Cursor;

/**
 * Interface that describes operations available for executing on
 * DatabaseManager database.
 *
 * @author zagart
 */
public interface IDatabaseOperations {

    int bulkInsert(Class<?> pTable, ContentValues[] pValues);

    long delete(Class<?> pTable, String pSql, String... pParams);

    long insert(Class<?> pTable, ContentValues pValues);

    Cursor query(String pSql, String... pParams);
}

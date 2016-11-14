package observer.zagart.by.client.repository.helper;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.List;

/**
 * Interface that describes operations available for executing on
 * DbHelper database.
 *
 * @author zagart
 */
public interface IDbOperations {

    int bulkInsert(Class<?> pTable, List<ContentValues> pValues);

    long delete(Class<?> pTable, String pSql, String... pParams);

    long insert(Class<?> pTable, ContentValues pValues);

    Cursor query(String pSql, String... pParams);
}

package by.grodno.zagart.observer.observerandroid.db;
import android.content.ContentValues;
import android.database.Cursor;

import java.util.List;

/**
 * @author zagart
 */
public interface IDbOperations {
    int bulkInsert(Class<?> pTable, List<ContentValues> pValues);
    long delete(Class<?> pTable, String pSql, String... pParams);
    long insert(Class<?> pTable, ContentValues pValues);
    Cursor query(String pSql, String... pParams);
}

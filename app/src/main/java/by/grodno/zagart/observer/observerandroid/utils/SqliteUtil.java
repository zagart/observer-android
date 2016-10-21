package by.grodno.zagart.observer.observerandroid.utils;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import by.grodno.zagart.observer.observerandroid.cache.FeedReaderContract;
import by.grodno.zagart.observer.observerandroid.cache.FeedReaderDbHelper;
import by.grodno.zagart.observer.observerandroid.singletons.ContextHolder;

/**
 * Temporary util class for SQLite layer.
 */
public class SqliteUtil {
    public static final String NEW_TITLE = "New title";
    public static final String TITLE = "My best title";
    public static final String CONTENT = "My best content";
    private static SQLiteOpenHelper mDbHelper = new FeedReaderDbHelper(ContextHolder.get());
    private static SQLiteDatabase mSqLiteDb;

    public static void persistRow(String title, String content) {
        mSqLiteDb = mDbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_TITLE, title);
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_CONTENT, content);
        long newRowId = -1;
        try {
            newRowId = mSqLiteDb.insert(
                    FeedReaderContract.FeedEntry.TABLE_NAME,
                    FeedReaderContract.FeedEntry.COLUMN_NAME_NULLABLE,
                    values);
        } catch (Exception ex) {
            Toast.makeText(
                    ContextHolder.get(),
                    ex.getMessage(),
                    Toast.LENGTH_LONG
            );
        }
        Toast.makeText(
                ContextHolder.get(),
                String.format("Insertion ID = %d", newRowId),
                Toast.LENGTH_LONG
        ).show();
    }

    public static void retrieveData() {
        mSqLiteDb = mDbHelper.getReadableDatabase();
        String[] projection = {
                FeedReaderContract.FeedEntry._ID,
                FeedReaderContract.FeedEntry.COLUMN_NAME_TITLE,
        };
        String selection = FeedReaderContract.FeedEntry.COLUMN_NAME_TITLE + " = ?";
        String[] selectionArgs = {TITLE};
        String sortOrder =
                FeedReaderContract.FeedEntry.COLUMN_NAME_TITLE + " DESC";
        Cursor c = mSqLiteDb.query(
                FeedReaderContract.FeedEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder
        );
        c.moveToLast();
        long itemId = c.getLong(c.getColumnIndexOrThrow(FeedReaderContract.FeedEntry._ID));
        Toast.makeText(
                ContextHolder.get(),
                String.format("Last ID is %d", itemId),
                Toast.LENGTH_LONG
        ).show();
    }

    public static void updateData() {
        mSqLiteDb = mDbHelper.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_TITLE, NEW_TITLE);
        String selection = FeedReaderContract.FeedEntry.COLUMN_NAME_TITLE + " LIKE ?";
        String[] selectionArgs = {TITLE};
        int count = mSqLiteDb.update(
                FeedReaderContract.FeedEntry.TABLE_NAME,
                values,
                selection,
                selectionArgs);
        Toast.makeText(
                ContextHolder.get(),
                String.format("Updated %d elements", count),
                Toast.LENGTH_LONG
        ).show();
    }
}

package by.grodno.zagart.observer.observerandroid.activities;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import by.grodno.zagart.observer.observerandroid.R;
import by.grodno.zagart.observer.observerandroid.cache.FeedReaderContract;
import by.grodno.zagart.observer.observerandroid.cache.FeedReaderDbHelper;

/**
 * SQLite test activity.
 */
public class GuestActivity extends AppCompatActivity {

    public static final String NEW_TITLE = "New title";
    public static final String TITLE = "My best title";
    public static final String CONTENT = "My best content";
    private SQLiteOpenHelper mDbHelper = new FeedReaderDbHelper(this);
    private SQLiteDatabase mSqLiteDb;

    private void deleteData() {
        mSqLiteDb = mDbHelper.getWritableDatabase();
        mSqLiteDb.delete(FeedReaderContract.FeedEntry.TABLE_NAME, null, null);
    }

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.guest_activity);
    }

    public void onCreateClick(View view) {
        mDbHelper = new FeedReaderDbHelper(this);
        persistRow(TITLE, CONTENT);
        mDbHelper.close();
    }

    public void onDeleteClick(View view) {
        mDbHelper = new FeedReaderDbHelper(this);
        deleteData();
        mDbHelper.close();
    }

    public void onRetrieveClick(View view) {
        mDbHelper = new FeedReaderDbHelper(this);
        retrieveData();
        mDbHelper.close();
    }

    public void onUpdateClick(View view) {
        mDbHelper = new FeedReaderDbHelper(this);
        updateData();
        mDbHelper.close();
    }

    private void persistRow(String title, String content) {
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
                    this,
                    ex.getMessage(),
                    Toast.LENGTH_LONG
            );
        }
        Toast.makeText(
                this,
                String.format("Insertion ID = %d", newRowId),
                Toast.LENGTH_LONG
        ).show();
    }

    private void retrieveData() {
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
                this,
                String.format("Last ID is %d", itemId),
                Toast.LENGTH_LONG
        ).show();
    }

    private void updateData() {
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
                this,
                String.format("Updated %d elements", count),
                Toast.LENGTH_LONG
        ).show();
    }
}

package by.grodno.zagart.observer.observerandroid.cache;
import android.provider.BaseColumns;

/**
 * SQLite test class
 */
public final class FeedReaderContract {

    private FeedReaderContract() {
    }

    public static class FeedEntry implements BaseColumns {

        public static final String TABLE_NAME = "entry";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_CONTENT = "content";
        public static final String COLUMN_NAME_NULLABLE = "NULL";
    }
}

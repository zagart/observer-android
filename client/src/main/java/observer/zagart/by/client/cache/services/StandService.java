package observer.zagart.by.client.cache.services;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import observer.zagart.by.client.cache.helper.DbHelper;
import observer.zagart.by.client.cache.model.Stand;

/**
 * Service class for processing database data related to Stand model.
 *
 * @author zagart
 */
public class StandService {
    private static final String SELECT_ALL_STANDS = "SELECT * FROM STAND;";
    private static DbHelper sDbHelper;

    public static List<Stand> selectAllStands() {
        List<Stand> stands = new ArrayList<>();
        sDbHelper = DbHelper.getInstance();
        Cursor cursor = sDbHelper.query(SELECT_ALL_STANDS);
        cursor.moveToFirst();
        while (!cursor.isLast()) {
            stands.add(Stand.parseCursorRow(cursor));
            cursor.moveToNext();
        }
        return stands;
    }
}

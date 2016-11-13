package observer.zagart.by.client.repository;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import observer.zagart.by.client.repository.helper.DbHelper;
import observer.zagart.by.client.repository.model.Module;
import observer.zagart.by.client.repository.model.Stand;
import observer.zagart.by.client.repository.model.contracts.StandContract;

/**
 * Service class for processing database data related to Stand model.
 *
 * @author zagart
 */
public class Service {
    private static final String SELECT_ALL_MODULES = "SELECT * FROM MODULE;";
    private static final String SELECT_ALL_STANDS = "SELECT * FROM STAND;";
    private static DbHelper sDbHelper;

    public static List<Module> selectAllModules() {
        List<Module> modules = new ArrayList<>();
        sDbHelper = DbHelper.getInstance();
        Cursor cursor = sDbHelper.query(SELECT_ALL_MODULES);
        cursor.moveToFirst();
        while (!cursor.isLast()) {
            modules.add(Module.parseCursorRow(cursor));
            cursor.moveToNext();
        }
        sDbHelper.close();
        return modules;
    }

    public static List<Stand> selectAllStands() {
        List<Stand> stands = new ArrayList<>();
        sDbHelper = DbHelper.getInstance();
        Cursor cursor = sDbHelper.query(SELECT_ALL_STANDS);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            stands.add(Stand.parseCursorRow(cursor));
            cursor.moveToNext();
        }
        sDbHelper.close();
        return stands;
    }

    public static void synchronizeStands(List<Stand> pStands) {
        sDbHelper.delete(StandContract.class, null);
        //TODO correct synchronization
        for (Stand stand : pStands) {
            sDbHelper.insert(StandContract.class, stand.convert());
        }
        sDbHelper.close();
    }
}

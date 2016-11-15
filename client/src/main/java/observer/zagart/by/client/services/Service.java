package observer.zagart.by.client.services;

import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import observer.zagart.by.client.App;
import observer.zagart.by.client.repository.helper.DbHelper;
import observer.zagart.by.client.repository.entities.Module;
import observer.zagart.by.client.repository.entities.Stand;
import observer.zagart.by.client.repository.entities.contracts.StandContract;

import static observer.zagart.by.client.constants.Constants.SELECT_ALL_MODULES;
import static observer.zagart.by.client.constants.Constants.SELECT_ALL_STANDS;
import static observer.zagart.by.client.constants.Constants.SELECT_FROM_STAND_WHERE_ID;

/**
 * Service class for processing database data related to Stand model.
 *
 * @author zagart
 */
public class Service {

    private static DbHelper sDbHelper;

    static {
        sDbHelper = App.getState().getDbHelper();
    }

    public static void clearCachedStands() {
        sDbHelper.delete(StandContract.class, null);
    }

    private static boolean isStandCached(Stand pStand) {
        final Cursor query = sDbHelper.query(
                SELECT_FROM_STAND_WHERE_ID,
                pStand.getId().toString()
        );
        return query.getCount() > 0;
    }

    public static List<Module> selectAllModules() {
        List<Module> modules = new ArrayList<>();
        Cursor cursor = sDbHelper.query(SELECT_ALL_MODULES);
        if (cursor.moveToFirst()) {
            while (!cursor.isLast()) {
                modules.add(Module.parseCursorRow(cursor));
                cursor.moveToNext();
            }
        }
        sDbHelper.close();
        return modules;
    }

    public static List<Stand> selectAllStands() {
        List<Stand> stands = new ArrayList<>();
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
        for (Stand stand : pStands) {
            if (!isStandCached(stand)) {
                sDbHelper.insert(StandContract.class, stand.convert());
            }
        }
        sDbHelper.close();
    }
}

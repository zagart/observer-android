package observer.zagart.by.client.mvp.models;

import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import observer.zagart.by.client.App;
import observer.zagart.by.client.constants.Constants;
import observer.zagart.by.client.mvp.MVP;
import observer.zagart.by.client.repository.entities.Stand;
import observer.zagart.by.client.repository.entities.contracts.StandContract;
import observer.zagart.by.client.repository.helper.DbHelper;

/**
 * MVP model implementation for stand.
 *
 * @author zagart
 */

public class StandModel implements MVP.IModelOperations<Stand> {

    private MVP.IPresenterOperations mPresenter;
    private DbHelper mHelper;

    public StandModel(final MVP.IPresenterOperations pPresenter) {
        mPresenter = pPresenter;
        mHelper = App.getState().getDbHelper();
    }

    @Override
    public List<Stand> retrieveAll() {
        List<Stand> stands = new ArrayList<>();
        Cursor cursor = mHelper.query(Constants.SELECT_ALL_STANDS);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            stands.add(Stand.parseCursorRow(cursor));
            cursor.moveToNext();
        }
        cursor.close();
        mHelper.close();
        return stands;
    }

    @Override
    public void persistAll(final List<Stand> pStands) {
        for (Stand stand : pStands) {
            if (!isStandCached(stand)) {
                mHelper.insert(StandContract.class, stand.convert());
            }
        }
        mHelper.close();
    }

    @Override
    public void deleteAll() {
        mHelper.delete(StandContract.class, null);
    }

    private boolean isStandCached(final Stand pStand) {
        final Cursor cursor = mHelper.query(
                Constants.SELECT_FROM_STAND_WHERE_ID,
                pStand.getId().toString()
        );
        final boolean cached = cursor.getCount() > 0;
        cursor.close();
        return cached;
    }
}

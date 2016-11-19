package observer.zagart.by.client.mvp.models;

import android.content.ContentResolver;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import observer.zagart.by.client.App;
import observer.zagart.by.client.constants.Constants;
import observer.zagart.by.client.mvp.MVP;
import observer.zagart.by.client.repository.entities.Stand;
import observer.zagart.by.client.utils.URIUtil;

/**
 * MVP model implementation for stand.
 *
 * @author zagart
 */

public class StandModel implements MVP.IModelOperations<Stand> {

    private MVP.IPresenterOperations mPresenter;

    public StandModel(final MVP.IPresenterOperations pPresenter) {
        mPresenter = pPresenter;
    }

    @Override
    public List<Stand> retrieveAll() {
        final ContentResolver resolver = App.getState().getContext().getContentResolver();
        final List<Stand> stands = new ArrayList<>();
        final Cursor cursor = resolver.query(
                URIUtil.getStandUri(),
                null,
                Constants.SELECT_ALL_STANDS,
                null,
                null);
        if (cursor != null && cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                stands.add(Stand.parseCursorRow(cursor));
                cursor.moveToNext();
            }
            cursor.close();
        }
        return stands;
    }

    @Override
    public void persistAll(final List<Stand> pStands) {
        for (Stand stand : pStands) {
            if (!isStandCached(stand)) {
                final ContentResolver resolver = App.getState().getContext().getContentResolver();
                resolver.insert(URIUtil.getStandUri(), stand.convert());
            }
        }
    }

    @Override
    public void deleteAll() {
        final ContentResolver resolver = App.getState().getContext().getContentResolver();
        resolver.delete(URIUtil.getStandUri(), null, null);
    }

    private boolean isStandCached(final Stand pStand) {
        final ContentResolver resolver = App.getState().getContext().getContentResolver();
        final String[] args = {pStand.getId().toString()};
        final Cursor cursor = resolver.query(
                URIUtil.getStandUri(),
                null,
                Constants.SELECT_FROM_STAND_WHERE_ID,
                args,
                null);
        if (cursor == null) {
            return false;
        } else {
            final boolean cached = cursor.getCount() > 0;
            cursor.close();
            return cached;
        }
    }
}

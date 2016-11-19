package observer.zagart.by.client.mvp.models.repository;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import observer.zagart.by.client.App;
import observer.zagart.by.client.application.constants.Constants;
import observer.zagart.by.client.application.managers.DatabaseManager;
import observer.zagart.by.client.application.utils.URIUtil;
import observer.zagart.by.client.mvp.models.repository.contracts.ModuleContract;
import observer.zagart.by.client.mvp.models.repository.contracts.StandContract;

/**
 * Implementation of {@link ContentProvider}.
 *
 * @author zagart
 */

public class ObserverContentProvider extends ContentProvider {

    private DatabaseManager mHelper;

    @Override
    public boolean onCreate() {
        mHelper = new DatabaseManager(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(
            @NonNull final Uri pUri,
            final String[] pProjection,
            final String pSelection,
            final String[] pSelectionArgs,
            final String pSortOrder) {
        return mHelper.query(pSelection, pSelectionArgs);
    }

    @Nullable
    @Override
    public String getType(@NonNull final Uri pUri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull final Uri pUri, final ContentValues pContentValues) {
        final Class<?> table = getTable(pUri);
        final long id = mHelper.insert(table, pContentValues);
        final Uri newUri = URIUtil.addId(pUri, id);
        onDataChanged(newUri);
        return newUri;
    }

    @Override
    public int delete(
            @NonNull final Uri pUri,
            final String pSelection,
            final String[] pSelectionArgs) {
        final int id = (int) mHelper.delete(getTable(pUri), pSelection, pSelectionArgs);
        onDataChanged(pUri);
        return id;
    }

    @Override
    public int bulkInsert(@NonNull final Uri pUri, @NonNull final ContentValues[] pValues) {
        final int affected = mHelper.bulkInsert(getTable(pUri), pValues);
        onDataChanged(pUri);
        return affected;
    }

    @Override
    public int update(
            @NonNull final Uri pUri,
            final ContentValues pContentValues,
            final String pSelection,
            final String[] pSelectionArgs) {
        return 0;
    }

    private void onDataChanged(Uri pUri) {
        App.getContext().getContentResolver().notifyChange(pUri, null);
    }

    private Class<?> getTable(Uri pUri) {
        final String table = URIUtil.getClearUriPath(pUri);
        switch (table) {
            case Constants.MODULE:
                return ModuleContract.class;
            case Constants.STAND:
                return StandContract.class;
            default:
                return null;
        }
    }
}

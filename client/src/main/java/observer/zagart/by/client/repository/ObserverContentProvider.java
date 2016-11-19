package observer.zagart.by.client.repository;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import observer.zagart.by.client.App;
import observer.zagart.by.client.constants.Constants;
import observer.zagart.by.client.repository.entities.contracts.ModuleContract;
import observer.zagart.by.client.repository.entities.contracts.StandContract;
import observer.zagart.by.client.repository.helper.DbHelper;
import observer.zagart.by.client.utils.URIUtil;

/**
 * Implementation of {@link ContentProvider}.
 *
 * @author zagart
 */

public class ObserverContentProvider extends ContentProvider {

    private DbHelper mHelper;

    @Override
    public boolean onCreate() {
        mHelper = new DbHelper(getContext());
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
        List<ContentValues> values = new ArrayList<>();
        Collections.addAll(values, pValues);
        final int affected = mHelper.bulkInsert(getTable(pUri), values);
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
        App.getState().getContext().getContentResolver().notifyChange(pUri, null);
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

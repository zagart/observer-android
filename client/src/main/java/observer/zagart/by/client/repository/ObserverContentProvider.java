package observer.zagart.by.client.repository;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.Locale;

import observer.zagart.by.client.App;
import observer.zagart.by.client.constants.Constants;
import observer.zagart.by.client.repository.entities.contracts.ModuleContract;
import observer.zagart.by.client.repository.entities.contracts.StandContract;
import observer.zagart.by.client.repository.helper.DbHelper;

/**
 * @author zagart
 */

public class ObserverContentProvider extends ContentProvider {

    private static final String AUTH =
            "content://observer.zagart.by.client.repository.ObserverContentProvider";
    private DbHelper mOperations;

    @Override
    public boolean onCreate() {
        mOperations = App.getState().getDbHelper();
        return true;
    }

    @Nullable
    @Override
    public Cursor query(
            @NonNull final Uri pUri,
            final String[] pProjection,
            final String pSelection,
            final String[] pSelectionArgs,
            final String pSortOrder
    ) {
        return mOperations.query(pUri.toString(), pSelectionArgs);
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
        final long id = mOperations.insert(table, pContentValues);
        final String uriBuilder = String.format(
                Locale.getDefault(),
                Constants.URI_BUILDER,
                AUTH,
                DbHelper.getTableName(table),
                id
        );
        final Uri uri = Uri.parse(uriBuilder);
        App.getState().getContext().getContentResolver().notifyChange(uri, null);
        return uri;
    }

    @Override
    public int delete(@NonNull final Uri pUri, final String pS, final String[] pStrings) {
        return 0;
    }

    @Override
    public int update(
            @NonNull final Uri pUri,
            final ContentValues pContentValues,
            final String pS,
            final String[] pStrings
    ) {
        return 0;
    }

    private Class<?> getTable(Uri pUri) {
        String table = pUri.getPath();
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

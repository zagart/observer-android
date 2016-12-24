package observer.zagart.by.client.mvp.models.repository;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import observer.zagart.by.client.App;
import observer.zagart.by.client.BuildConfig;
import observer.zagart.by.client.application.constants.ExceptionConstants;
import observer.zagart.by.client.application.constants.Services;
import observer.zagart.by.client.application.constants.URIConstants;
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

    private DatabaseManager mDatabaseManager;

    @SuppressWarnings("WrongConstant")
    @Override
    public boolean onCreate() {
        //TODO find out cause of unstable null pointer exception
        final Context context = App.getContext();
        if (context != null) {
            mDatabaseManager = (DatabaseManager) context.getSystemService(Services.THREAD_MANAGER);
        } else {
            if (BuildConfig.DEBUG) {
                Log.e(ObserverContentProvider.class.getSimpleName(), ExceptionConstants
                        .NULL_CONTEXT);
            }
            mDatabaseManager = new DatabaseManager(getContext());
        }
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
        return mDatabaseManager.query(pSelection, pSelectionArgs);
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
        final long id = mDatabaseManager.insert(table, pContentValues);
        return ContentUris.withAppendedId(pUri, id);
    }

    @Override
    public int delete(
            @NonNull final Uri pUri,
            final String pSelection,
            final String[] pSelectionArgs) {
        return (int) mDatabaseManager.delete(getTable(pUri), pSelection,
                pSelectionArgs);
    }

    @Override
    public int bulkInsert(@NonNull final Uri pUri, @NonNull final ContentValues[] pValues) {
        return mDatabaseManager.bulkInsert(getTable(pUri), pValues);
    }

    @Override
    public int update(@NonNull final Uri pUri,
                      final ContentValues pContentValues,
                      final String pSelection,
                      final String[] pSelectionArgs) {
        return 0;
    }

    private Class<?> getTable(final Uri pUri) {
        final String table = URIUtil.getClearUriPath(pUri);
        switch (table) {
            case URIConstants.MODULE:
                return ModuleContract.class;
            case URIConstants.STAND:
                return StandContract.class;
            default:
                return null;
        }
    }
}

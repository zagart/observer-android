package observer.zagart.by.client.mvp.models.repository;

import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;

import java.lang.ref.WeakReference;

import observer.zagart.by.client.application.utils.URIUtil;
import observer.zagart.by.client.mvp.IMvp;

/**
 * Content observer help application to react on changing of
 * database content.
 *
 * @author zagart
 */
public class DatabaseContentObserver extends ContentObserver {

    private WeakReference<IMvp.IViewOperations> mView;

    public DatabaseContentObserver(final IMvp.IViewOperations pView) {
        super(new Handler(Looper.getMainLooper()));
        mView = new WeakReference<>(pView);
    }

    @Override
    public void onChange(final boolean pSelfChange, final Uri pUri) {
        final String standUri = URIUtil.getStandUri().toString();
        final String moduleUri = URIUtil.getModuleUri().toString();
        final String uri = pUri.toString();
        if (uri.contains(standUri) || uri.contains(moduleUri)) {
            mView.get().onDataChanged(null);
        }
    }
}

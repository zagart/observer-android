package observer.zagart.by.client.mvp.models.repository;

import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import java.lang.ref.WeakReference;

import observer.zagart.by.client.mvp.IMvp;

/**
 * Content observer help application to react on changing of
 * database content.
 *
 * @author zagart
 */
public class ModelContentObserver extends ContentObserver {

    private static final String LOG_MESSAGE = "New event";
    private WeakReference<IMvp.IViewOperations> mView;

    public ModelContentObserver(final IMvp.IViewOperations pView) {
        super(new Handler(Looper.getMainLooper()));
        mView = new WeakReference<>(pView);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onChange(final boolean pSelfChange, final Uri pUri) {
        Log.i(ModelContentObserver.class.getSimpleName(), LOG_MESSAGE);
//        final String standUri = URIUtil.getStandUri().toString();
//        final String moduleUri = URIUtil.getModuleUri().toString();
//        final String uri = pUri.toString();
//        if (uri.contains(standUri) || uri.contains(moduleUri)) {
//            mView.get().onDataChanged(null);
//        }
    }
}

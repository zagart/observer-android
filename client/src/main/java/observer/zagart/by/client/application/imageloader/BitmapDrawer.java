package observer.zagart.by.client.application.imageloader;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.LruCache;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.ref.WeakReference;

import observer.zagart.by.client.App;
import observer.zagart.by.client.application.interfaces.IAction;
import observer.zagart.by.client.application.interfaces.ICallback;
import observer.zagart.by.client.application.interfaces.IDrawable;
import observer.zagart.by.client.application.managers.ThreadManager;
import observer.zagart.by.client.network.http.HttpFactory;
import observer.zagart.by.client.network.http.interfaces.IHttpClient;
import observer.zagart.by.client.network.http.requests.DownloadBytesRequest;

/**
 * Implementation of interface IDrawable.
 * Canvas - ImageView. Image - Bitmap.
 *
 * @author zagart
 */
@SuppressWarnings("unused") //TODO thread manager behavior was changed, callbacks must be rewritten
public class BitmapDrawer implements IDrawable<ImageView, String> {

    final private static float MEMORY_USE_COEFFICIENT = 0.125f; //12.5%
    private final LruCache<String, Bitmap> mCache;
    private ThreadManager mThreadManager;

    {
        final int mMaxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        final int mCacheSize = (int) (mMaxMemory * MEMORY_USE_COEFFICIENT);
        mCache = new LruCache<String, Bitmap>(mCacheSize) {

            @Override
            protected int sizeOf(final String pKey, final Bitmap pImage) {
                return pImage.getByteCount() / 1024;
            }
        };
        mThreadManager = App.getThreadManager();
    }

    /**
     * Method draws on ImageView canvas specified as URL bitmap.
     * If there is no persisted in cache bitmap with such
     * URL as key, then it will be try to download it in background
     * thread.
     *
     * @param pImageView Canvas
     * @param pUrl       URL of Bitmap image
     */
    @Override
    public void draw(final ImageView pImageView, final String pUrl) {
        final boolean isImageSet = setImageBitmap(pImageView, pUrl);
        if (!isImageSet) {
            mThreadManager.performAction(
                    new BitmapDownloadAction(),
                    new BitmapDownloadCallback(pImageView),
                    pUrl);
        }
    }

    private Bitmap getFromCache(String pUrl) {
        synchronized (mCache) {
            return mCache.get(pUrl);
        }
    }

    private boolean putInCache(String pUrl, Bitmap pBitmap) {
        synchronized (mCache) {
            if (mCache.get(pUrl) == null) {
                mCache.put(pUrl, pBitmap);
                return true;
            }
            return false;
        }
    }

    private boolean setImageBitmap(final ImageView pImageView, final String pUrl) {
        final Bitmap bitmap = getFromCache(pUrl);
        if (bitmap != null) {
            pImageView.setImageBitmap(bitmap);
            return true;
        }
        return false;
    }

    /**
     * IAction interface implementation for downloading Bitmap image.
     * Calls {@link BitmapDownloadCallback} methods. Required to
     * execute downloading in background thread {@link ThreadManager}.
     */
    private class BitmapDownloadAction implements IAction<String, Void, ByteArrayOutputStream> {

        @Override
        public ByteArrayOutputStream process(
                final ICallback<Void, ByteArrayOutputStream> pCallback,
                final String... pParam
        ) throws InterruptedException {
            final String pUrl = pParam[0];
            pCallback.onStart(pUrl);
            pCallback.onComplete(pUrl, null);
            return null;
        }
    }

    /**
     * ICallback implementation with callbacks for Bitmap image downloading.
     * Use {@link observer.zagart.by.client.network.http.HttpClient}
     * as HTTP-client for downloading bytes from specified URL. On successful
     * downloading convert them into Bitmap and try to set downloaded image.
     */
    private class BitmapDownloadCallback implements ICallback<Void, ByteArrayOutputStream> {

        private IHttpClient mHttpClient = HttpFactory.getDefaultClient();
        private byte[] downloaded;
        private WeakReference<ImageView> mImageView;

        BitmapDownloadCallback(ImageView pImageView) {
            mImageView = new WeakReference<>(pImageView);
        }

        @Override
        public void onComplete(final String pParam, final ByteArrayOutputStream pBytesStream) {
            final Bitmap bitmap = BitmapFactory.decodeByteArray(
                    downloaded,
                    0,
                    downloaded.length);
            downloaded = null;
            if (pParam != null && bitmap != null) {
                putInCache(pParam, bitmap);
                mThreadManager.post(
                        new Runnable() {

                            @Override
                            public void run() {
                                setImageBitmap(mImageView.get(), pParam);
                            }
                        });
            }
        }

        @Override
        public void onException(final String pParam, final Exception pEx) {
            //ignored
        }

        @Override
        public void onStart(final String pParam) {
            try {
                final ByteArrayOutputStream resultStream = mHttpClient.executeRequest(
                        new DownloadBytesRequest(pParam));
                downloaded = resultStream.toByteArray();
            } catch (IOException pEx) {
                onException(pParam, pEx);
            }
        }

        @Override
        public void onUpdate(final String pParam, final Void pVoid) {
            //ignored
        }
    }
}
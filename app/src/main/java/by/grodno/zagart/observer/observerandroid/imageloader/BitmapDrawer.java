package by.grodno.zagart.observer.observerandroid.imageloader;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.util.LruCache;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.Locale;

import by.grodno.zagart.observer.observerandroid.BuildConfig;
import by.grodno.zagart.observer.observerandroid.http.HttpClientFactory;
import by.grodno.zagart.observer.observerandroid.http.interfaces.IHttpClient;
import by.grodno.zagart.observer.observerandroid.interfaces.IAction;
import by.grodno.zagart.observer.observerandroid.interfaces.ICallback;
import by.grodno.zagart.observer.observerandroid.interfaces.IDrawable;

/**
 * Implementation of interface IDrawable.
 * Canvas - ImageView. Image - Bitmap.
 *
 * @author zagart
 */
public class BitmapDrawer implements IDrawable<ImageView, String> {
    private static final float MEMORY_USE_COEFFICIENT = 0.125f; //12.5%
    private final LruCache<String, Bitmap> mCache;
    private BitmapBackgroundTask mBitmapTask;

    {
        final int mMaxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        final int mCacheSize = (int) (mMaxMemory * MEMORY_USE_COEFFICIENT);
        mCache = new LruCache<String, Bitmap>(mCacheSize) {
            @Override
            protected int sizeOf(final String pKey, final android.graphics.Bitmap pImage) {
                return pImage.getByteCount() / 1024;
            }
        };
        mBitmapTask = new BitmapBackgroundTask(BitmapDrawer.class.getSimpleName());
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
            mBitmapTask.performAction(
                    new BitmapDownloadAction(),
                    new BitmapDownloadCallback(pImageView),
                    pUrl
            );
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
     * execute downloading in background thread {@link BitmapBackgroundTask}.
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
     * Use {@link by.grodno.zagart.observer.observerandroid.http.HttpClient}
     * as HTTP-client for downloading bytes from specified URL. On successful
     * downloading convert them into Bitmap and try to set downloaded image.
     */
    private class BitmapDownloadCallback implements ICallback<Void, ByteArrayOutputStream> {
        private static final String ON_START_EXCEPTION_LOG = "Parameter on failing onStart: %s";
        private IHttpClient mHttpClient = HttpClientFactory.getDefaultClient();
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
                    downloaded.length
            );
            downloaded = null;
            if (pParam != null && bitmap != null) {
                putInCache(pParam, bitmap);
                mBitmapTask.doInUiThread(
                        new Runnable() {
                            @Override
                            public void run() {
                                setImageBitmap(mImageView.get(), pParam);
                            }
                        }
                );
            }
        }

        @Override
        public void onException(final String pParam, final Exception pEx) {
            if (BuildConfig.DEBUG) {
                Log.d(this.getClass().getSimpleName(), pEx.getMessage());
            }
            pEx.getCause();
        }

        @Override
        public void onStart(final String pParam) {
            try {
                final ByteArrayOutputStream resultStream = mHttpClient.downloadBytes(pParam);
                downloaded = resultStream.toByteArray();
            } catch (IOException pEx) {
                if (BuildConfig.DEBUG) {
                    Log.d(
                            this.getClass().getSimpleName(),
                            String.format(Locale.ENGLISH, ON_START_EXCEPTION_LOG, pParam)
                    );
                }
                onException(pParam, pEx);
            }
        }

        @Override
        public void onUpdate(final String pParam, final Void pVoid) {
        }
    }
}

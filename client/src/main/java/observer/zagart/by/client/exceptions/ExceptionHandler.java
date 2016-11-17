package observer.zagart.by.client.exceptions;

import android.util.Log;

import observer.zagart.by.client.BuildConfig;

/**
 * Custom exception handler.
 *
 * @author zagart
 */

public class ExceptionHandler implements Thread.UncaughtExceptionHandler {

    @Override
    public void uncaughtException(final Thread pThread, final Throwable pEx) {
        if (BuildConfig.DEBUG) {
            Log.e(ExceptionHandler.class.getSimpleName(), pEx.getMessage(), pEx);
        }
    }
}

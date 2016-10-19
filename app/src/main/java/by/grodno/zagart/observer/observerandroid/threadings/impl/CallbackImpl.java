package by.grodno.zagart.observer.observerandroid.threadings.impl;
import android.util.Log;

import java.util.Locale;

import by.grodno.zagart.observer.observerandroid.BuildConfig;
import by.grodno.zagart.observer.observerandroid.threadings.Callback;

/**
 * Callback interface implementation.
 */
public class CallbackImpl implements Callback<String, Integer> {
    public static final String STR_UPDATED = "Updated with ";
    public static final String STR_STARTED = "Started.";
    public static final String STR_COMPLETE = "Completed with %s";

    @Override
    public void onComplete(final String pName, final String pResult) {
        if (BuildConfig.DEBUG) {
            Log.d(pName, String.format(Locale.ENGLISH, STR_COMPLETE, pResult));
        }
    }

    @Override
    public void onException(final String pName, final Exception pEx) {
        if (BuildConfig.DEBUG) {
            Log.d(pName, pEx.getMessage());
        }
    }

    @Override
    public void onStart(final String pName) {
        if (BuildConfig.DEBUG) {
            Log.d(pName, STR_STARTED);
        }
    }

    @Override
    public void onUpdate(final String pName, final Integer pProgress) {
        if (BuildConfig.DEBUG) {
            Log.d(pName, String.format(Locale.ENGLISH, "%s %d", STR_UPDATED, pProgress));
        }
    }
}

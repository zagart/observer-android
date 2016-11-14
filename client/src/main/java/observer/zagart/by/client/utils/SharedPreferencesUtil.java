package observer.zagart.by.client.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.Locale;

import observer.zagart.by.client.BuildConfig;
import observer.zagart.by.client.R;

/**
 * Utility class with methods for persisting activity's data as Shared References.
 */
public class SharedPreferencesUtil {

    public static final String PREF_FILE = "SharedFile";

    public static void clearPersistedValues(Context pContext) {
        final SharedPreferences.Editor editor = getSharedPreferencesEditor(pContext);
        editor.clear();
        editor.apply();
    }

    private static SharedPreferences.Editor getSharedPreferencesEditor(Context pContext) {
        SharedPreferences replies = pContext.getSharedPreferences(PREF_FILE, Context.MODE_PRIVATE);
        return replies.edit();
    }

    public static void persistBooleanValue(Context pContext, String pName, Boolean pInput) {
        final SharedPreferences.Editor editor = getSharedPreferencesEditor(pContext);
        editor.putBoolean(pName, pInput);
        editor.apply();
    }

    public static void persistStringValue(Context pContext, String pName, String pInput) {
        final SharedPreferences.Editor editor = getSharedPreferencesEditor(pContext);
        editor.putString(pName, pInput);
        editor.apply();
        if (BuildConfig.DEBUG) {
            Log.i(
                    SharedPreferencesUtil.class.getSimpleName(),
                    String.format(
                            Locale.getDefault(),
                            pContext.getString(R.string.msg_sp_value_persisted),
                            pInput
                    )
            );
        }
    }

    public static Boolean retrieveBooleanValue(Context pContext, String pName) {
        return pContext
                .getSharedPreferences(PREF_FILE, Context.MODE_PRIVATE).getBoolean(pName, false);
    }

    public static String retrieveStringValue(Context pContext, String pName) {
        final String valueRetrieved = pContext
                .getSharedPreferences(PREF_FILE, Context.MODE_PRIVATE).getString(pName, null);
        if (BuildConfig.DEBUG) {
            Log.i(
                    SharedPreferencesUtil.class.getSimpleName(),
                    String.format(
                            Locale.getDefault(),
                            pContext.getString(R.string.msg_sp_value_retrieved),
                            valueRetrieved
                    )
            );
        }
        return valueRetrieved;
    }
}

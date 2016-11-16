package observer.zagart.by.client.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.Locale;

import observer.zagart.by.client.BuildConfig;
import observer.zagart.by.client.R;

import static observer.zagart.by.client.constants.Constants.PREF_FILE;

/**
 * Utility class with methods for persisting activity's data as Shared References.
 */
public class SharedPreferencesUtil {

    private static SharedPreferences.Editor getSharedPreferencesEditor(Context pContext) {
        SharedPreferences replies = pContext.getSharedPreferences(PREF_FILE, Context.MODE_PRIVATE);
        return replies.edit();
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

    static String retrieveStringValue(Context pContext, String pName) {
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

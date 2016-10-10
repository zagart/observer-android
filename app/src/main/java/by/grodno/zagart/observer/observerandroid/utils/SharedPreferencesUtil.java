package by.grodno.zagart.observer.observerandroid.utils;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;

import static android.content.Context.MODE_PRIVATE;

/**
 * Utility class with methods for persisting activity's data as Shared References.
 */
public class SharedPreferencesUtil {

    public static final String PREF_FILE = "MyAgreementsFile";

    public static void clearPersistedValues(AppCompatActivity activity) {
        final SharedPreferences.Editor editor = getSharedPreferencesEditor(activity);
        editor.clear();
        editor.apply();
    }

    private static SharedPreferences.Editor getSharedPreferencesEditor(AppCompatActivity activity) {
        SharedPreferences replies = activity.getSharedPreferences(PREF_FILE, MODE_PRIVATE);
        return replies.edit();
    }

    public static void persistValue(AppCompatActivity activity, String name, Object input) {
        final SharedPreferences.Editor editor = getSharedPreferencesEditor(activity);
        if (input instanceof Boolean) {
            editor.putBoolean(name, (boolean) input);
        }
        if (input instanceof String) {
            editor.putString(name, (String) input);
        }
        editor.apply();
    }

    public static Object retrieveValue(AppCompatActivity activity, String name, Class type) {
        if (String.class == type) {
            return activity.getSharedPreferences(PREF_FILE, MODE_PRIVATE).getString(name, null);
        }
        if (Boolean.class == type) {
            return activity.getSharedPreferences(PREF_FILE, MODE_PRIVATE).getBoolean(name, false);
        }
        return null;
    }

}

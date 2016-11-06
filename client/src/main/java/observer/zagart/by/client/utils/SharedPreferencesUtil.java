package observer.zagart.by.client.utils;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;

/**
 * Utility class with methods for persisting activity's data as Shared References.
 */
public class SharedPreferencesUtil {

    public static final String PREF_FILE = "SharedFile";

    public static void clearPersistedValues(AppCompatActivity activity) {
        final SharedPreferences.Editor editor = getSharedPreferencesEditor(activity);
        editor.clear();
        editor.apply();
    }

    private static SharedPreferences.Editor getSharedPreferencesEditor(AppCompatActivity activity) {
        SharedPreferences replies = activity.getSharedPreferences(PREF_FILE, Context.MODE_PRIVATE);
        return replies.edit();
    }

    public static void persistBooleanValue(AppCompatActivity activity, String name, Boolean input) {
        final SharedPreferences.Editor editor = getSharedPreferencesEditor(activity);
        editor.putBoolean(name, input);
        editor.apply();
    }

    public static void persistStringValue(AppCompatActivity activity, String name, String input) {
        final SharedPreferences.Editor editor = getSharedPreferencesEditor(activity);
        editor.putString(name, input);
        editor.apply();
    }

    public static Boolean retrieveBooleanValue(AppCompatActivity activity, String name) {
        return activity.getSharedPreferences(PREF_FILE, Context.MODE_PRIVATE).getBoolean(name, false);
    }

    public static String retrieveStringValue(AppCompatActivity activity, String name) {
        return activity.getSharedPreferences(PREF_FILE, Context.MODE_PRIVATE).getString(name, null);
    }
}

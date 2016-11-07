package observer.zagart.by.client.utils;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import java.util.Locale;

import observer.zagart.by.client.singletons.ContextHolder;

/**
 * ...just not found better place for Toast for now
 *
 * @author zagart
 */
public class AndroidUtil {
    public static void showMessage(String pMessage, String... pFormatArgs) {
        Toast.makeText(
                ContextHolder.get(),
                String.format(
                        Locale.getDefault(),
                        pMessage,
                        pFormatArgs
                ),
                Toast.LENGTH_LONG
        ).show();
    }

    public static void showMessage(int pResId, String... pFormatArgs) {
        showMessage(ContextHolder.get().getString(pResId), pFormatArgs);
    }

    public static void startActivity(Class pClass) {
        final Context context = ContextHolder.get();
        Intent intent = new Intent(context, pClass);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        context.startActivity(intent);
    }
}

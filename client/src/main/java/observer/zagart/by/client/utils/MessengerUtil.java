package observer.zagart.by.client.utils;
import android.widget.Toast;

import java.util.Locale;

import observer.zagart.by.client.singletons.ContextHolder;

/**
 * ...just not found better place for Toast for now
 *
 * @author zagart
 */
public class MessengerUtil {
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
}

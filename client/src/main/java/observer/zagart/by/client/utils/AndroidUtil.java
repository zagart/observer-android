package observer.zagart.by.client.utils;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import java.util.Locale;

import observer.zagart.by.client.singletons.ContextHolder;
import observer.zagart.by.client.threadings.ThreadWorker;

/**
 * ...just not found better place for this methods
 *
 * @author zagart
 */
public class AndroidUtil {
    private static ThreadWorker sThreadWorker = ThreadWorker.getDefaultInstance();

    /**
     * Calls {@link #showMessage(int, String...)} using UI-thread.
     *
     * @param pResId      Look linked method
     * @param pFormatArgs Look linked method
     */
    public static void postMessage(final int pResId, final String... pFormatArgs) {
        sThreadWorker.post(
                new Runnable() {
                    @Override
                    public void run() {
                        showMessage(pResId, pFormatArgs);
                    }
                }
        );
    }

    /**
     * Calls {@link #showMessage(String, String...)} using UI-thread.
     *
     * @param pMessage    Look linked method
     * @param pFormatArgs Look linked method
     */
    public static void postMessage(final String pMessage, final String... pFormatArgs) {
        sThreadWorker.post(
                new Runnable() {
                    @Override
                    public void run() {
                        showMessage(pMessage, pFormatArgs);
                    }
                }
        );
    }

    /**
     * Method shows Toast with specified parameters.
     * Method uses {@link ContextHolder#get()} as context for resolving
     * resource id.
     *
     * @param pMessage    Message to show
     * @param pFormatArgs If message contains format parameters, values
     *                    for that parameters can be sent via this array
     */
    public static void showMessage(final String pMessage, final String... pFormatArgs) {
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

    /**
     * Method shows Toast with specified parameters.
     * Method uses {@link ContextHolder#get()} as context for resolving
     * resource id.
     *
     * @param pResId      Resource id of string to show
     * @param pFormatArgs If message contains format parameters, values
     *                    for that parameters can be sent via this array
     */
    public static void showMessage(final int pResId, final String... pFormatArgs) {
        showMessage(ContextHolder.get().getString(pResId), pFormatArgs);
    }

    /**
     * Method starts new activity with {@link android.content.Intent#FLAG_ACTIVITY_SINGLE_TOP}.
     *
     * @param pClass Class of activity to start
     * @param pFlags Additional flags
     */
    public static void startActivity(final Class pClass, final int... pFlags) {
        final Context context = ContextHolder.get();
        Intent intent = new Intent(context, pClass);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        for (int flag : pFlags) {
            intent.setFlags(flag);
        }
        context.startActivity(intent);
    }
}

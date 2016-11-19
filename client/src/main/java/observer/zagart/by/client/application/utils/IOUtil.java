package observer.zagart.by.client.application.utils;

import android.content.Context;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;

import observer.zagart.by.client.App;
import observer.zagart.by.client.application.constants.Constants;
import observer.zagart.by.client.application.managers.ThreadManager;

/**
 * Utility class for input/output related methods.
 *
 * @author zagart
 */
public class IOUtil {

    private static ThreadManager sThreadManager = App.getState().getThreadManager();

    public static void close(final Closeable pCloseable) {
        if (pCloseable != null) {
            try {
                pCloseable.close();
            } catch (IOException pEx) {
                Log.e(IOUtil.class.getSimpleName(), Constants.FAILED_TO_EXECUTE_CLOSING, pEx);
            }
        }
    }

    public static void showToast(final Context pContext, final String pMessage) {
        if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
            showNotNullMessage(pContext, pMessage);
        } else {
            sThreadManager.post(
                    new Runnable() {

                        @Override
                        public void run() {
                            showNotNullMessage(pContext, pMessage);
                        }
                    });
        }
    }

    private static void showNotNullMessage(final Context pContext, final String pMessage) {
        if (pMessage != null) {
            Toast.makeText(pContext, pMessage, Toast.LENGTH_LONG).show();
        }
    }

    public static String readStreamUsingBuffer(final InputStream pInputStream) throws IOException {
        final StringBuilder result = new StringBuilder();
        final Reader reader = new InputStreamReader(pInputStream, Charset.defaultCharset());
        final char[] buffer = new char[Constants.READ_BUFFER_SIZE];
        try {
            int bytes;
            while ((bytes = reader.read(buffer)) != Constants.EOF) {
                result.append(buffer, 0, bytes);
            }
        } finally {
            IOUtil.close(reader);
        }
        return result.toString();
    }
}

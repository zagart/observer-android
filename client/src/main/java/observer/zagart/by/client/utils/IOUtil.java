package observer.zagart.by.client.utils;
import android.util.Log;

import java.io.Closeable;
import java.io.IOException;

import observer.zagart.by.client.BuildConfig;

/**
 * Utility class for input/output related methods.
 *
 * @author zagart
 */
public class IOUtil {
    private static final String FAILED_TO_EXECUTE_CLOSING = "Failed to execute closing";

    public static void close(Closeable pCloseable) {
        if (pCloseable != null) {
            try {
                pCloseable.close();
            } catch (IOException pEx) {
                if (BuildConfig.DEBUG) {
                    Log.e(IOUtil.class.getSimpleName(), FAILED_TO_EXECUTE_CLOSING, pEx);
                }
            }
        }
    }
}

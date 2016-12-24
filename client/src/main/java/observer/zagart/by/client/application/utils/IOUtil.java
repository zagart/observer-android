package observer.zagart.by.client.application.utils;

import android.content.Context;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;

import observer.zagart.by.client.App;
import observer.zagart.by.client.application.constants.Services;
import observer.zagart.by.client.application.managers.ThreadManager;
import observer.zagart.by.client.network.http.requests.DownloadBytesRequest;

/**
 * Utility class for input/output related methods.
 *
 * @author zagart
 */
@SuppressWarnings({"WrongConstant", "unused"})
public class IOUtil {

    private static final short READ_BUFFER_SIZE = 4096;
    private static final byte EOF = -1;
    private static final String FAILED_TO_EXECUTE_CLOSING = "Failed to execute closing";
    private static ThreadManager sThreadManager = (ThreadManager) App
            .getContext()
            .getSystemService(Services.THREAD_MANAGER);

    public static void close(final Closeable pCloseable) {
        if (pCloseable != null) {
            try {
                pCloseable.close();
            } catch (IOException pEx) {
                Log.e(IOUtil.class.getSimpleName(), FAILED_TO_EXECUTE_CLOSING, pEx);
            }
        }
    }

    public static void showToast(final String pMessage) {
        final Context context = App.getContext();
        if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
            showNotNullMessage(context, pMessage);
        } else {
            sThreadManager.post(
                    () -> showNotNullMessage(context, pMessage));
        }
    }

    public static void showToast(final Context pContext, final String pMessage) {
        if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
            showNotNullMessage(pContext, pMessage);
        } else {
            sThreadManager.post(
                    () -> showNotNullMessage(pContext, pMessage));
        }
    }

    private static void showNotNullMessage(final Context pContext, final String pMessage) {
        if (pMessage != null) {
            Toast.makeText(pContext, pMessage, Toast.LENGTH_LONG).show();
        }
    }

    @Nullable
    public static ByteArrayOutputStream readInputIntoByteArray(final InputStream pInputStream) {
        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        final byte[] buffer = new byte[IOUtil.READ_BUFFER_SIZE];
        int bytesRead;
        try {
            while ((bytesRead = pInputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, bytesRead);
            }
        } catch (IOException pEx) {
            Log.e(DownloadBytesRequest.class.getSimpleName(), pEx.getMessage(), pEx);
            return null;
        }
        return outputStream;
    }

    public static String readStreamIntoString(final InputStream pInputStream) throws IOException {
        final Reader reader = new InputStreamReader(pInputStream, Charset.defaultCharset());
        final StringBuilder result = new StringBuilder();
        final char[] buffer = new char[READ_BUFFER_SIZE];
        try {
            int bytes;
            while ((bytes = reader.read(buffer)) != EOF) {
                result.append(buffer, 0, bytes);
            }
        } finally {
            IOUtil.close(reader);
        }
        return result.toString();
    }
}

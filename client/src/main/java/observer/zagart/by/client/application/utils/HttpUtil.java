package observer.zagart.by.client.application.utils;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.util.Map;

/**
 * Utility class for HTTP-requests and responses.
 *
 * @author zagart
 */

public class HttpUtil {

    public static final int BYTE_STREAM = 0;
    private static final String UTF_8 = "UTF-8";

    public static void setRequestBody(final HttpURLConnection pConnection,
                                      final Map<String, String> pBody)
            throws IOException, JSONException {
        final JSONObject jsonObject = new JSONObject();
        for (final Map.Entry<String, String> entry : pBody.entrySet()) {
            jsonObject.put(entry.getKey(), entry.getValue());
            Log.i(HttpUtil.class.getSimpleName(), jsonObject.toString());
            byte[] outputInBytes = jsonObject.toString().getBytes(UTF_8);
            final OutputStream stream = pConnection.getOutputStream();
            for (byte byte3 : outputInBytes) {
                stream.write(byte3);
            }
            stream.close();
        }
    }
}

package observer.zagart.by.client.network.http.requests;

import android.graphics.Bitmap;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Map;

import observer.zagart.by.client.application.constants.NetworkConstants;
import observer.zagart.by.client.application.utils.IOUtil;
import observer.zagart.by.client.network.http.interfaces.IHttpClient;
import observer.zagart.by.client.network.http.interfaces.IHttpClient.IRequest;

/**
 * Request for uploading image to server.
 *
 * @author zagart
 * @see IRequest
 */

public class UploadImageRequest implements IHttpClient.IRequest<String> {

    private String mUrl;
    private Bitmap mBitmap;

    public UploadImageRequest(final String pUrl) {
        mUrl = pUrl;
    }

    public UploadImageRequest(final Bitmap pBitmap) {
        mBitmap = pBitmap;
    }

    @Override
    public String getContentType() {
        return IHttpClient.IHttpData.ContentType.APPLICATION_JSON_CHARSET_UTF_8;
    }

    @Override
    public IHttpClient.Method getMethodType() {
        return IHttpClient.Method.POST;
    }

    @Override
    public String getUrl() {
        return NetworkConstants.IMAGES_UPLOAD_URL;
    }

    @Override
    public void handleRequestConnection(final HttpURLConnection pConnection) {
        final ByteArrayOutputStream stream = new ByteArrayOutputStream();
        final Map<String, String> body = new HashMap<String, String>() {

            {
                put(NetworkConstants.UPLOAD_PRESET, NetworkConstants.PRESET_KEY);
                if (mUrl != null) {
                    put(NetworkConstants.FILE, mUrl);
                } else {
                    mBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    put(NetworkConstants.FILE, new String(stream.toByteArray()));
                    try {
//            HttpUtil.setRequestBody(pConnection, body);
                        JSONObject request = new JSONObject();
                        request.put(NetworkConstants.UPLOAD_PRESET, NetworkConstants.PRESET_KEY);
                        request.put(NetworkConstants.FILE, new String(stream.toByteArray()));
                        final OutputStream outputStream = pConnection.getOutputStream();
                    } catch (IOException | JSONException pEx) {
                        onIOException();
                    }
                }
            }
        };
    }

    @Override
    public String onErrorStream(final HttpURLConnection pConnection,
                                final InputStream pInputStream) throws IOException {
        final String response = IOUtil.readStreamIntoString(pInputStream);
        Log.i(this.getClass().getSimpleName(), response);
        return response;
    }

    @Override
    public String onStandardStream(final InputStream pInputStream) throws IOException {
        final String response = IOUtil.readStreamIntoString(pInputStream);
        Log.i(this.getClass().getSimpleName(), response);
        return response;
    }

    @Override
    public void onTimeoutException(final String... pParameters) {
    }

    @Override
    public void onIOException(final String... pParameters) {
    }
}
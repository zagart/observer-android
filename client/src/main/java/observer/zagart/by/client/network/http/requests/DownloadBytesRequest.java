package observer.zagart.by.client.network.http.requests;

import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;

import observer.zagart.by.client.application.utils.IOUtil;
import observer.zagart.by.client.network.http.interfaces.IHttpClient;

/**
 * Request for downloading bytes at specified URL address.
 *
 * @author zagart
 */

public class DownloadBytesRequest implements IHttpClient.IRequest<ByteArrayOutputStream> {

    final private String mUrl;

    public DownloadBytesRequest(final String pUrl) {
        mUrl = pUrl;
    }

    @Override
    public String getContentType() {
        return null;
    }

    @Override
    public IHttpClient.Method getMethodType() {
        return IHttpClient.Method.GET;
    }

    @Override
    public String getUrl() {
        return mUrl;
    }

    @Override
    public void handleRequestConnection(final HttpURLConnection pConnection) {
        //no special handling required
    }

    @Override
    public ByteArrayOutputStream onErrorStream(
            final HttpURLConnection pConnection,
            final InputStream pInputStream) throws IOException {
        Log.e(DownloadBytesRequest.class.getSimpleName(),
                "Response: " + pConnection.getResponseCode());
        return null;
    }

    @Override
    public ByteArrayOutputStream onStandardStream(final InputStream pInputStream) {
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

    @Override
    public void onTimeoutException() {
        //TODO connection timeout handling
    }
}

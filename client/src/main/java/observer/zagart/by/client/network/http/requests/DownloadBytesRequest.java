package observer.zagart.by.client.network.http.requests;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;

import observer.zagart.by.client.application.utils.IOUtil;
import observer.zagart.by.client.network.http.interfaces.IHttpClient;
import observer.zagart.by.client.network.http.interfaces.IHttpClient.IRequest;

/**
 * Request for downloading bytes at specified URL address.
 *
 * @author zagart
 * @see IRequest
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
        return null;
    }

    @Override
    public ByteArrayOutputStream onStandardStream(final InputStream pInputStream) {
        return IOUtil.readInputIntoByteArray(pInputStream);
    }

    @Override
    public void onTimeoutException(final String... pParameters) {
    }

    @Override
    public void onIOException(final String... pParameters) {
    }
}

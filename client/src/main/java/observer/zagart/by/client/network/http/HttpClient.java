package observer.zagart.by.client.network.http;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;

import observer.zagart.by.client.application.utils.IOUtil;
import observer.zagart.by.client.network.http.interfaces.IHttpClient;

/**
 * Implementation of IHttpClient interface.
 */
public class HttpClient implements IHttpClient {

    private static final int DEFAULT_TIMEOUT = 3000;

    @Override
    public <Result> Result executeRequest(final IRequest<Result> pRequest) throws IOException {
        final Result result;
        final HttpURLConnection connection;
        InputStream errorStream = null;
        InputStream standardStream = null;
        final URL reqUrl = new URL(pRequest.getUrl());
        connection = ((HttpURLConnection) reqUrl.openConnection());
        connection.setConnectTimeout(DEFAULT_TIMEOUT);
        connection.setRequestMethod(pRequest.getMethodType().name());
        connection.setRequestProperty(
                IHttpClient.IHttpData.Header.CONTENT_TYPE,
                pRequest.getContentType());
        pRequest.handleRequestConnection(connection);
        try {
            errorStream = connection.getErrorStream();
            if (errorStream == null) {
                standardStream = connection.getInputStream();
                result = pRequest.onStandardStream(standardStream);
            } else {
                result = pRequest.onErrorStream(connection, errorStream);
            }
            return result;
        } catch (SocketTimeoutException pEx) {
            pRequest.onTimeoutException();
            return null;
        } catch (IOException pEx) {
            pRequest.onIOException();
            return null;
        } finally {
            IOUtil.close(standardStream);
            IOUtil.close(errorStream);
            connection.disconnect();
        }
    }
}

package observer.zagart.by.client.network.http;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Set;

import observer.zagart.by.client.application.constants.ApplicationConstants;
import observer.zagart.by.client.application.utils.IOUtil;
import observer.zagart.by.client.network.http.interfaces.IHttpClient;

/**
 * Implementation of IHttpClient interface.
 */
public class HttpClient implements IHttpClient {

    private static final int TIMEOUT = 3000;

    @Override
    public <Result> Result executeRequest(final IRequest<Result> pRequest) throws IOException {
        final Result result;
        final HttpURLConnection connection;
        InputStream errorStream = null;
        InputStream standardStream = null;
        final URL reqUrl = new URL(pRequest.getUrl());
        connection = ((HttpURLConnection) reqUrl.openConnection());
        connection.setConnectTimeout(TIMEOUT);
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
            return null;
        } finally {
            IOUtil.close(standardStream);
            IOUtil.close(errorStream);
            connection.disconnect();
        }
    }

    //Debug method.
    //TODO delete when backend will be finished
    @SuppressWarnings("unused")
    private String getHeadersInfo(final HttpURLConnection pConnection) {
        StringBuilder builder = new StringBuilder();
        final Map<String, List<String>> headerFields = pConnection.getHeaderFields();
        final Set<Map.Entry<String, List<String>>> entries = headerFields.entrySet();
        for (Map.Entry<String, List<String>> entry : entries) {
            builder.append(entry.getKey()).append(": ");
            for (String value : entry.getValue()) {
                builder.append("(").append(value).append(")");
            }
            builder.append(";\n");
        }
        return builder.toString();
    }
}

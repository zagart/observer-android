package observer.zagart.by.client.http;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Set;

import observer.zagart.by.client.constants.Constants;
import observer.zagart.by.client.http.interfaces.IHttpClient;
import observer.zagart.by.client.utils.IOUtil;

/**
 * Implementation of IHttpClient interface.
 */
public class HttpClient implements IHttpClient {

    //TODO implement method as IRequest object
    @Override
    public ByteArrayOutputStream downloadBytes(final String pUrl) throws IOException {
        final HttpURLConnection connection;
        final InputStream inputStream;
        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        URL url = new URL(pUrl);
        connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod(Method.GET.name());
        inputStream = connection.getInputStream();
        byte[] buffer = new byte[Constants.READ_BUFFER_SIZE];
        int bytesRead;
        while ((bytesRead = inputStream.read(buffer)) > 0) {
            outputStream.write(buffer, 0, bytesRead);
        }
        inputStream.close();
        return outputStream;
    }

    @Override
    public <Result> Result executeRequest(final IRequest<Result> pRequest) throws IOException {
        Result result;
        HttpURLConnection connection;
        InputStream errorStream = null;
        InputStream standardStream = null;
        URL reqUrl = new URL(pRequest.getUrl());
        connection = ((HttpURLConnection) reqUrl.openConnection());
        connection.setConnectTimeout(Constants.TIMEOUT);
        connection.setRequestMethod(pRequest.getMethodType().name());
        connection.setRequestProperty(
                IHttpClient.IHttpData.Header.CONTENT_TYPE,
                pRequest.getContentType()
        );
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

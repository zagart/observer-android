package observer.zagart.by.client.http;
import android.util.Log;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Set;

import observer.zagart.by.client.BuildConfig;
import observer.zagart.by.client.http.interfaces.IHttpClient;

import static observer.zagart.by.client.http.interfaces.IHttpClient.IHttpData.Header.CONTENT_TYPE;

/**
 * Implementation of IHttpClient interface.
 */
public class HttpClient implements IHttpClient {
    private static final int READ_BUFFER_SIZE = 4096;
    private static final String TAG = HttpClient.class.getSimpleName();
    public static final String STATUS = "status";
    public static final String ERROR_STREAM_NOT_NULL = "Error stream not null";
    public static final String HTTP_REQUEST_FAILED = "HTTP-request failed";

    @Override
    public ByteArrayOutputStream downloadBytes(final String pUrl) throws IOException {
        final HttpURLConnection connection;
        final InputStream inputStream;
        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        URL url = new URL(pUrl);
        connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod(Method.GET.name());
        inputStream = connection.getInputStream();
        byte[] buffer = new byte[READ_BUFFER_SIZE];
        int bytesRead;
        while ((bytesRead = inputStream.read(buffer)) > 0) {
            outputStream.write(buffer, 0, bytesRead);
        }
        inputStream.close();
        return outputStream;
    }

    @Override
    public String executeRequest(final IRequest pRequest) throws IOException {
        String response = null;
        HttpURLConnection connection;
        InputStream inputStream;
        try {
            URL reqUrl = new URL(pRequest.getUrl());
            connection = ((HttpURLConnection) reqUrl.openConnection());
            connection.setRequestMethod(pRequest.getMethodType().name());
            connection.setRequestProperty(
                    CONTENT_TYPE,
                    pRequest.getContentType()
            );
            pRequest.handleRequestConnection(connection);
            if ((inputStream = connection.getErrorStream()) == null) {
                inputStream = connection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line);
                }
                response = stringBuilder.toString();
            } else {
                response = getHeadersInfo(connection);
                if (BuildConfig.DEBUG) {
                    Log.d(TAG, ERROR_STREAM_NOT_NULL);
                }
            }
            inputStream.close();
        } catch (Exception pEx) {
            if (BuildConfig.DEBUG) {
                Log.e(TAG, HTTP_REQUEST_FAILED, pEx);
            }
        }
        return response;
    }

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

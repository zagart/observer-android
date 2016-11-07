package observer.zagart.by.client.http;
import android.util.Log;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;
import java.util.Set;

import observer.zagart.by.client.BuildConfig;
import observer.zagart.by.client.http.interfaces.IHttpClient;
import observer.zagart.by.client.utils.IOUtil;

import static observer.zagart.by.client.http.interfaces.IHttpClient.IHttpData.Header.CONTENT_TYPE;

/**
 * Implementation of IHttpClient interface.
 */
public class HttpClient implements IHttpClient {
    private static final byte EOF = -1;
    private static final String ERROR_STREAM_NOT_NULL = "Error stream not null";
    private static final String HTTP_REQUEST_FAILED = "HTTP-request failed";
    private static final short READ_BUFFER_SIZE = 4096;
    private static final String TAG = HttpClient.class.getSimpleName();

    public static String inputStreamToString(InputStream pInputStream) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(pInputStream));
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            stringBuilder.append(line);
        }
        return stringBuilder.toString();
    }

    public static String readStreamUsingBuffer(InputStream pInputStream) throws IOException {
        final StringBuilder result = new StringBuilder();
        final Reader reader = new InputStreamReader(pInputStream, Charset.defaultCharset());
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
    public <Result> Result executeRequest(final IRequest<Result> pRequest) throws IOException {
        Result result;
        HttpURLConnection connection;
        InputStream errorStream = null;
        InputStream standardStream = null;
        URL reqUrl = new URL(pRequest.getUrl());
        connection = ((HttpURLConnection) reqUrl.openConnection());
        connection.setRequestMethod(pRequest.getMethodType().name());
        connection.setRequestProperty(
                CONTENT_TYPE,
                pRequest.getContentType()
        );
        pRequest.handleRequestConnection(connection);
        try {
            errorStream = connection.getErrorStream();
            if (errorStream == null) {
                standardStream = connection.getInputStream();
                result = pRequest.onStandardStream(standardStream);
            } else {
                if (BuildConfig.DEBUG) {
                    Log.d(TAG, ERROR_STREAM_NOT_NULL);
                }
                result = pRequest.onErrorStream(errorStream);
            }
            return result;
        } catch (IOException pEx) {
            if (BuildConfig.DEBUG) {
                Log.e(TAG, HTTP_REQUEST_FAILED, pEx);
            }
            return null;
        } finally {
            IOUtil.close(standardStream);
            IOUtil.close(errorStream);
        }
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

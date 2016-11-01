package by.grodno.zagart.observer.observerandroid.http;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import by.grodno.zagart.observer.observerandroid.http.interfaces.IHttpClient;

import static by.grodno.zagart.observer.observerandroid.http.interfaces.IHttpClient.IHttpData.Header.CONTENT_TYPE;

/**
 * Implementation of IHttpClient interface.
 */
public class HttpClient implements IHttpClient {
    private static final int READ_BUFFER_SIZE = 4096;

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
            inputStream = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
            response = stringBuilder.toString();
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }
}

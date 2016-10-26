package by.grodno.zagart.observer.observerandroid.http;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import by.grodno.zagart.observer.observerandroid.http.interfaces.IHttpClient;

/**
 * Implementation of IHttpClient interface.
 */
public class HttpClient implements IHttpClient {
    @Override
    public String executeRequest(final IRequest pRequest) {
        String response = null;
        HttpURLConnection connection;
        try {
            URL reqUrl = new URL(pRequest.getUrl());
            connection = ((HttpURLConnection) reqUrl.openConnection());
            connection.setRequestMethod(pRequest.getMethodType().name());
            pRequest.handleRequestConnection(connection);
            InputStream inputStream = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
            response = stringBuilder.toString();
            inputStream.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }
}

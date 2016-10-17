package by.grodno.zagart.observer.observerandroid.http;
import android.support.annotation.Nullable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Enumeration;
import java.util.Properties;

import static by.grodno.zagart.observer.observerandroid.http.IHttpClient.IRequest.Method.GET;
import static by.grodno.zagart.observer.observerandroid.http.IHttpClient.IRequest.Method.POST;

/**
 * Implementation of IHttpClient interface.
 */
public class HttpClient implements IHttpClient {
    @Override
    public String get(final IRequest pRequest) {
        return processRequest(pRequest, GET);
    }

    @Override
    public String post(final IRequest pRequest) {
        return processRequest(pRequest, POST);
    }

    @Nullable
    private String processRequest(final IRequest pRequest, IRequest.Method method) {
        String response = null;
        try {
            URL reqUrl = new URL(pRequest.getUrl());
            HttpURLConnection connection = ((HttpURLConnection) reqUrl.openConnection());
            connection.setRequestMethod(method.name());
            setRequestProperties(connection, pRequest.getProperties());
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

    private void setRequestProperties(HttpURLConnection pConnection, Properties pProperties) {
        Enumeration e = pProperties.propertyNames();
        while (e.hasMoreElements()) {
            String key = (String) e.nextElement();
            String value = pProperties.getProperty(key);
            pConnection.setRequestProperty(key, value);
        }
    }
}

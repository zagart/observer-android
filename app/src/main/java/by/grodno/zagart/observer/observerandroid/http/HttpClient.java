package by.grodno.zagart.observer.observerandroid.http;
import android.app.Activity;
import android.widget.Button;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import by.grodno.zagart.observer.observerandroid.R;
import by.grodno.zagart.observer.observerandroid.singleton.ContextHolder;

/**
 * Implementation of IHttpClient interface.
 */
public class HttpClient implements IHttpClient {
    @Override
    public String get(final IRequest pRequest) {
        String response = null;
        try {
            URL reqUrl = new URL(pRequest.getUrl());
            HttpURLConnection connection = ((HttpURLConnection) reqUrl.openConnection());
            connection.setRequestMethod("GET");
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
        } catch (Exception e) {
            Activity activity = (Activity) ContextHolder.get();
            final Button viewById = (Button) activity.findViewById(R.id.main_menu_btn_settings);
            viewById.setText(e.getMessage());
        }
        return response;
    }

    @Override
    public InputStream post(final IRequest pRequest) {
        return null;
    }
}

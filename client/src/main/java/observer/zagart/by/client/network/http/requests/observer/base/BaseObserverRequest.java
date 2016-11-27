package observer.zagart.by.client.network.http.requests.observer.base;

import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.Locale;

import observer.zagart.by.client.App;
import observer.zagart.by.client.BuildConfig;
import observer.zagart.by.client.R;
import observer.zagart.by.client.application.constants.LogConstants;
import observer.zagart.by.client.application.utils.IOUtil;
import observer.zagart.by.client.network.http.interfaces.IHttpClient;

/**
 * Abstract request-class with main settings for connecting Observer HTTP-server.
 *
 * @author zagart
 * @see IHttpClient.IRequest
 */
public abstract class BaseObserverRequest implements IHttpClient.IRequest<String> {

    @Override
    public String getContentType() {
        return IHttpClient.IHttpData.ContentType.APPLICATION_JSON_CHARSET_UTF_8;
    }

    @Override
    public String getUrl() {
        return App.getContext().getString(R.string.observer_url);
    }

    @Override
    public String onErrorStream(
            final HttpURLConnection pConnection,
            final InputStream pInputStream) throws IOException {
        if (BuildConfig.DEBUG) {
            String errorMessage = String.format(
                    Locale.getDefault(),
                    App.getContext().getString(R.string.err_code_server_response),
                    pConnection.getResponseCode());
            Log.e(BaseObserverRequest.class.getSimpleName(), errorMessage);
        }
        return null;
    }

    @Override
    public String onStandardStream(final InputStream pInputStream) {
        try {
            return IOUtil.readStreamUsingBuffer(pInputStream);
        } catch (IOException pEx) {
            if (BuildConfig.DEBUG) {
                Log.e(BaseObserverRequest.class.getSimpleName(), pEx.getMessage());
            }
            return null;
        }
    }

    @Override
    public void onTimeoutException() {
        if (BuildConfig.DEBUG) {
            Log.e(BaseObserverRequest.class.getSimpleName(), LogConstants.TIMEOUT_EXCEPTION);
        }
    }
}

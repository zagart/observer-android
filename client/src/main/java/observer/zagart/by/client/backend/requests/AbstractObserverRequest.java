package observer.zagart.by.client.backend.requests;
import android.content.Context;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.Locale;

import observer.zagart.by.client.BuildConfig;
import observer.zagart.by.client.R;
import observer.zagart.by.client.activities.BaseActivity;
import observer.zagart.by.client.http.interfaces.IHttpClient;
import observer.zagart.by.client.singletons.ContextHolder;
import observer.zagart.by.client.utils.BroadcastUtil;
import observer.zagart.by.client.utils.IOUtil;

/**
 * Abstract class with main data for connecting ObserverApi HTTP-server.
 *
 * @author zagart
 */
public abstract class AbstractObserverRequest implements IHttpClient.IRequest<String> {
    @Override
    public String getContentType() {
        return IHttpClient.IHttpData.ContentType.APPLICATION_JSON_CHARSET_UTF_8;
    }

    @Override
    public String getUrl() {
        return ContextHolder.get().getString(R.string.observer_url);
    }

    @Override
    public String onErrorStream(
            final HttpURLConnection pConnection,
            final InputStream pInputStream
    ) throws IOException {
        if (BuildConfig.DEBUG) {
            String errorMessage = String.format(
                    Locale.getDefault(),
                    ContextHolder.get().getString(R.string.err_code_server_response),
                    pConnection.getResponseCode()
            );
            Log.e(AbstractObserverRequest.class.getSimpleName(), errorMessage);
        }
        return null;
    }

    @Override
    public String onStandardStream(final InputStream pInputStream) {
        try {
            return IOUtil.readStreamUsingBuffer(pInputStream);
        } catch (IOException pEx) {
            if (BuildConfig.DEBUG) {
                Log.e(AbstractObserverRequest.class.getSimpleName(), pEx.getMessage(), pEx);
            }
            return null;
        }
    }

    @Override
    public void onTimeoutException() {
        final Context context = ContextHolder.get();
        BroadcastUtil.sendBroadcast(
                context,
                BaseActivity.SERVER_TIMEOUT_ERROR,
                context.getString(R.string.err_timeout)
        );
    }
}

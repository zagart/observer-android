package observer.zagart.by.client.server.requests;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.Locale;

import observer.zagart.by.client.BuildConfig;
import observer.zagart.by.client.R;
import observer.zagart.by.client.http.interfaces.IHttpClient;
import observer.zagart.by.client.singletons.ContextHolder;
import observer.zagart.by.client.utils.AndroidUtil;
import observer.zagart.by.client.utils.IOUtil;

/**
 * IRequest implementation that is responsible for registration at
 * HTTP-server Observer.
 *
 * @author zagart
 * @see IHttpClient.IRequest
 */
public class RegistrationRequest implements IHttpClient.IRequest<String> {
    private String mLogin;
    private String mPassword;

    public RegistrationRequest(final String pLogin, final String pPassword) {
        mLogin = pLogin;
        mPassword = pPassword;
    }

    @Override
    public String getContentType() {
        return IHttpClient.IHttpData.ContentType.APPLICATION_JSON_CHARSET_UTF_8;
    }

    @Override
    public IHttpClient.Method getMethodType() {
        return IHttpClient.Method.POST;
    }

    @Override
    public String getUrl() {
        return ContextHolder.get().getString(R.string.observer_url);
    }

    @Override
    public void handleRequestConnection(final HttpURLConnection pConnection) {
        pConnection.addRequestProperty(
                IHttpClient.IHttpData.Header.ACTION,
                IHttpClient.IHttpData.Actions.REGISTER
        );
        pConnection.addRequestProperty(
                IHttpClient.IHttpData.Header.LOGIN,
                mLogin
        );
        pConnection.addRequestProperty(
                IHttpClient.IHttpData.Header.PASSWORD,
                mPassword
        );
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
            Log.e(RegistrationRequest.class.getSimpleName(), errorMessage);
        }
        return null;
    }

    @Override
    public String onStandardStream(final InputStream pInputStream) {
        try {
            return IOUtil.readStreamUsingBuffer(pInputStream);
        } catch (IOException pEx) {
            if (BuildConfig.DEBUG) {
                Log.e(RegistrationRequest.class.getSimpleName(), pEx.getMessage(), pEx);
            }
            return null;
        }
    }

    @Override
    public void onTimeoutException() {
        AndroidUtil.postMessage(R.string.err_timeout);
    }
}

package observer.zagart.by.client.server.requests;
import java.net.HttpURLConnection;

import observer.zagart.by.client.R;
import observer.zagart.by.client.http.interfaces.IHttpClient;
import observer.zagart.by.client.singletons.ContextHolder;

/**
 * IRequest implementation that is responsible for authorization at
 * HTTP-server Observer.
 *
 * @author zagart
 * @see observer.zagart.by.client.http.interfaces.IHttpClient.IRequest
 */
public class AuthenticationRequest implements IHttpClient.IRequest {
    private String mLogin;
    private String mPassword;

    public AuthenticationRequest(final String pLogin, final String pPassword) {
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
                IHttpClient.IHttpData.Actions.AUTHENTICATE
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
}

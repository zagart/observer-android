package by.grodno.zagart.observer.observerandroid.http.observer.requests;
import java.net.HttpURLConnection;

import by.grodno.zagart.observer.observerandroid.http.interfaces.IHttpClient;

/**
 * IRequest implementation that is responsible for registration at
 * HTTP-server Observer.
 *
 * @author zagart
 * @see IHttpClient.IRequest
 */
public class RegistrationRequest implements IHttpClient.IRequest {
    private static final String AUTHORIZATION_URL = "authorization_url";
    private String mLogin;
    private String mPassword;

    public RegistrationRequest(final String pLogin, final String pPassword) {
        mLogin = pLogin;
        mPassword = pPassword;
    }

    @Override
    public String getContentType() {
        return IHttpClient.IHttpData.ContentType.APPLICATION_JSON;
    }

    @Override
    public IHttpClient.Method getMethodType() {
        return IHttpClient.Method.POST;
    }

    @Override
    public String getUrl() {
        return AUTHORIZATION_URL;
    }

    @Override
    public void handleRequestConnection(final HttpURLConnection pConnection) {
        pConnection.addRequestProperty(
                IHttpClient.IHttpData.Header.ACTION,
                IHttpClient.IHttpData.Actions.AUTHORIZE
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

package observer.zagart.by.client.network.http.requests.observer;

import java.net.HttpURLConnection;

import observer.zagart.by.client.network.http.interfaces.IHttpClient;
import observer.zagart.by.client.network.http.requests.observer.base.BaseObserverRequest;

/**
 * BaseObserverRequest implementation that is responsible for authorization at
 * HTTP-server Observer.
 *
 * @author zagart
 */
public class AuthenticationRequest extends BaseObserverRequest {

    private String mLogin;
    private String mPassword;

    public AuthenticationRequest(final String pLogin, final String pPassword) {
        mLogin = pLogin;
        mPassword = pPassword;
    }

    @Override
    public IHttpClient.Method getMethodType() {
        return IHttpClient.Method.POST;
    }

    @Override
    public void handleRequestConnection(final HttpURLConnection pConnection) {
        pConnection.addRequestProperty(
                IHttpClient.IHttpData.Header.ACTION,
                IHttpClient.IHttpData.Actions.AUTHENTICATE);
        pConnection.addRequestProperty(
                IHttpClient.IHttpData.Header.LOGIN,
                mLogin);
        pConnection.addRequestProperty(
                IHttpClient.IHttpData.Header.PASSWORD,
                mPassword);
    }
}

package observer.zagart.by.client.network.http.requests.observer;

import java.net.HttpURLConnection;

import observer.zagart.by.client.network.http.interfaces.IHttpClient;
import observer.zagart.by.client.network.http.requests.observer.base.BaseObserverRequest;

/**
 * BaseObserverRequest implementation that is responsible for registration at
 * HTTP-server Observer.
 *
 * @author zagart
 */
public class RegistrationRequest extends BaseObserverRequest {

    private String mLogin;
    private String mPassword;

    public RegistrationRequest(final String pLogin, final String pPassword) {
        mLogin = pLogin;
        mPassword = pPassword;
    }

    @Override
    public IHttpClient.Method getMethodType() {
        return IHttpClient.Method.GET;
    }

    @Override
    public void handleRequestConnection(final HttpURLConnection pConnection) {
        pConnection.addRequestProperty(
                IHttpClient.IHttpData.Header.ACTION,
                IHttpClient.IHttpData.Actions.REGISTER);
        pConnection.addRequestProperty(
                IHttpClient.IHttpData.Header.LOGIN,
                mLogin);
        pConnection.addRequestProperty(
                IHttpClient.IHttpData.Header.PASSWORD,
                mPassword);
    }

    @Override
    public void onTimeoutException(final String... pParameters) {
    }

    @Override
    public void onIOException(final String... pParameters) {
    }
}

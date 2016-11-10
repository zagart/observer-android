package observer.zagart.by.client.server.requests;
import java.net.HttpURLConnection;

import observer.zagart.by.client.http.interfaces.IHttpClient;

/**
 * AbstractObserverRequest implementation that is responsible for authorization at
 * HTTP-server Observer.
 *
 * @author zagart
 * @see observer.zagart.by.client.http.interfaces.IHttpClient.IRequest
 */
public class AuthenticationRequest extends AbstractObserverRequest {
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

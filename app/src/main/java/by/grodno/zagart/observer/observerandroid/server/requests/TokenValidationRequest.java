package by.grodno.zagart.observer.observerandroid.server.requests;
import java.net.HttpURLConnection;

import by.grodno.zagart.observer.observerandroid.http.interfaces.IHttpClient;

/**
 * @author zagart
 */
public class TokenValidationRequest implements IHttpClient.IRequest {
    @Override
    public String getContentType() {
        return null;
    }

    @Override
    public IHttpClient.Method getMethodType() {
        return null;
    }

    @Override
    public String getUrl() {
        return null;
    }

    @Override
    public void handleRequestConnection(final HttpURLConnection pConnection) {
    }
}

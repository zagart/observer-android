package by.grodno.zagart.observer.observerandroid.http.interfaces.impl;
import java.net.HttpURLConnection;

import by.grodno.zagart.observer.observerandroid.http.interfaces.IHttpClient;

/**
 * Implementation of POST-method of HTTP-protocol.
 */
public class PostRequest implements IHttpClient.IRequest {
    @Override
    public String getContentType() {
        return null;
    }

    @Override
    public IHttpClient.Method getMethodType() {
        return IHttpClient.Method.POST;
    }

    @Override
    public String getUrl() {
        return null;
    }

    @Override
    public void handleRequestConnection(final HttpURLConnection pConnection) {
    }
}

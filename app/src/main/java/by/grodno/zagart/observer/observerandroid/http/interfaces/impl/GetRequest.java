package by.grodno.zagart.observer.observerandroid.http.interfaces.impl;
import java.net.HttpURLConnection;

import by.grodno.zagart.observer.observerandroid.http.interfaces.IHttpClient;

/**
 * Implementation of GET-method of HTTP-protocol.
 */
public class GetRequest implements IHttpClient.IRequest {
    @Override
    public String getContentType() {
        return null;
    }

    @Override
    public IHttpClient.Method getMethodType() {
        return IHttpClient.Method.GET;
    }

    @Override
    public String getUrl() {
        return null;
    }

    @Override
    public String handleRequestConnection(final HttpURLConnection pConnection) {
        return null;
    }
}

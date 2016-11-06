package observer.zagart.by.client.http.interfaces.impl;
import java.net.HttpURLConnection;

import observer.zagart.by.client.http.interfaces.IHttpClient;

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

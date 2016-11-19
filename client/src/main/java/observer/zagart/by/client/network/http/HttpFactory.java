package observer.zagart.by.client.network.http;

import observer.zagart.by.client.network.http.interfaces.IHttpClient;

/**
 * Factory of HTTP-clients.
 */
public class HttpFactory {

    public static IHttpClient getDefaultClient() {
        return new HttpClient();
    }
}

package by.grodno.zagart.observer.observerandroid.http;
import by.grodno.zagart.observer.observerandroid.http.interfaces.IHttpClient;

/**
 * Implementation of IHttpClient interface that is based on
 * OkHttp.
 */
public class OkHttpClient implements IHttpClient {
    @Override
    public String executeRequest(final IRequest pRequest, final Method pMethod) {
        return null;
    }
}

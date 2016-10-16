package by.grodno.zagart.observer.observerandroid.http;
import java.io.InputStream;

/**
 * Implementation of this interface allows to class-implementer
 * use privileges of HTTP-client such as do HTTP-request.
 */
public interface IHttpClient {
    String get(IRequest pRequest);
    InputStream post(IRequest pRequest);
    interface IRequest {
        String getUrl();
    }
}

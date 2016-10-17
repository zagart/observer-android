package by.grodno.zagart.observer.observerandroid.http;
import java.util.Properties;

/**
 * Implementation of this interface allows to class-implementer
 * use privileges of HTTP-client such as do HTTP-request.
 */
public interface IHttpClient {
    String get(IRequest pRequest);
    String post(IRequest pRequest);
    interface IRequest {
        Properties getProperties();
        String getUrl();
        enum Action {
            AUTHORIZE
        }

        enum Header {
            ACTION, LOGIN, PASSWORD
        }

        enum Method {
            GET, POST
        }
    }
}

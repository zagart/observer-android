package by.grodno.zagart.observer.observerandroid.http.interfaces;
import java.net.HttpURLConnection;

/**
 * Implementation of this interface allows to class-implementer
 * use privileges of HTTP-client such as do HTTP-request.
 */
public interface IHttpClient {
    String executeRequest(IRequest pRequest, Method pMethod);
    enum Method {
        GET, POST
    }

    interface IRequest {
        String getContentType();
        String getUrl();
        String handleRequestConnection(HttpURLConnection pConnection);
    }

    interface IHttpData {
        enum Attribute {
        }

        enum Header {
        }

        enum Property {
        }
    }
}

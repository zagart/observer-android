package by.grodno.zagart.observer.observerandroid.http.interfaces;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;

/**
 * Implementation of this interface allows to class-implementer
 * use privileges of HTTP-client such as do HTTP-request.
 */
public interface IHttpClient<Result> {
    ByteArrayOutputStream downloadBytes(String pUrl) throws IOException;
    Result executeRequest(IRequest<Result> pRequest) throws IOException;
    enum Method {
        GET, POST
    }

    interface IRequest<Result> {
        String getContentType();
        Method getMethodType();
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

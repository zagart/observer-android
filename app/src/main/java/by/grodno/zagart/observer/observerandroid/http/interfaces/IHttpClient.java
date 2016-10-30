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
    Result executeRequest(IRequest pRequest) throws IOException;
    enum Method {
        GET, POST
    }

    interface IRequest {
        String getContentType();
        Method getMethodType();
        String getUrl();
        void handleRequestConnection(HttpURLConnection pConnection);
    }

    interface IHttpData {
        class ContentType {
            public static final String APPLICATION_JSON = "application/json";
        }

        class Header {
            public static final String ACTION = "action";
            public static final String LOGIN = "login";
            public static final String TOKEN = "token";
            public static final String PASSWORD = "password";
        }

        class Actions {
            public static final String AUTHORIZE = "authorize";
        }
    }
}

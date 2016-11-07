package observer.zagart.by.client.http.interfaces;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;

/**
 * Implementation of this interface allows to class-implementer
 * use privileges of HTTP-client such as do HTTP-request.
 */
public interface IHttpClient {
    ByteArrayOutputStream downloadBytes(String pUrl) throws IOException;
    <Result> Result executeRequest(IRequest<Result> pRequest) throws IOException;
    enum Method {
        GET, POST
    }

    interface IRequest<Result> {
        String getContentType();
        Method getMethodType();
        String getUrl();
        void handleRequestConnection(HttpURLConnection pConnection);
        Result onErrorStream(InputStream pInputStream);
        Result onStandardStream(InputStream pInputStream);
    }

    interface IHttpData {
        class ContentType {
            public static final String APPLICATION_JSON_CHARSET_UTF_8 =
                    "application/json; charset=utf-8";
        }

        class Header {
            public static final String ACTION = "action";
            public static final String LOGIN = "login";
            public static final String TOKEN = "token";
            public static final String PASSWORD = "password";
            public static final String CONTENT_TYPE = "Content-Type";
        }

        class Actions {
            public static final String AUTHENTICATE = "authenticate";
            public static final String REGISTER = "register";
        }
    }
}

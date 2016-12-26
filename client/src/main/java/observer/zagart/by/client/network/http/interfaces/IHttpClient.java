package observer.zagart.by.client.network.http.interfaces;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;

/**
 * Implementation of this interface allows to class-implementer
 * use privileges of HTTP-client such as do HTTP-request.
 */
public interface IHttpClient {

    <Result> Result executeRequest(final IRequest<Result> pRequest) throws IOException;

    enum Method {
        GET, POST
    }

    interface IRequest<Result> {

        String getContentType();

        Method getMethodType();

        String getUrl();

        void handleRequestConnection(final HttpURLConnection pConnection);

        Result onErrorStream(
                HttpURLConnection pConnection,
                InputStream pInputStream
        ) throws IOException;

        Result onStandardStream(final InputStream pInputStream) throws IOException;

        void onTimeoutException(final String... pParameters);

        void onIOException(final String... pParameters);
    }

    //TODO refactor constants
    interface IHttpData {

        class ContentType {

            public static final String APPLICATION_JSON_CHARSET_UTF_8 =
                    "application/json; charset=UTF-8";
        }

        class Header {

            public static final String ACTION = "action";
            public static final String LOGIN = "login";
            public static final String TOKEN = "token";
            public static final String PASSWORD = "password";
            public static final String CONTENT_TYPE = "Content-Type";
            public static final String CRITERIA = "criteria";
        }

        class Actions {

            public static final String AUTHENTICATE = "authenticate";
            public static final String REGISTER = "register";
            public static final String GET_DATA = "get_data";
        }
    }
}

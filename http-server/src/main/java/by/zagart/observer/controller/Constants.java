package by.zagart.observer.controller;

/**
 * @author zagart
 */
public class Constants {
    public static final String REFLECTION = "reflection";
    public static final String STANDS_KEY = "stands";

    public class ServletLogConstants {
        public static final String ATTEMPT_TO_REGISTER_USER = "Attempt to register user.";
        public static final String FAILED_TO_REGISTER_USER = "Failed to register user.";
        public static final String GUEST_CONNECTED = "Guest connected.";
        public static final String USER_HAS_BEEN_AUTHENTICATED = "User has been authenticated.";
        public static final String USER_REQUEST_REJECTED = "User request rejected.";
    }

    public class DataActions {
        public static final String GET_DATA = "get_data";
        public static final String GET_STANDS = "get_stands";
        public static final String GET_MODULES = "get_modules";
    }

    public class Headers {
        public static final String CRITERIA = "criteria";
    }

    public class HttpRequestTypes {
        public static final String ACTION = "action";
        public static final String APPLICATION_JSON_CHARSET_UTF_8 = "application/json; charset=utf-8";
        public static final String AUTHENTICATE = "authenticate";
        public static final String GUEST = "guest";
        public static final String LOGIN = "login";
        public static final String MISSING_CONTENT_TYPE = "missing content type";
        public static final String PASSWORD = "password";
        public static final String REGISTER = "register";
        public static final String TEXT_HTML = "text/html; charset=utf-8";
        public static final String TOKEN = "token";
    }
}

package by.zagart.observer.controller;

import by.zagart.observer.database.services.MainService;
import by.zagart.observer.utils.HttpUtil;
import org.json.simple.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static by.zagart.observer.controller.Observer.HttpErrorCodes.UNAUTHORIZED;
import static by.zagart.observer.controller.Observer.HttpErrorCodes.UNSUPPORTED_MEDIA_TYPE;
import static by.zagart.observer.controller.Observer.ObserverHttpRequestTypes.*;
import static by.zagart.observer.controller.Observer.ServletLogConstants.*;

@WebServlet("/RequestHandler")
public class Observer extends HttpServlet {
    private MainService mService;

    public Observer() {
        super();
    }

    protected void doGet(HttpServletRequest pRequest, HttpServletResponse pResponse)
            throws ServletException, IOException {
        String contentType = (pRequest.getContentType() == null) ? MISSING_CONTENT_TYPE : pRequest.getContentType();
        switch (contentType) {
            case APPLICATION_JSON_CHARSET_UTF_8:
                break;
            case TEXT_HTML:
                break;
            default:
                pResponse.sendError(UNSUPPORTED_MEDIA_TYPE);
        }
    }

    protected void doPost(HttpServletRequest pRequest, HttpServletResponse pResponse)
            throws ServletException, IOException {
        Logger.log(HttpUtil.getRequestInfo(pRequest));
        String contentType = (pRequest.getContentType() == null) ? MISSING_CONTENT_TYPE : pRequest.getContentType();
        switch (contentType) {
            case APPLICATION_JSON_CHARSET_UTF_8:
                final String login = pRequest.getHeader(LOGIN);
                final String password = pRequest.getHeader(PASSWORD);
                final String token = pRequest.getHeader(TOKEN);
                mService
                        .setLogin(login)
                        .setPassword(password)
                        .setToken(token);
                if (login.equals(GUEST)) {
                    Logger.log(GUEST_CONNECTED);
                    handleGuestRequest(pRequest);
                } else {
                    final String action = pRequest.getHeader(ACTION);
                    handleAction(pRequest, pResponse, action);
                }
                break;
            case TEXT_HTML:
                break;
            default:
                pResponse.sendError(UNSUPPORTED_MEDIA_TYPE);
        }
    }

    private void handleAction(
            HttpServletRequest pRequest,
            HttpServletResponse pResponse,
            String pAction
    ) throws IOException {
        JSONObject jsonToken = new JSONObject();
        String token;
        switch (pAction) {
            case AUTHENTICATE:
                token = mService.authenticateUser();
                jsonToken.put(TOKEN, token);
                if (token != null) {
                    Logger.log(USER_HAS_BEEN_AUTHENTICATED);
                    responseInJson(pResponse, jsonToken);
                } else {
                    Logger.log(USER_REQUEST_REJECTED);
                    pResponse.sendError(UNAUTHORIZED);
                }
                break;
            case REGISTER:
                token = mService.registerUser(pRequest);
                jsonToken.put(TOKEN, token);
                if (token == null) {
                    Logger.log(FAILED_TO_REGISTER_USER);
//                    pResponse.sendError(SERVER_INTERNAL_ERROR);
                    pResponse.getWriter().println("Failed :(");
                    break;
                } else {
                    responseInJson(pResponse, jsonToken);
                }
                break;
        }
    }

    private void handleGuestRequest(HttpServletRequest pRequest) {
    }

    @Override
    public void init() throws ServletException {
        mService = new MainService();
    }

    private void responseInJson(HttpServletResponse pResponse, JSONObject pJSONObject) throws IOException {
        pResponse.getWriter().println(pJSONObject.toJSONString());
    }

    public class ServletLogConstants {
        public static final String ATTEMPT_TO_REGISTER_USER = "Attempt to register user.";
        public static final String FAILED_TO_REGISTER_USER = "Failed to register user.";
        public static final String GUEST_CONNECTED = "Guest connected.";
        public static final String USER_HAS_BEEN_AUTHENTICATED = "User has been authenticated.";
        public static final String USER_REQUEST_REJECTED = "User request rejected.";
    }

    public class ObserverHttpRequestTypes {
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

    public class HttpErrorCodes {
        public static final int FORBIDDEN = 403;
        public static final int SERVER_INTERNAL_ERROR = 500;
        public static final int UNAUTHORIZED = 401;
        public static final int UNSUPPORTED_MEDIA_TYPE = 415;
    }
}


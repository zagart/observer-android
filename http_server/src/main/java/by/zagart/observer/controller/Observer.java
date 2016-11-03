package by.zagart.observer.controller;

import by.zagart.observer.database.services.MainService;
import by.zagart.observer.utils.HttpUtil;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.crypto.MacProvider;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.Key;

@WebServlet("/ObserverRequestHandler")
public class Observer extends HttpServlet {
    public static final String ACTION = "action";
    public static final String APPLICATION_JSON_CHARSET_UTF_8 = "application/json; charset=utf-8";
    public static final String ATTEMPT_TO_REGISTER_USER = "Attempt to register user.";
    public static final String AUTHORIZE = "authorize";
    public static final int FORBIDDEN = 403;
    public static final String GET_ALL_STANDS = "get_all_stands";
    public static final String GUEST = "guest";
    public static final String GUEST_CONNECTED = "Guest connected.";
    public static final String LOGIN = "login";
    public static final String MISSING_CONTENT_TYPE = "missing content type";
    public static final String PASSWORD = "password";
    public static final String REGISTER = "register";
    public static final Key SECRET_KEY = MacProvider.generateKey(SignatureAlgorithm.HS512);
    public static final int SERVER_INTERNAL_ERROR = 500;
    public static final String TEXT_HTML = "text/html";
    public static final String TOKEN = "token";
    public static final int UNAUTHORIZED = 401;
    public static final int UNSUPPORTED_MEDIA_TYPE = 415;
    public static final String USER_AUTHENTICATED = "User authorized.";
    public static final String USER_HAS_BEEN_AUTHORIZED = "User has been authorized.";
    public static final String USER_REQUEST_REJECTED = "User request rejected.";
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
        String contentType = (pRequest.getContentType() == null) ? MISSING_CONTENT_TYPE : pRequest.getContentType();
        final PrintWriter writer = pResponse.getWriter();
        switch (contentType) {
            case APPLICATION_JSON_CHARSET_UTF_8:
                HttpUtil.printRequestInfo(pRequest);
                final String login = pRequest.getHeader(LOGIN);
                final String password = pRequest.getHeader(PASSWORD);
                final String token = pRequest.getHeader(TOKEN);
                mService
                        .setLogin(login)
                        .setPassword(password)
                        .setToken(token);
                if (login.equals(GUEST)) {
                    System.out.println(GUEST_CONNECTED);
                    handleGuestRequest(pRequest);
                } else {
                    if (mService.isUserAuthenticated()) {
                        System.out.println(USER_AUTHENTICATED);
                        final String action = pRequest.getHeader(ACTION);
                        handleAction(pRequest, pResponse, action);
                    } else {
                        System.out.println(USER_REQUEST_REJECTED);
                        pResponse.sendError(FORBIDDEN);
                    }
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
        switch (pAction) {
            case AUTHORIZE:
                if (mService.isUserAuthorized()) {
                    System.out.println(USER_HAS_BEEN_AUTHORIZED);
                } else {
                    System.out.println(USER_REQUEST_REJECTED);
                    pResponse.sendError(UNAUTHORIZED);
                }
                break;
            case REGISTER:
                System.out.println(ATTEMPT_TO_REGISTER_USER);
                final Long result = mService.registerUser(pRequest);
                if (result == -1) {
                    pResponse.sendError(SERVER_INTERNAL_ERROR);
                    break;
                }
                pResponse.getWriter().println("User with id #" + result + "registered.");
                break;
        }
    }

    private void handleGuestRequest(HttpServletRequest pRequest) {
    }

    @Override
    public void init() throws ServletException {
        mService = new MainService();
    }
}


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
    public static final String GET_ALL_STANDS = "get_all_stands";
    public static final String LOGIN = "login";
    public static final String MISSING_CONTENT_TYPE = "missing content type";
    public static final Key SECRET_KEY = MacProvider.generateKey(SignatureAlgorithm.HS512);
    public static final String TEXT_HTML = "text/html";
    public static final String TOKEN = "token";
    public static final int UNSUPPORTED_MEDIA_TYPE = 415;

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
                //pResponse.sendError(UNSUPPORTED_MEDIA_TYPE);
//                final PrintWriter writer = pResponse.getWriter();
//                writer.println(MainService.getAllStandsInJson());
                HttpUtil.printRequestInfo(pRequest);
        }
    }

    protected void doPost(HttpServletRequest pRequest, HttpServletResponse pResponse)
            throws ServletException, IOException {
        String contentType = (pRequest.getContentType() == null) ? MISSING_CONTENT_TYPE : pRequest.getContentType();
        final PrintWriter writer = pResponse.getWriter();
        switch (contentType) {
            case APPLICATION_JSON_CHARSET_UTF_8:
                final String login = pRequest.getHeader(LOGIN);
                final String token = pRequest.getHeader(TOKEN);
                final boolean authorized = MainService.isAuthorized(login, token);
                if (authorized) {
                    final String action = pRequest.getHeader(ACTION);
                    if (action.equals(GET_ALL_STANDS)) {
                        writer.println(MainService.getAllStandsInJson());
                    }
                }
                break;
            case TEXT_HTML:
                break;
            default:
                HttpUtil.printRequestInfo(pRequest);
        }
    }
}


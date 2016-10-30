package by.zagart.observer.controller;

import by.zagart.observer.database.services.MainService;
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
    public static final String APPLICATION_JSON = "application/json";
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

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String contentType = (request.getContentType() == null) ? MISSING_CONTENT_TYPE : request.getContentType();
        switch (contentType) {
            case APPLICATION_JSON:
                break;
            case TEXT_HTML:
                break;
            default:
                //response.sendError(UNSUPPORTED_MEDIA_TYPE);
                final PrintWriter writer = response.getWriter();
                writer.println(MainService.getAllStandsInJson());
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String contentType = (request.getContentType() == null) ? MISSING_CONTENT_TYPE : request.getContentType();
        final PrintWriter writer = response.getWriter();
        switch (contentType) {
            case APPLICATION_JSON:
                final String login = request.getHeader(LOGIN);
                final String token = request.getHeader(TOKEN);
                final boolean authorized = MainService.isAuthorized(login, token);
                if (authorized) {
                    final String action = request.getHeader(ACTION);
                    if (action.equals(GET_ALL_STANDS)) {
                        writer.println(MainService.getAllStandsInJson());
                    }
                }
                break;
            case TEXT_HTML:
                break;
            default:
        }
    }
}


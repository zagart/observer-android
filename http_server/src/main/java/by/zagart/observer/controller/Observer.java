package by.zagart.observer.controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/ObserverRequestHandler")
public class Observer extends HttpServlet {
    public static final String APPLICATION_JSON = "application/json";
    public static final String MISSING_CONTENT_TYPE = "missing content type";
    public static final String TEXT_HTML = "text/html";
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
                response.sendError(UNSUPPORTED_MEDIA_TYPE);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //handle post
    }
}


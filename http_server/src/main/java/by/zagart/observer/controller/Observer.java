package by.zagart.observer.controller;

import by.zagart.observer.database.services.MainService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/AndroidRequestHandler")
public class Observer extends HttpServlet {
    public static final String APPLICATION_JSON = "application/json";
    public static final String AUTHORIZED = "You've been authorized.";
    public static final String FAILED_TO_AUTHORIZE = "Failed to authorize.";
    public static final String GET = "This is GET method response.";
    public static final String POST = "This is POST method response.";
    public static final String TEXT_HTML = "text/html";

    public Observer() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType(TEXT_HTML);
        response.getWriter().print(GET);
        System.out.println("GET processed.");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("POST started.");
        final String action = request.getHeader(Header.ACTION.name());
        final String login = request.getHeader(Header.LOGIN.name());
        final String password = request.getHeader(Header.PASSWORD.name());
        boolean success = false;
        if (action.equals(Action.AUTHORIZE.name())) {
            success = MainService.authorizeUser(login, password);
        }
        response.setContentType(APPLICATION_JSON);
        final PrintWriter writer = response.getWriter();
        if (success) {
            writer.println(AUTHORIZED);
        } else {
            writer.println(FAILED_TO_AUTHORIZE);
        }
    }

    public void init() throws ServletException {
    }

    enum Action {
        AUTHORIZE
    }

    enum Header {
        ACTION, LOGIN, PASSWORD
    }
}


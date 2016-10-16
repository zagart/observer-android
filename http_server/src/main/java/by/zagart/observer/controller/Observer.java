package by.zagart.observer.controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/AndroidRequestHandler")
public class Observer extends HttpServlet {
    public static final String CONTENT_TYPE = "application/json";
    public static final String GET = "This is GET method response.";
    public static final String POST = "This is POST method response.";

    public Observer() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType(CONTENT_TYPE);
        response.getWriter().print(GET);
        System.out.println("GET processed.");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType(CONTENT_TYPE);
        response.getWriter().print(POST);
        System.out.println("POST processed.");
    }

    public void init() throws ServletException {
    }
}


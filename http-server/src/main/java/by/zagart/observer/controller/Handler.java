package by.zagart.observer.controller;

import by.zagart.observer.database.entities.Stand;
import by.zagart.observer.database.services.MainService;
import by.zagart.observer.database.services.impl.StandServiceImpl;
import org.json.simple.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static by.zagart.observer.controller.ObserverConstants.DataActions.GET_DATA;
import static by.zagart.observer.controller.ObserverConstants.DataActions.GET_STANDS;
import static by.zagart.observer.controller.ObserverConstants.HttpRequestTypes.*;
import static by.zagart.observer.controller.ObserverConstants.ServletLogConstants.*;

/**
 * @author zagart
 */
public class Handler {
    private MainService mService;
    private StandServiceImpl mStandService;

    public Handler() {
        mService = new MainService();
        mStandService = new StandServiceImpl();
    }

    public void defineGetAction(
            HttpServletRequest pRequest,
            HttpServletResponse pResponse,
            String pAction
    ) throws IOException {
        switch (pAction) {
            case GET_DATA:
                getDataByCriteria(pResponse, pRequest.getHeader(ObserverConstants.Headers.CRITERIA));
            default:
                pResponse.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    private void definePostAction(
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
                    pResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                }
                break;
            case REGISTER:
                token = mService.registerUser(pRequest);
                jsonToken.put(TOKEN, token);
                if (token == null) {
                    Logger.log(FAILED_TO_REGISTER_USER);
                    pResponse.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                    break;
                } else {
                    responseInJson(pResponse, jsonToken);
                }
                break;
        }
    }

    private String getContentType(HttpServletRequest pRequest) {
        return (pRequest.getContentType() == null) ? MISSING_CONTENT_TYPE : pRequest.getContentType();
    }

    private void getDataByCriteria(HttpServletResponse pResponse, String pCriteria) throws IOException {
        switch (pCriteria) {
            case GET_STANDS:
                final List<Stand> stands = mStandService.getAll();
                JSONObject jsonResponse = new JSONObject();
                for (Stand stand : stands) {
                    jsonResponse.put("stand", stand.toJSONString());
                }
                responseInJson(pResponse, jsonResponse);
                break;
            default:
                pResponse.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    public void handleGetRequest(HttpServletRequest pRequest, HttpServletResponse pResponse) throws IOException {
        switch (getContentType(pRequest)) {
            case APPLICATION_JSON_CHARSET_UTF_8:
                final String action = pRequest.getHeader(ACTION);
                defineGetAction(pRequest, pResponse, action);
                break;
            case TEXT_HTML:
                break;
            default:
                pResponse.sendError(HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE);
        }
    }

    private void handleGuestRequest(HttpServletRequest pRequest) {
    }

    public void handlePostRequest(HttpServletRequest pRequest, HttpServletResponse pResponse) throws IOException {
        switch (getContentType(pRequest)) {
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
                    definePostAction(pRequest, pResponse, action);
                }
                break;
            case TEXT_HTML:
                break;
            default:
                pResponse.sendError(HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE);
        }
    }

    private void responseInJson(HttpServletResponse pResponse, JSONObject pJSONObject) throws IOException {
        pResponse.getWriter().println(pJSONObject.toJSONString());
    }
}

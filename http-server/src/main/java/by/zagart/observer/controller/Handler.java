package by.zagart.observer.controller;

import by.zagart.observer.database.entities.Module;
import by.zagart.observer.database.entities.Stand;
import by.zagart.observer.database.services.MainService;
import by.zagart.observer.database.services.impl.ModuleServiceImpl;
import by.zagart.observer.database.services.impl.StandServiceImpl;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static by.zagart.observer.controller.Constants.DataActions.*;
import static by.zagart.observer.controller.Constants.HttpRequestTypes.*;
import static by.zagart.observer.controller.Constants.ServletLogConstants.*;

/**
 * @author zagart
 */
public class Handler {
    private ModuleServiceImpl mModuleService;
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
                getDataByCriteria(pResponse, pRequest.getHeader(Constants.Headers.CRITERIA));
                break;
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
                    printResponseInJson(pResponse, jsonToken);
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
                    printResponseInJson(pResponse, jsonToken);
                }
                break;
        }
    }

    private String getContentType(HttpServletRequest pRequest) {
        return (pRequest.getContentType() == null) ? MISSING_CONTENT_TYPE : pRequest.getContentType();
    }

    private void getDataByCriteria(HttpServletResponse pResponse, String pCriteria) throws IOException {
        JSONObject jsonResponse = new JSONObject();
        jsonResponse.put(Constants.REFLECTION, pCriteria);
        switch (pCriteria) {
            case GET_STANDS:
                final List<Stand> standList = mStandService.getAll();
                JSONArray standJsonArray = standList.stream().map(Stand::toJSONObject).collect(
                        Collectors.toCollection(JSONArray::new)
                );
                jsonResponse.put(Constants.STANDS_KEY, standJsonArray);
                printResponseInJson(pResponse, jsonResponse);
                break;
            case GET_MODULES:
                final List<Module> moduleList = mModuleService.getAll();
                JSONArray moduleJsonArray = moduleList.stream().map(Module::toJSONObject).collect(
                        Collectors.toCollection(JSONArray::new)
                );
                jsonResponse.put(Constants.STANDS_KEY, moduleJsonArray);
                printResponseInJson(pResponse, jsonResponse);
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

    private void printResponseInJson(HttpServletResponse pResponse, JSONObject pJSONObject) throws IOException {
        pResponse.getWriter().println(pJSONObject.toJSONString());
    }
}

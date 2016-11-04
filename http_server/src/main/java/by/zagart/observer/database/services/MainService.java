package by.zagart.observer.database.services;

import by.zagart.observer.controller.Logger;
import by.zagart.observer.database.entities.User;
import by.zagart.observer.database.services.impl.UserServiceImpl;
import by.zagart.observer.security.SecurityProvider;

import javax.servlet.http.HttpServletRequest;

import static by.zagart.observer.controller.Observer.ServletLogConstants.ATTEMPT_TO_REGISTER_USER;

public class MainService {
    public static final String ATTEMPT_TO_AUTHENTICATE_USER = "Attempt to authenticate user.";
    public static final String USER_REGISTERED = "User registered.";
    public static final String USER_WITH_SUCH_LOGIN_ALREADY_EXISTS = "User with such login already exists.";
    private String mLogin = null;
    private String mPassword = null;
    private String mToken = null;
    private User mUser = new User();
    private UserServiceImpl mUserService = new UserServiceImpl();

    public MainService() {
    }

    public String authenticateUser() {
        Logger.log(ATTEMPT_TO_AUTHENTICATE_USER);
        mUser = mUserService.getUserByLogin(mLogin);
        if (mUser == null) {
            return null;
        } else {
            return SecurityProvider.getToken(mUser, mPassword);
        }
    }

    public boolean extendSession(String pToken) {
        final User user = mUserService.getUserByToken(pToken);
        return true;
    }

    public String registerUser(HttpServletRequest pRequest) {
        Logger.log(ATTEMPT_TO_REGISTER_USER);
        if (mUserService.getUserByLogin(mLogin) != null) {
            Logger.log(USER_WITH_SUCH_LOGIN_ALREADY_EXISTS);
            return null;
        } else {
            Logger.log(USER_REGISTERED);
            return mUserService.saveUserFromRequest(pRequest);
        }
    }

    public <Data> Data requestData(String pToken, String pAction) {
        return (Data) pAction;
    }

    public MainService setLogin(String pLogin) {
        mLogin = pLogin;
        return this;
    }

    public MainService setPassword(String pPassword) {
        mPassword = pPassword;
        return this;
    }

    public MainService setToken(String pToken) {
        mToken = pToken;
        return this;
    }
}

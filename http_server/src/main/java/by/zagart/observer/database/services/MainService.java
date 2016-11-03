package by.zagart.observer.database.services;

import by.zagart.observer.database.entities.Module;
import by.zagart.observer.database.entities.Stand;
import by.zagart.observer.database.entities.User;
import by.zagart.observer.database.services.impl.StandServiceImpl;
import by.zagart.observer.database.services.impl.UserServiceImpl;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class MainService {
    private boolean isAuthorized = false;
    private String mLogin = null;
    private String mPassword = null;
    private StandServiceImpl mStandService = new StandServiceImpl();
    private String mToken = null;
    private User mUser = new User();
    private UserServiceImpl mUserService = new UserServiceImpl();

    public MainService() {
    }

    public static void persistPairStandModule(
            Stand pStand,
            Module pModule,
            GenericService pStandService,
            GenericService pModuleService
    ) {
        pStandService.save(pStand);
        pModuleService.save(pModule);
        pStand.addModule(pModule);
        pStandService.update(pStand);
        pModuleService.update(pModule);
    }

    public String getAllStandsInJson() {
        final List<Stand> stands = mStandService.getAll();
        final StringBuilder builder = new StringBuilder();
        for (Stand stand : stands) {
            builder.append(stand.toJSONString());
        }
        return stands.toString();
    }

    public boolean isUserAuthenticated() {
        if (mUser == null) {
            mUser = mUserService.getUserByLogin(mLogin);
        }
        if (mUser == null) {
            return false;
        } else {
            return true;
        }
    }

    public boolean isUserAuthorized() {
        return isAuthorized;
    }

    public Long registerUser(HttpServletRequest pRequest) {
        if (mUserService.getUserByLogin(mLogin) != null) {
            return -1L;
        }
        return mUserService.registerUser(pRequest);
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

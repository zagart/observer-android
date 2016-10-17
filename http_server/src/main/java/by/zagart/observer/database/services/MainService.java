package by.zagart.observer.database.services;

import by.zagart.observer.database.entities.User;
import by.zagart.observer.database.services.impl.UserServiceImpl;

public class MainService {
    private static User sUser;
    private static UserServiceImpl sUserService = new UserServiceImpl();

    private MainService() {
    }

    public static boolean authorizeUser(String pLogin, String pPassword) {
        sUser = authenticateUser(pLogin);
        if (sUser == null || !sUser.getPassword().equals(pPassword)) {
            sUser = null;
            return false;
        } else {
            return true;
        }
    }

    private static User authenticateUser(String login) {
        return sUserService.getUserByLogin(login);
    }
}

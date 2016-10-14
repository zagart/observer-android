package by.zagart.observer;

import by.zagart.observer.classes.WepAppRunner;
import by.zagart.observer.database.entities.User;
import by.zagart.observer.database.services.impl.UserServiceImpl;
import by.zagart.observer.utils.HibernateUtil;

public class WebAppMain {
    /**
     * Точка входа веб-приложения.
     */
    public static void main(String[] args) {
        new WepAppRunner().start();
        UserServiceImpl service = new UserServiceImpl();
        User user = new User();
        user.setRole(User.Role.ADMIN);
        user.setLogin("zagart");
        user.setPassword("s3cret");
        service.save(user);
        if (HibernateUtil.getSessionFactory() != null) {
            HibernateUtil.closeFactory();
        }
    }
}

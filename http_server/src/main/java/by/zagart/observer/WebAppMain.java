package by.zagart.observer;

import by.zagart.observer.utils.HibernateUtil;

public class WebAppMain {
    public static final String PASSWORD = "s3cret";
    public static final String LOGIN = "zagart";
    public static final String INFO = "The best!";
    public static final String AVATAR = "D:\\Info\\icons\\Z.jpg";

    /**
     * Точка входа веб-приложения.
     */
    public static void main(String[] args) {
//        new WepAppRunner().start();
//        UserServiceImpl service = new UserServiceImpl();
//        User user = new User();
//        user.setRole(User.Role.ADMIN);
//        user.setLogin(LOGIN);
//        user.setPassword(PASSWORD);
//        user.setAvatar(AVATAR);
//        user.setRole(User.Role.ADMIN);
//        user.setInfo(INFO);
//        service.save(user);
//        System.out.println(MainService.authorizeUser(LOGIN, PASSWORD));
        if (HibernateUtil.getSessionFactory() != null) {
            HibernateUtil.closeFactory();
        }
    }
}

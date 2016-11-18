package by.zagart.observer.model.services.impl;

import by.zagart.observer.model.dataaccess.impl.StandDaoImpl;
import by.zagart.observer.model.dataaccess.impl.UserDaoImpl;
import by.zagart.observer.model.entities.User;
import by.zagart.observer.model.services.AbstractHibernateService;
import by.zagart.observer.security.SecurityProvider;

import javax.servlet.http.HttpServletRequest;

import static by.zagart.observer.utils.HibernateUtil.closeCurrentSession;
import static by.zagart.observer.utils.HibernateUtil.openCurrentSession;

/**
 * Наследник абстрактного класса service, отвечает за использование
 * классов слоя dataccess типа User, управляет сессиями и транзакциями Hibernate
 * и выполняет логирование.
 * Благодаря механизму рефлексии реализация методов будет взята из
 * абстрактного класса-родителя на основе указанных при наследовании
 * параметров.
 */
public class UserServiceImpl extends AbstractHibernateService<User, Long, StandDaoImpl> {
    private UserDaoImpl mUserDao = new UserDaoImpl();

    public User getUserByLogin(String pLogin) {
        openCurrentSession();
        User user = mUserDao.getUserByLogin(pLogin);
        closeCurrentSession();
        logger.info(String.format("User pulled from model by login = %s.", pLogin));
        return user;
    }

    public User getUserByToken(String pToken) {
        return null;
    }

    public String saveUserFromRequest(HttpServletRequest pRequest) {
        User user = new User();
        user.setLogin(pRequest.getHeader(User.Fields.LOGIN));
        user.setPassword(pRequest.getHeader(User.Fields.PASSWORD));
        user.setInfo(pRequest.getHeader(User.Fields.INFO));
        user.setAvatar(pRequest.getHeader(User.Fields.AVATAR));
        final String role = pRequest.getHeader(User.Fields.ROLE);
        if (role == null) {
            user.setRole(User.Role.USER);
        } else {
            user.setRole(User.Role.valueOf(role));
        }
        //TODO check is fields correct, optimize method
        final String token = SecurityProvider.getToken(user, user.getPassword());
        user.setToken(token);
        openCurrentSession();
        final Long id = mUserDao.save(user);
        closeCurrentSession();
        logger.info(String.format("User registered with id = %d.", id));
        return token;
    }
}

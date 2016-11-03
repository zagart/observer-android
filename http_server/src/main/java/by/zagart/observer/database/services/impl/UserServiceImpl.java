package by.zagart.observer.database.services.impl;

import by.zagart.observer.database.dataaccess.impl.StandDaoImpl;
import by.zagart.observer.database.dataaccess.impl.UserDaoImpl;
import by.zagart.observer.database.entities.User;
import by.zagart.observer.database.services.AbstractHibernateService;

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
        logger.info(String.format("User pulled from database by login = %s.", pLogin));
        return user;
    }

    public Long registerUser(HttpServletRequest pFields) {
        User user = new User();
        user.setLogin(pFields.getHeader(User.Fields.LOGIN));
        user.setPassword(pFields.getHeader(User.Fields.PASSWORD));
        user.setInfo(pFields.getHeader(User.Fields.INFO));
        user.setAvatar(pFields.getHeader(User.Fields.AVATAR));
        user.setRole(User.Role.valueOf(pFields.getHeader(User.Fields.ROLE)));
        //TODO check if fields correct
        return mUserDao.save(user);
    }
}

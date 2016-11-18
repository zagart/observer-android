package by.zagart.observer.model.dataaccess.impl;

import by.zagart.observer.model.dataaccess.AbstractHibernateDao;
import by.zagart.observer.model.entities.User;
import org.hibernate.query.Query;

import static by.zagart.observer.utils.HibernateUtil.getCurrentSession;

/**
 * Наследник абстрактного класса слоя dataaccess, отвечает за манипуляцию
 * данными типа User и их обмен с данными соответствующей таблицы
 * в базе данных.
 * Благодаря механизму рефлексии реализация методов будет взята из
 * абстрактного класса-родителя на основе указанных при наследовании
 * параметров.
 */
public class UserDaoImpl extends AbstractHibernateDao<User, Long> {
    public User getUserByLogin(String pLogin) {
        String hql = String.format("select target from %s target where login = :login", User.class.getSimpleName());
        Query query = getCurrentSession().createQuery(hql);
        query.setParameter("login", pLogin);
        return (User) query.uniqueResult();
    }
}

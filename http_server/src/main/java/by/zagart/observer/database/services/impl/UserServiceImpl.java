package by.zagart.observer.database.services.impl;

import by.zagart.observer.database.dataaccess.impl.StandDaoImpl;
import by.zagart.observer.database.entities.User;
import by.zagart.observer.database.services.AbstractHibernateService;

/**
 * Наследник абстрактного класса service, отвечает за использование
 * классов слоя dataccess типа User, управляет сессиями и транзакциями Hibernate
 * и выполняет логирование.
 * Благодаря механизму рефлексии реализация методов будет взята из
 * абстрактного класса-родителя на основе указанных при наследовании
 * параметров.
 */
public class UserServiceImpl extends AbstractHibernateService<User, Long, StandDaoImpl> {
}

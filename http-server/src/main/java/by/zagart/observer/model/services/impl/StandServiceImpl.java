package by.zagart.observer.model.services.impl;

import by.zagart.observer.model.dataaccess.impl.StandDaoImpl;
import by.zagart.observer.model.entities.Stand;
import by.zagart.observer.model.services.AbstractHibernateService;

/**
 * Наследник абстрактного класса service, отвечает за использование
 * классов слоя dataccess типа Stand, управляет сессиями и транзакциями Hibernate
 * и выполняет логирование.
 * Благодаря механизму рефлексии реализация методов будет взята из
 * абстрактного класса-родителя на основе указанных при наследовании
 * параметров.
 */
public class StandServiceImpl extends AbstractHibernateService<Stand, Long, StandDaoImpl> {
}

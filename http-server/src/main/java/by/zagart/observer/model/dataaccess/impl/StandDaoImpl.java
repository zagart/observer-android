package by.zagart.observer.model.dataaccess.impl;

import by.zagart.observer.model.dataaccess.AbstractHibernateDao;
import by.zagart.observer.model.entities.Stand;

/**
 * Наследник абстрактного класса слоя dataccess, отвечает за манипуляцию
 * данными типа Stand и их обмен с данными соответствующей таблицы
 * в базе данных.
 * Благодаря механизму рефлексии реализация методов будет взята из
 * абстрактного класса-родителя на основе указанных при наследовании
 * параметров.
 */
public class StandDaoImpl extends AbstractHibernateDao<Stand, Long> {
}

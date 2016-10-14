package by.zagart.observer.database.dataaccess.impl;

import by.zagart.observer.database.dataaccess.AbstractHibernateDao;
import by.zagart.observer.database.entities.Module;

/**
 * Наследник абстрактного класса слоя dataaccess, отвечает за манипуляцию
 * данными типа Module и их обмен с данными соответствующей таблицы
 * в базе данных.
 * Благодаря механизму рефлексии реализация методов будет взята из
 * абстрактного класса-родителя на основе указанных при наследовании
 * параметров.
 */
public class ModuleDaoImpl extends AbstractHibernateDao<Module, Long> {
}

package by.zagart.observer.database.services.impl;

import by.zagart.observer.database.dataaccess.impl.ModuleDaoImpl;
import by.zagart.observer.database.entities.Module;
import by.zagart.observer.database.services.AbstractHibernateService;

/**
 * Наследник абстрактного класса service, отвечает за использование
 * классов слоя dataaccess типа Module, управляет сессиями и транзакциями Hibernate
 * и выполняет логирование.
 * Благодаря механизму рефлексии реализация методов будет взята из
 * абстрактного класса-родителя на основе указанных при наследовании
 * параметров.
 */
public class ModuleServiceImpl extends AbstractHibernateService<Module, Long, ModuleDaoImpl> {
}

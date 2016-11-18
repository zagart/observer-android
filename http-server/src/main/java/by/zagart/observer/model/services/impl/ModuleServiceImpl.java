package by.zagart.observer.model.services.impl;

import by.zagart.observer.model.dataaccess.impl.ModuleDaoImpl;
import by.zagart.observer.model.entities.Module;
import by.zagart.observer.model.services.AbstractHibernateService;

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

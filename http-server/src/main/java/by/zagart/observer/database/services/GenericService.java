package by.zagart.observer.database.services;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Интерфейс определяет методы, необходимые классам
 * слоя service.
 *
 * @param <T>
 * @param <PK>
 */
public interface GenericService<T, PK extends Serializable> {
    void delete(final PK id);

    void delete(final T obj);

    int executeQuery(String hql, Map<String, Object> parameters);

    List<T> getAll();

    T getById(final PK id);

    List<T> getListByQuery(String hql);

    Set<PK> getPkSetByQuery(String hql);

    PK save(final T obj);

    void update(final T obj);
}

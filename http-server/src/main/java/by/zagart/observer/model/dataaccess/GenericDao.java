package by.zagart.observer.model.dataaccess;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Интерфейс определяет методы, необходимые для классов
 * слоя dataaccess.
 *
 * @param <T>
 * @param <PK>
 */
public interface GenericDao<T, PK extends Serializable> {
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

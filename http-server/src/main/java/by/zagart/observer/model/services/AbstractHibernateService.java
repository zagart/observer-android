package by.zagart.observer.model.services;

import by.zagart.observer.interfaces.Identifiable;
import by.zagart.observer.interfaces.Reflective;
import by.zagart.observer.model.dataaccess.GenericDao;
import org.apache.log4j.Logger;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static by.zagart.observer.utils.HibernateUtil.*;

/**
 * Абстрактный класс, аналогичный классу слоя dataaccess. Выполняет
 * задачи уровня service, используя уровень dataaccess.
 *
 * @param <T>
 * @param <PK>
 * @param <DAO>
 */
public abstract class AbstractHibernateService<T extends Identifiable, PK extends Serializable, DAO extends GenericDao>
        implements GenericService<T, PK>, Reflective {
    private final T entityObj;
    private GenericDao mDao = (GenericDao) getGenericObject(2);
    public final Logger logger = Logger.getLogger(mDao.getClass());

    {
        entityObj = (T) getGenericObject(0);
    }

    @Override
    public void delete(PK id) {
        openCurrentSessionWithTransaction();
        mDao.delete(id);
        closeCurrentSessionWithTransaction();
        logger.info(String.format("%s object deleted from model by id = %d.",
                entityObj.getClass().getSimpleName(), id));
    }

    @Override
    public void delete(T obj) {
        openCurrentSessionWithTransaction();
        mDao.delete(obj);
        closeCurrentSessionWithTransaction();
        logger.info(String.format("%s object with id = %d deleted from model.",
                entityObj.getClass().getSimpleName(),
                obj.getId()));
    }

    @Override
    public int executeQuery(String hql, Map<String, Object> parameters) {
        openCurrentSessionWithTransaction();
        int affected = mDao.executeQuery(hql, parameters);
        closeCurrentSessionWithTransaction();
        logger.info(String.format("%s query executed. %d element(s) affected.",
                entityObj.getClass().getSimpleName(),
                affected));
        return affected;
    }

    @Override
    public List<T> getAll() {
        openCurrentSession();
        List<T> daoAll = mDao.getAll();
        closeCurrentSession();
        logger.info(String.format("All %s objects pulled from model(%d).",
                entityObj.getClass().getSimpleName(),
                daoAll.size()));
        return daoAll;
    }

    @Override
    public T getById(PK id) {
        openCurrentSession();
        T obj = (T) mDao.getById(id);
        closeCurrentSession();
        logger.info(String.format("%s object pulled from model by id = %d.",
                entityObj.getClass().getSimpleName(),
                obj.getId()));
        return obj;
    }

    @Override
    public List<T> getListByQuery(String hql) {
        openCurrentSession();
        List<T> daoListByQuery = mDao.getListByQuery(hql);
        closeCurrentSession();
        logger.info(String.format("%s object(s) pulled from model by query(%d).",
                entityObj.getClass().getSimpleName(),
                daoListByQuery.size()));
        return daoListByQuery;
    }

    @Override
    public Set<PK> getPkSetByQuery(String hql) {
        openCurrentSession();
        Set<PK> daoPkSetByQuery = mDao.getPkSetByQuery(hql);
        closeCurrentSession();
        logger.info(String.format("%s id(s) pulled from model by query(%d).",
                entityObj.getClass().getSimpleName(),
                daoPkSetByQuery.size()));
        return daoPkSetByQuery;
    }

    @Override
    public PK save(T obj) {
        openCurrentSessionWithTransaction();
        mDao.save(obj);
        closeCurrentSessionWithTransaction();
        logger.info(String.format("%s object saved with id = %d.",
                entityObj.getClass().getSimpleName(),
                obj.getId()));
        return (PK) obj.getId();
    }

    @Override
    public void update(T obj) {
        openCurrentSessionWithTransaction();
        mDao.update(obj);
        closeCurrentSessionWithTransaction();
        logger.info(String.format("%s object with id = %d updated.",
                entityObj.getClass().getSimpleName(),
                obj.getId()));
    }
}

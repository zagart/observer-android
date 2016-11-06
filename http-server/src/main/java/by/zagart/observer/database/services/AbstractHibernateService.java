package by.zagart.observer.database.services;

import by.zagart.observer.database.dataaccess.GenericDao;
import by.zagart.observer.interfaces.Identifiable;
import by.zagart.observer.interfaces.Reflective;
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
    private GenericDao mUserDao = (GenericDao) getGenericObject(2);
    public final Logger logger = Logger.getLogger(mUserDao.getClass());

    {
        entityObj = (T) getGenericObject(0);
    }

    @Override
    public void delete(PK id) {
        openCurrentSessionWithTransaction();
        mUserDao.delete(id);
        closeCurrentSessionWithTransaction();
        logger.info(String.format("%s object deleted from database by id = %d.",
                entityObj.getClass().getSimpleName(), id));
    }

    @Override
    public void delete(T obj) {
        openCurrentSessionWithTransaction();
        mUserDao.delete(obj);
        closeCurrentSessionWithTransaction();
        logger.info(String.format("%s object with id = %d deleted from database.",
                entityObj.getClass().getSimpleName(),
                obj.getId()));
    }

    @Override
    public int executeQuery(String hql, Map<String, Object> parameters) {
        openCurrentSessionWithTransaction();
        int affected = mUserDao.executeQuery(hql, parameters);
        closeCurrentSessionWithTransaction();
        logger.info(String.format("%s query executed. %d element(s) affected.",
                entityObj.getClass().getSimpleName(),
                affected));
        return affected;
    }

    @Override
    public List<T> getAll() {
        openCurrentSession();
        List<T> daoAll = mUserDao.getAll();
        closeCurrentSession();
        logger.info(String.format("All %s objects pulled from database(%d).",
                entityObj.getClass().getSimpleName(),
                daoAll.size()));
        return daoAll;
    }

    @Override
    public T getById(PK id) {
        openCurrentSession();
        T obj = (T) mUserDao.getById(id);
        closeCurrentSession();
        logger.info(String.format("%s object pulled from database by id = %d.",
                entityObj.getClass().getSimpleName(),
                obj.getId()));
        return obj;
    }

    @Override
    public List<T> getListByQuery(String hql) {
        openCurrentSession();
        List<T> daoListByQuery = mUserDao.getListByQuery(hql);
        closeCurrentSession();
        logger.info(String.format("%s object(s) pulled from database by query(%d).",
                entityObj.getClass().getSimpleName(),
                daoListByQuery.size()));
        return daoListByQuery;
    }

    @Override
    public Set<PK> getPkSetByQuery(String hql) {
        openCurrentSession();
        Set<PK> daoPkSetByQuery = mUserDao.getPkSetByQuery(hql);
        closeCurrentSession();
        logger.info(String.format("%s id(s) pulled from database by query(%d).",
                entityObj.getClass().getSimpleName(),
                daoPkSetByQuery.size()));
        return daoPkSetByQuery;
    }

    @Override
    public PK save(T obj) {
        openCurrentSessionWithTransaction();
        mUserDao.save(obj);
        closeCurrentSessionWithTransaction();
        logger.info(String.format("%s object saved with id = %d.",
                entityObj.getClass().getSimpleName(),
                obj.getId()));
        return (PK) obj.getId();
    }

    @Override
    public void update(T obj) {
        openCurrentSessionWithTransaction();
        mUserDao.update(obj);
        closeCurrentSessionWithTransaction();
        logger.info(String.format("%s object with id = %d updated.",
                entityObj.getClass().getSimpleName(),
                obj.getId()));
    }
}

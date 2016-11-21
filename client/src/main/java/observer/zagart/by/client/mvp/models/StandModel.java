package observer.zagart.by.client.mvp.models;

import java.util.List;

import observer.zagart.by.client.application.constants.Constants;
import observer.zagart.by.client.application.utils.URIUtil;
import observer.zagart.by.client.mvp.IMvp;
import observer.zagart.by.client.mvp.models.repository.entities.Stand;

/**
 * IMvp model implementation for {@link Stand}.
 *
 * @author zagart
 */

public class StandModel extends BaseModel<Stand> implements IMvp.IModelOperations<Stand> {

    public StandModel() {
        super(URIUtil.getStandUri());
    }

    @Override
    public Long persist(final Stand pStand) {
        return super.persist(pStand, Constants.SELECT_FROM_STAND_WHERE_ID);
    }

    @Override
    public List<Stand> retrieveAll() {
        return super.retrieveAll(Constants.SELECT_ALL_STANDS);
    }

    @Override
    public void persistAll(final List<Stand> pStands) {
        super.persistAll(pStands, Constants.SELECT_FROM_STAND_WHERE_ID);
    }

    @Override
    public void deleteAll() {
        super.deleteAll();
    }
}

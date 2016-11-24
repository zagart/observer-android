package observer.zagart.by.client.mvp.models;

import java.util.List;

import observer.zagart.by.client.application.constants.DatabaseConstants;
import observer.zagart.by.client.application.utils.URIUtil;
import observer.zagart.by.client.mvp.models.base.BaseModel;
import observer.zagart.by.client.mvp.models.repository.entities.Stand;

/**
 * IMvp model implementation for {@link Stand}.
 *
 * @author zagart
 */

public class StandModel extends BaseModel<Stand> {

    public StandModel() {
        super(URIUtil.getStandUri(), true);
    }

    @Override
    public Long persist(final Stand pStand) {
        return super.persist(pStand, DatabaseConstants.SELECT_FROM_STAND_WHERE_ID);
    }

    @Override
    public List<Stand> retrieveAll() {
        return super.retrieveAll(DatabaseConstants.SELECT_ALL_STANDS);
    }

    @Override
    public void persistAll(final List<Stand> pStands) {
        super.persistAll(pStands, DatabaseConstants.SELECT_FROM_STAND_WHERE_ID);
    }

    @Override
    public void deleteAll() {
        super.deleteAll();
    }
}

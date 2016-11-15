package observer.zagart.by.client.mvp.models;

import java.util.List;

import observer.zagart.by.client.mvp.MVP;
import observer.zagart.by.client.repository.entities.Stand;
import observer.zagart.by.client.services.Service;

/**
 * MVP model implementation for stand.
 *
 * @author zagart
 */

public class StandModel implements MVP.IModelOperations<Stand> {

    private MVP.IPresenterOperations mPresenter;

    public StandModel(final MVP.IPresenterOperations pPresenter) {
        mPresenter = pPresenter;
    }

    @Override
    public List<Stand> retrieveAll() {
        return Service.selectAllStands();
    }

    @Override
    public void persistAll(final List<Stand> pStands) {
        Service.synchronizeStands(pStands);
    }

    @Override
    public void deleteAll() {
        Service.clearCachedStands();
    }
}

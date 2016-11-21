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

    final private IMvp.IPresenterOperations mPresenter;

    public StandModel(final IMvp.IPresenterOperations pPresenter) {
        super(URIUtil.getStandUri());
        mPresenter = pPresenter;
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

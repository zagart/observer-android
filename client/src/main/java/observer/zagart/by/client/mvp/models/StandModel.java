package observer.zagart.by.client.mvp.models;

import java.util.List;

import observer.zagart.by.client.application.utils.URIUtil;
import observer.zagart.by.client.mvp.models.base.BaseModel;
import observer.zagart.by.client.mvp.models.repository.QueryBuilder;
import observer.zagart.by.client.mvp.models.repository.contracts.StandContract;
import observer.zagart.by.client.mvp.models.repository.entities.Stand;

/**
 * IMvp model implementation for {@link Stand}.
 *
 * @author zagart
 */

public class StandModel extends BaseModel<Stand> {

    final private QueryBuilder mQueryBuilder;

    public StandModel() {
        super(URIUtil.getStandUri(), true);
        mQueryBuilder = new QueryBuilder(StandContract.class);
    }

    @Override
    public Long persist(final Stand pStand) {
        return super.persist(pStand, mQueryBuilder.selectById());
    }

    @Override
    public List<Stand> retrieveAll() {
        return super.retrieveAll(mQueryBuilder.selectAll());
    }

    @Override
    public void persistAll(final List<Stand> pStands) {
        super.persistAll(pStands, mQueryBuilder.selectById());
    }

    @Override
    public void deleteAll() {
        super.deleteAll();
    }
}

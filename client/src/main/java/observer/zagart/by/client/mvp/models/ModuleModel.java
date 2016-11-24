package observer.zagart.by.client.mvp.models;

import java.util.List;

import observer.zagart.by.client.application.utils.URIUtil;
import observer.zagart.by.client.mvp.models.base.BaseModel;
import observer.zagart.by.client.mvp.models.repository.QueryBuilder;
import observer.zagart.by.client.mvp.models.repository.contracts.ModuleContract;
import observer.zagart.by.client.mvp.models.repository.entities.Module;

/**
 * IMvp model implementation for {@link Module}.
 *
 * @author zagart
 */

public class ModuleModel extends BaseModel<Module> {

    final private QueryBuilder mQueryBuilder;

    public ModuleModel() {
        super(URIUtil.getModuleUri(), true);
        mQueryBuilder = new QueryBuilder(ModuleContract.class);
    }

    @Override
    public Long persist(final Module pModule) {
        return super.persist(pModule, mQueryBuilder.selectById());
    }

    @Override
    public List<Module> retrieveAll() {
        return super.retrieveAll(mQueryBuilder.selectAll());
    }

    @Override
    public void persistAll(final List<Module> pModules) {
        super.persistAll(pModules, mQueryBuilder.selectById());
    }

    @Override
    public void deleteAll() {
        super.deleteAll();
    }
}

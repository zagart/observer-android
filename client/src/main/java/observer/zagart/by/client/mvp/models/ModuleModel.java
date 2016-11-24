package observer.zagart.by.client.mvp.models;

import java.util.List;

import observer.zagart.by.client.application.constants.DatabaseConstants;
import observer.zagart.by.client.application.utils.URIUtil;
import observer.zagart.by.client.mvp.models.base.BaseModel;
import observer.zagart.by.client.mvp.models.repository.entities.Module;

/**
 * IMvp model implementation for {@link Module}.
 *
 * @author zagart
 */

public class ModuleModel extends BaseModel<Module> {

    public ModuleModel() {
        super(URIUtil.getModuleUri(), true);
    }

    @Override
    public Long persist(final Module pModule) {
        return super.persist(pModule, DatabaseConstants.SELECT_FROM_MODULE_WHERE_ID);
    }

    @Override
    public List<Module> retrieveAll() {
        return super.retrieveAll(DatabaseConstants.SELECT_ALL_MODULES);
    }

    @Override
    public void persistAll(final List<Module> pModules) {
        super.persistAll(pModules, DatabaseConstants.SELECT_FROM_MODULE_WHERE_ID);
    }

    @Override
    public void deleteAll() {
        super.deleteAll();
    }
}

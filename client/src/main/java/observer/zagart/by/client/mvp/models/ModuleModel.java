package observer.zagart.by.client.mvp.models;

import java.util.List;

import observer.zagart.by.client.application.constants.Constants;
import observer.zagart.by.client.application.utils.URIUtil;
import observer.zagart.by.client.mvp.IMvp;
import observer.zagart.by.client.mvp.models.repository.entities.Module;

/**
 * IMvp model implementation for {@link Module}.
 *
 * @author zagart
 */

public class ModuleModel extends BaseModel<Module> implements IMvp.IModelOperations<Module> {

    public ModuleModel() {
        super(URIUtil.getModuleUri());
    }

    @Override
    public Long persist(final Module pModule) {
        return super.persist(pModule, Constants.SELECT_FROM_MODULE_WHERE_ID);
    }

    @Override
    public List<Module> retrieveAll() {
        return super.retrieveAll(Constants.SELECT_ALL_MODULES);
    }

    @Override
    public void persistAll(final List<Module> pModules) {
        super.persistAll(pModules, Constants.SELECT_FROM_MODULE_WHERE_ID);
    }

    @Override
    public void deleteAll() {
        super.deleteAll();
    }
}

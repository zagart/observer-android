package observer.zagart.by.client.mvp.models;

import java.util.List;

import observer.zagart.by.client.mvp.MVP;
import observer.zagart.by.client.repository.entities.Module;
import observer.zagart.by.client.services.Service;

/**
 * MVP model implementation for stand.
 *
 * @author zagart
 */

public class ModuleModel implements MVP.IModelOperations<Module> {

    private MVP.IPresenterOperations mPresenter;

    public ModuleModel(final MVP.IPresenterOperations pPresenter) {
        mPresenter = pPresenter;
    }

    @Override
    public List<Module> retrieveAll() {
        return Service.selectAllModules();
    }

    @Override
    public void persistAll(final List<Module> pStands) {
        //TODO persisting modules
    }

    @Override
    public void deleteAll() {
        //TODO delete all modules
    }
}

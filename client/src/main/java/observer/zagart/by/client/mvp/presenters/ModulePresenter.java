package observer.zagart.by.client.mvp.presenters;

import java.util.List;

import observer.zagart.by.client.mvp.IMvp;
import observer.zagart.by.client.mvp.models.ModuleModel;
import observer.zagart.by.client.mvp.models.repository.entities.Module;
import observer.zagart.by.client.mvp.presenters.base.BasePresenter;
import observer.zagart.by.client.network.http.interfaces.IHttpClient;
import observer.zagart.by.client.network.http.requests.observer.GetModulesRequest;

/**
 * Presenter implementation for {@link observer.zagart.by.client.mvp.views.ModulesActivity} view
 * that uses {@link ModuleModel} model.
 *
 * @author zagart
 */

public class ModulePresenter extends BasePresenter<Module> {

    public ModulePresenter(final IMvp.IViewOperations pView) {
        super(pView, new ModuleModel());
    }

    @Override
    public List<Module> getElementsFromModel() {
        return super.getElementsFromModel();
    }

    @Override
    public void synchronizeModel(final IHttpClient.IRequest<String> pRequest) {
        super.synchronizeModel(new GetModulesRequest());
    }

    @Override
    public void clearModel() {
        super.clearModel();
    }
}

package observer.zagart.by.client.mvp.presenters;

import android.net.Uri;

import java.util.List;

import observer.zagart.by.client.network.http.requests.GetModulesRequest;
import observer.zagart.by.client.network.http.interfaces.IHttpClient;
import observer.zagart.by.client.mvp.IMvp;
import observer.zagart.by.client.mvp.models.ModuleModel;
import observer.zagart.by.client.mvp.models.repository.entities.Module;

/**
 * Presenter implementation for {@link observer.zagart.by.client.mvp.views.ModulesActivity} view.
 *
 * @author zagart
 */

public class ModulePresenter extends BasePresenter<Module> {

    public ModulePresenter(final IMvp.IViewOperations pView) {
        super(pView);
        onCreate(new ModuleModel(this));
    }

    @Override
    public List<Module> getElementsFromModel(final Uri pUri) {
        return super.getElementsFromModel(pUri);
    }

    @Override
    public void synchronizeModel(final Uri pUri, final IHttpClient.IRequest<String> pRequest) {
        super.synchronizeModel(pUri, new GetModulesRequest());
    }

    @Override
    public void clearModel(final Uri pUri) {
        super.clearModel(pUri);
    }
}

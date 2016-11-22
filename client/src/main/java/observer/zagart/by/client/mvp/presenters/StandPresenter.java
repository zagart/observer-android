package observer.zagart.by.client.mvp.presenters;

import java.util.List;

import observer.zagart.by.client.mvp.IMvp;
import observer.zagart.by.client.mvp.models.StandModel;
import observer.zagart.by.client.mvp.models.repository.entities.Stand;
import observer.zagart.by.client.mvp.presenters.base.BasePresenter;
import observer.zagart.by.client.network.http.interfaces.IHttpClient;
import observer.zagart.by.client.network.http.requests.GetStandsRequest;

/**
 * Presenter implementation for {@link observer.zagart.by.client.mvp.views.StandsActivity} view
 * that uses {@link StandModel} model.
 *
 * @author zagart
 */

public class StandPresenter extends BasePresenter<Stand> {

    public StandPresenter(final IMvp.IViewOperations pView) {
        super(pView, new StandModel());
    }

    @Override
    public List<Stand> getElementsFromModel() {
        return super.getElementsFromModel();
    }

    @Override
    public void synchronizeModel(final IHttpClient.IRequest<String> pRequest) {
        super.synchronizeModel(new GetStandsRequest());
    }

    @Override
    public void clearModel() {
        super.clearModel();
    }
}

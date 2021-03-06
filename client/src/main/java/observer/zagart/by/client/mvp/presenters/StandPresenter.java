package observer.zagart.by.client.mvp.presenters;

import java.util.List;

import observer.zagart.by.client.mvp.IMvp;
import observer.zagart.by.client.mvp.models.StandModel;
import observer.zagart.by.client.mvp.models.repository.entities.Stand;
import observer.zagart.by.client.mvp.presenters.base.BasePresenter;
import observer.zagart.by.client.network.http.interfaces.IHttpClient;
import observer.zagart.by.client.network.http.requests.observer.GetStandsRequest;

/**
 * Presenter implementation that uses {@link StandModel} model.
 *
 * @author zagart
 */

public class StandPresenter extends BasePresenter<Stand> {

    public StandPresenter(final IMvp.IViewOperations<Stand> pView) {
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

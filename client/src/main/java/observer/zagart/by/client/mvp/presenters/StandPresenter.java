package observer.zagart.by.client.mvp.presenters;

import android.net.Uri;

import java.util.List;

import observer.zagart.by.client.backend.requests.GetStandsRequest;
import observer.zagart.by.client.http.interfaces.IHttpClient;
import observer.zagart.by.client.mvp.MVP;
import observer.zagart.by.client.mvp.models.StandModel;
import observer.zagart.by.client.repository.entities.Stand;

/**
 * Presenter implementation for {@link observer.zagart.by.client.mvp.views.StandsActivity} view.
 *
 * @author zagart
 */

public class StandPresenter extends BasePresenter<Stand> {

    public StandPresenter(final MVP.IViewOperations pView) {
        super(pView);
        onCreate(new StandModel(this));
    }

    @Override
    public List<Stand> getElementsFromModel(final Uri pUri) {
        return super.getElementsFromModel(pUri);
    }

    @Override
    public void synchronizeModel(final Uri pUri, final IHttpClient.IRequest<String> pRequest) {
        super.synchronizeModel(pUri, new GetStandsRequest());
    }

    @Override
    public void clearModel(final Uri pUri) {
        super.clearModel(pUri);
    }
}

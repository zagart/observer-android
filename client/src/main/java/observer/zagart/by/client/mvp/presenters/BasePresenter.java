package observer.zagart.by.client.mvp.presenters;

import android.content.Context;
import android.net.Uri;

import org.json.JSONException;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.List;

import observer.zagart.by.client.App;
import observer.zagart.by.client.R;
import observer.zagart.by.client.backend.api.ObserverCallback;
import observer.zagart.by.client.http.HttpClientFactory;
import observer.zagart.by.client.http.interfaces.IHttpClient;
import observer.zagart.by.client.mvp.MVP;
import observer.zagart.by.client.threadings.ThreadWorker;
import observer.zagart.by.client.utils.IOUtil;

/**
 * Main presenter implementation.
 *
 * @author zagart
 */
class BasePresenter<Entity> implements MVP.IPresenterOperations<Entity> {

    private WeakReference<MVP.IViewOperations> mView;
    private ThreadWorker mThreadWorker;
    private MVP.IModelOperations<Entity> mModel;

    BasePresenter(final MVP.IViewOperations pView) {
        mView = new WeakReference<>(pView);
        mThreadWorker = App.getState().getThreadWorker();
    }

    @Override
    public void onCreate(final MVP.IModelOperations<Entity> pModel) {
        mModel = pModel;
    }

    @Override
    public List<Entity> getElementsFromModel(final Uri pUri) {
        return mModel.retrieveAll();
    }

    @Override
    public void synchronizeModel(final Uri pUri, final IHttpClient.IRequest<String> pRequest) {
        mThreadWorker.execute(
                new Runnable() {

                    @Override
                    public void run() {
                        try {
                            final String response = HttpClientFactory
                                    .getDefaultClient()
                                    .executeRequest(pRequest);
                            final List<Entity> entities = ObserverCallback.onResponseReceived(
                                    response);
                            if (entities != null && entities.size() > 0) {
                                mModel.persistAll(entities);
                                notifyViewDataChange();
                            }
                            //TODO hande NullPoiterException very bad solution!
                        } catch (IOException | JSONException | NullPointerException pEx) {
                            final Context context = BasePresenter.this.mView.get().getViewContext();
                            IOUtil.showToast(
                                    context,
                                    context.getString(R.string.msg_failed_download_stands));
                        }
                    }
                });
    }

    @Override
    public void clearModel(final Uri pUri) {
        mModel.deleteAll();
        notifyViewDataChange();
    }

    private void notifyViewDataChange() {
        mThreadWorker.post(new Runnable() {

            @Override
            public void run() {
                mView.get().onDataChanged();
            }
        });
    }
}

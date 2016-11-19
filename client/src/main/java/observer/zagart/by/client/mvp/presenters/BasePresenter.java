package observer.zagart.by.client.mvp.presenters;

import android.content.Context;
import android.net.Uri;

import org.json.JSONException;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.List;

import observer.zagart.by.client.App;
import observer.zagart.by.client.R;
import observer.zagart.by.client.application.managers.ThreadManager;
import observer.zagart.by.client.mvp.IMvp;
import observer.zagart.by.client.network.api.ObserverCallback;
import observer.zagart.by.client.network.http.HttpFactory;
import observer.zagart.by.client.network.http.interfaces.IHttpClient;
import observer.zagart.by.client.application.utils.IOUtil;

/**
 * Main presenter implementation.
 *
 * @author zagart
 */
class BasePresenter<Entity> implements IMvp.IPresenterOperations<Entity> {

    private WeakReference<IMvp.IViewOperations> mView;
    private ThreadManager mThreadManager;
    private IMvp.IModelOperations<Entity> mModel;

    BasePresenter(final IMvp.IViewOperations pView) {
        mView = new WeakReference<>(pView);
        mThreadManager = App.getThreadManager();
    }

    @Override
    public void onCreate(final IMvp.IModelOperations<Entity> pModel) {
        mModel = pModel;
    }

    @Override
    public List<Entity> getElementsFromModel(final Uri pUri) {
        return mModel.retrieveAll();
    }

    @Override
    public void synchronizeModel(final Uri pUri, final IHttpClient.IRequest<String> pRequest) {
        mThreadManager.execute(
                new Runnable() {

                    @Override
                    public void run() {
                        try {
                            final String response = HttpFactory
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
        mThreadManager.post(new Runnable() {

            @Override
            public void run() {
                mView.get().onDataChanged();
            }
        });
    }
}

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
import observer.zagart.by.client.application.utils.IOUtil;
import observer.zagart.by.client.mvp.IMvp;
import observer.zagart.by.client.network.api.ObserverCallback;
import observer.zagart.by.client.network.http.HttpFactory;
import observer.zagart.by.client.network.http.interfaces.IHttpClient;

/**
 * Base presenter implementation.
 *
 * @author zagart
 */
abstract class BasePresenter<Entity> implements IMvp.IPresenterOperations<Entity> {

    final private WeakReference<IMvp.IViewOperations> mView;
    final private ThreadManager mThreadManager;
    final private IMvp.IModelOperations<Entity> mModel;
    final private Uri mUri;

    BasePresenter(final IMvp.IViewOperations pView,
                  final IMvp.IModelOperations<Entity> pModel,
                  final Uri pUri) {
        mView = new WeakReference<>(pView);
        mModel = pModel;
        mUri = pUri;
        mThreadManager = App.getThreadManager();
    }

    public Uri getEntityUri() {
        return mUri;
    }

    @Override
    public List<Entity> getElementsFromModel() {
        return mModel.retrieveAll();
    }

    @Override
    public void synchronizeModel(final IHttpClient.IRequest<String> pRequest) {
        mThreadManager.execute(
                () -> {
                    final Context context = getContext();
                    try {
                        final String response = HttpFactory
                                .getDefaultClient()
                                .executeRequest(pRequest);
                        final List<Entity> entities = ObserverCallback.onResponseReceived(
                                response);
                        if (entities != null && entities.size() > 0) {
                            mModel.persistAll(entities);
                        } else {
                            IOUtil.showToast(context,
                                    context.getString(R.string.msg_no_server_response));
                        }
                    } catch (IOException | JSONException pEx) {
                        IOUtil.showToast(
                                context,
                                context.getString(R.string.msg_failed_parse_stands));
                    }
                });
    }

    @Override
    public void clearModel() {
        mModel.deleteAll();
    }

    protected Context getContext() {
        return mView.get().getViewContext();
    }

    protected IMvp.IModelOperations<Entity> getModel() {
        return mModel;
    }

    protected WeakReference<IMvp.IViewOperations> getView() {
        return mView;
    }
}

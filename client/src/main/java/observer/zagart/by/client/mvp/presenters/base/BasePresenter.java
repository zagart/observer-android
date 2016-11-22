package observer.zagart.by.client.mvp.presenters.base;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.ContentObserver;

import org.json.JSONException;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.List;

import observer.zagart.by.client.App;
import observer.zagart.by.client.R;
import observer.zagart.by.client.application.managers.ThreadManager;
import observer.zagart.by.client.application.utils.IOUtil;
import observer.zagart.by.client.mvp.IMvp;
import observer.zagart.by.client.mvp.models.base.BaseModel;
import observer.zagart.by.client.mvp.models.repository.ModelContentObserver;
import observer.zagart.by.client.mvp.models.repository.entities.IEntity;
import observer.zagart.by.client.network.http.HttpFactory;
import observer.zagart.by.client.network.http.interfaces.IHttpClient;
import observer.zagart.by.client.network.http.responses.ObserverJsonResponse;

/**
 * Base presenter implementation.
 *
 * @author zagart
 */
public abstract class BasePresenter<Entity extends IEntity<Entity, ContentValues, Long>>
        implements IMvp.IPresenterOperations<Entity> {

    final private WeakReference<IMvp.IViewOperations> mView;
    final private ThreadManager mThreadManager;
    final private BaseModel<Entity> mModel;
    final private ContentObserver mObserver;

    protected BasePresenter(final IMvp.IViewOperations pView,
                            final BaseModel<Entity> pModel) {
        mView = new WeakReference<>(pView);
        mModel = pModel;
        mThreadManager = App.getThreadManager();
        mObserver = new ModelContentObserver(mView.get());
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
                        final List<Entity> entities = new ObserverJsonResponse(response)
                                .extractEntities();
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

    public void registerObserver() {
        getContentResolver().registerContentObserver(mModel.getUri(), true, mObserver);
    }

    public void unregisterObserver() {
        getContentResolver().unregisterContentObserver(mObserver);
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

    private ContentResolver getContentResolver() {
        return mView.get()
                .getViewContext()
                .getContentResolver();
    }
}
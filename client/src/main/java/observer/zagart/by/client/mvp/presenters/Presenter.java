package observer.zagart.by.client.mvp.presenters;

import android.accounts.AccountAuthenticatorActivity;
import android.content.Context;
import android.text.TextUtils;

import org.json.JSONException;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.List;

import observer.zagart.by.client.App;
import observer.zagart.by.client.R;
import observer.zagart.by.client.account.ObserverAccount;
import observer.zagart.by.client.backend.api.ObserverApi;
import observer.zagart.by.client.backend.api.ObserverCallback;
import observer.zagart.by.client.backend.requests.GetStandsRequest;
import observer.zagart.by.client.http.HttpClientFactory;
import observer.zagart.by.client.mvp.MVP;
import observer.zagart.by.client.mvp.models.ModuleModel;
import observer.zagart.by.client.mvp.models.StandModel;
import observer.zagart.by.client.repository.entities.Module;
import observer.zagart.by.client.repository.entities.Stand;
import observer.zagart.by.client.threadings.ThreadWorker;
import observer.zagart.by.client.utils.IOUtil;

/**
 * Main presenter implementation.
 *
 * @author zagart
 */
public class Presenter implements MVP.IPresenterOperations {

    private WeakReference<MVP.IViewOperations> mView;
    private ThreadWorker mThreadWorker;
    private MVP.IModelOperations<Module> mModuleModel;
    private MVP.IModelOperations<Stand> mStandModel;

    public Presenter(final MVP.IViewOperations pView) {
        mView = new WeakReference<>(pView);
        mThreadWorker = App.getState().getThreadWorker();
        mModuleModel = new ModuleModel(this);
        mStandModel = new StandModel(this);
    }

    @Override
    public List<Stand> getStandsFromModel() {
        return mStandModel.retrieveAll();
    }

    @Override
    public List<Module> getModulesFromModel() {
        return mModuleModel.retrieveAll();
    }

    @Override
    public void downloadAllStands() {
        mThreadWorker.execute(
                new Runnable() {

                    @Override
                    public void run() {
                        try {
                            final String response = HttpClientFactory
                                    .getDefaultClient()
                                    .executeRequest(new GetStandsRequest());
                            final List<Stand> parsedStands = ObserverCallback.onStandsReceived(
                                    response
                            );
                            mStandModel.persistAll(parsedStands);
                            notifyViewDataChange();
                        } catch (IOException | JSONException | NullPointerException pEx) {
                            final Context context = Presenter.this.mView.get().getViewContext();
                            IOUtil.showToast(
                                    context,
                                    context.getString(R.string.msg_failed_download_stands)
                            );
                        }
                    }
                }
        );
    }

    @Override
    public void executeRegistration(
            final AccountAuthenticatorActivity pActivity,
            final CharSequence pCharLogin,
            final CharSequence pCharPassword
    ) {
        mThreadWorker.execute(
                new Runnable() {

                    @Override
                    public void run() {
                        final String login = pCharLogin.toString();
                        final String password = pCharPassword.toString();
                        final String token = ObserverApi.signUp(
                                pActivity,
                                login,
                                password
                        );
                        if (!TextUtils.isEmpty(token)) {
                            ObserverAccount account = new ObserverAccount(login);
                            ObserverCallback.onTokenReceived(
                                    pActivity,
                                    account,
                                    password,
                                    token
                            );
                        } else {
                            Context viewContext = mView.get().getViewContext();
                            IOUtil.showToast(
                                    viewContext,
                                    viewContext.getString(R.string.err_registration_failed)
                            );
                        }
                    }
                }
        );
    }

    @Override
    public void executeAuthentication(
            final AccountAuthenticatorActivity pActivity,
            final CharSequence pLogin,
            final CharSequence pPassword
    ) {
        mThreadWorker.execute(
                new Runnable() {

                    @Override
                    public void run() {
                        final String login = pLogin.toString();
                        final String password = pPassword.toString();
                        final String token = ObserverApi.logIn(
                                pActivity,
                                login,
                                password
                        );
                        if (!TextUtils.isEmpty(token)) {
                            ObserverAccount account = new ObserverAccount(login);
                            ObserverCallback.onTokenReceived(
                                    pActivity,
                                    account,
                                    password,
                                    token
                            );
                        } else {
                            Context viewContext = mView.get().getViewContext();
                            IOUtil.showToast(
                                    viewContext,
                                    viewContext.getString(R.string.msg_failed_authentication)
                            );
                        }
                    }
                }
        );
    }

    @Override
    public void clearStandModel() {
        mStandModel.deleteAll();
        mView.get().onDataChanged();
    }

    @Override
    public void clearModuleModel() {
        mModuleModel.deleteAll();
        mView.get().onDataChanged();
    }

    private void notifyViewDataChange() {
        mThreadWorker.post(
                new Runnable() {

                    @Override
                    public void run() {
                        mView.get().onDataChanged();
                    }
                }
        );
    }
}

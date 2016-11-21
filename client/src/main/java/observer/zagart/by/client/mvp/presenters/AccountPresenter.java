package observer.zagart.by.client.mvp.presenters;

import android.os.Bundle;
import android.text.TextUtils;

import java.util.List;

import observer.zagart.by.client.App;
import observer.zagart.by.client.R;
import observer.zagart.by.client.application.accounts.ObserverAccount;
import observer.zagart.by.client.application.managers.ThreadManager;
import observer.zagart.by.client.application.utils.IOUtil;
import observer.zagart.by.client.application.utils.URIUtil;
import observer.zagart.by.client.mvp.IMvp;
import observer.zagart.by.client.mvp.models.AccountModel;
import observer.zagart.by.client.network.api.ObserverApi;
import observer.zagart.by.client.network.http.interfaces.IHttpClient;

/**
 * Presenter implementation for authentication views that use
 * {@link AccountModel} model.
 *
 * @author zagart
 */

public class AccountPresenter extends BasePresenter<ObserverAccount> {

    final private ThreadManager mThreadManager = App.getThreadManager();

    public AccountPresenter(final IMvp.IViewOperations pView) {
        super(pView, new AccountModel(), URIUtil.getAccountUri());
    }

    public void executeRegistration(final CharSequence pCharLogin,
                                    final CharSequence pCharPassword) {
        mThreadManager.execute(
                () -> {
                    final String login = pCharLogin.toString();
                    final String password = pCharPassword.toString();
                    final String token = ObserverApi.signUp(
                            login,
                            password);
                    if (!TextUtils.isEmpty(token)) {
                        logInOrAddAccount(login, password, token);
                    } else {
                        IOUtil.showToast(
                                getContext(),
                                getContext().getString(R.string.err_registration_failed));
                    }
                });
    }

    public void executeAuthentication(final CharSequence pLogin, final CharSequence pPassword) {
        mThreadManager.execute(
                () -> {
                    final String login = pLogin.toString();
                    final String password = pPassword.toString();
                    final String token = ObserverApi.logIn(
                            login,
                            password);
                    if (!TextUtils.isEmpty(token)) {
                        logInOrAddAccount(login, password, token);
                    } else {
                        IOUtil.showToast(getContext(),
                                getContext().getString(R.string.msg_failed_authentication));
                    }
                });
    }

    @Override
    public List<ObserverAccount> getElementsFromModel() {
        return getModel().retrieveAll();
    }

    @Override
    public void synchronizeModel(final IHttpClient.IRequest<String> pRequest) {
        //TODO sync adapter instead
    }

    @Override
    public void clearModel() {
        getModel().deleteAll();
    }

    private void logInOrAddAccount(final String pLogin,
                                   final String pPassword,
                                   final String pToken) {
        final Bundle parameters = new Bundle();
        parameters.putString(ObserverAccount.NAME, pLogin);
        parameters.putString(ObserverAccount.PASSWORD, pPassword);
        parameters.putString(ObserverAccount.TOKEN, pToken);
        getView().get().onDataChanged(parameters);
    }
}

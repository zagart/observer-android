package observer.zagart.by.client.mvp.presenters;

import android.os.Bundle;
import android.text.TextUtils;

import java.io.IOException;
import java.util.List;

import observer.zagart.by.client.App;
import observer.zagart.by.client.R;
import observer.zagart.by.client.application.accounts.ObserverAccount;
import observer.zagart.by.client.application.managers.ThreadManager;
import observer.zagart.by.client.application.utils.AccountUtil;
import observer.zagart.by.client.application.utils.IOUtil;
import observer.zagart.by.client.application.utils.URIUtil;
import observer.zagart.by.client.mvp.IMvp;
import observer.zagart.by.client.mvp.models.AccountModel;
import observer.zagart.by.client.mvp.presenters.base.BasePresenter;
import observer.zagart.by.client.network.http.HttpFactory;
import observer.zagart.by.client.network.http.interfaces.IHttpClient;
import observer.zagart.by.client.network.http.requests.AuthenticationRequest;
import observer.zagart.by.client.network.http.requests.parser.ObserverJsonParser;

/**
 * Presenter implementation for authentication views that use
 * {@link AccountModel} model.
 *
 * @author zagart
 */

public class AccountPresenter extends BasePresenter<ObserverAccount> {

    final private ThreadManager mThreadManager;
    final private IHttpClient mHttpClient;

    public AccountPresenter(final IMvp.IViewOperations pView) {
        super(pView, new AccountModel(URIUtil.getAccountUri()));
        mThreadManager = App.getThreadManager();
        mHttpClient = HttpFactory.getDefaultClient();
    }

    public void persistAccount(final Bundle pAccountBundle) {
        final ObserverAccount account = AccountUtil.parseAccountBundle(pAccountBundle);
        getModel().persist(account);
        AccountUtil.setCurrentAccount(account);
    }

    public void executeRegistration(final CharSequence pLogin, final CharSequence pPassword) {
        executeAuthRequest(
                pLogin,
                pPassword,
                new AuthenticationRequest(pLogin.toString(), pPassword.toString()),
                R.string.err_registration_failed);
    }

    public void executeAuthentication(final CharSequence pLogin, final CharSequence pPassword) {
        executeAuthRequest(
                pLogin,
                pPassword,
                new AuthenticationRequest(pLogin.toString(), pPassword.toString()),
                R.string.msg_failed_authentication);
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

    private void executeAuthRequest(final CharSequence pLogin,
                                    final CharSequence pPassword,
                                    final IHttpClient.IRequest<String> pRequest,
                                    final int pErrorMessageResId) {
        mThreadManager.execute(
                () -> {
                    final String login = pLogin.toString();
                    final String password = pPassword.toString();
                    String token;
                    try {
                        final String response = mHttpClient.executeRequest(pRequest);
                        token = ObserverJsonParser.getTokenFromResponseString(response);
                    } catch (IOException pEx) {
                        token = null;
                    }
                    if (!TextUtils.isEmpty(token)) {
                        final Bundle accountBundle = AccountUtil.createAccountBundle(
                                login, password, token);
                        getView().get().onDataChanged(accountBundle);
                    } else {
                        IOUtil.showToast(getContext(), getContext().getString(pErrorMessageResId));
                    }
                });
    }
}

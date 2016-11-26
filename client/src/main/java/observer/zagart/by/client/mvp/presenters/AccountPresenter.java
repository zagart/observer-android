package observer.zagart.by.client.mvp.presenters;

import android.text.TextUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import observer.zagart.by.client.App;
import observer.zagart.by.client.R;
import observer.zagart.by.client.application.accounts.ObserverAccount;
import observer.zagart.by.client.application.constants.Services;
import observer.zagart.by.client.application.managers.ThreadManager;
import observer.zagart.by.client.application.utils.AccountUtil;
import observer.zagart.by.client.application.utils.IOUtil;
import observer.zagart.by.client.application.utils.URIUtil;
import observer.zagart.by.client.mvp.IMvp;
import observer.zagart.by.client.mvp.models.AccountModel;
import observer.zagart.by.client.mvp.presenters.base.BasePresenter;
import observer.zagart.by.client.network.http.HttpFactory;
import observer.zagart.by.client.network.http.interfaces.IHttpClient;
import observer.zagart.by.client.network.http.requests.observer.AuthenticationRequest;
import observer.zagart.by.client.network.http.requests.observer.RegistrationRequest;
import observer.zagart.by.client.network.http.responses.ObserverJsonResponse;

/**
 * Presenter implementation for authentication views that use
 * {@link AccountModel} model.
 *
 * @author zagart
 */

public class AccountPresenter extends BasePresenter<ObserverAccount> {

    final private ThreadManager mThreadManager;
    final private IHttpClient mHttpClient;

    @SuppressWarnings("WrongConstant")
    public AccountPresenter(final IMvp.IViewOperations<ObserverAccount> pView) {
        super(pView, new AccountModel(URIUtil.getAccountUri()));
        mThreadManager = (ThreadManager) App.getContext().getSystemService(Services.THREAD_MANAGER);
        mHttpClient = HttpFactory.getDefaultClient();
    }

    public void persistAccount(final ObserverAccount pAccount) {
        getModel().persist(pAccount);
        AccountUtil.setCurrentAccount(pAccount);
    }

    public void executeRegistration(final CharSequence pLogin, final CharSequence pPassword) {
        executeUserRequest(
                pLogin,
                pPassword,
                new RegistrationRequest(pLogin.toString(), pPassword.toString()),
                R.string.err_registration_failed);
    }

    public void executeAuthentication(final CharSequence pLogin, final CharSequence pPassword) {
        executeUserRequest(
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
    public void startDataReload() {
        getView().get().onDataChanged(null);
    }

    @Override
    public void synchronizeModel(final IHttpClient.IRequest<String> pRequest) {
        //TODO sync adapter instead
    }

    @Override
    public void clearModel() {
        getModel().deleteAll();
    }

    private void executeUserRequest(final CharSequence pLogin,
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
                        token = new ObserverJsonResponse(response).extractToken();
                    } catch (IOException pEx) {
                        token = null;
                    }
                    if (!TextUtils.isEmpty(token)) {
                        final ObserverAccount account = new ObserverAccount(login);
                        final ArrayList<ObserverAccount> accounts = new ArrayList<>();
                        accounts.add(account);
                        account.setToken(token);
                        account.setPassword(password);
                        getView().get().onDataChanged(accounts);
                    } else {
                        IOUtil.showToast(getContext(), getContext().getString(pErrorMessageResId));
                    }
                });
    }
}

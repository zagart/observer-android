package observer.zagart.by.client.mvp.presenters;

import android.annotation.SuppressLint;
import android.text.TextUtils;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import observer.zagart.by.client.App;
import observer.zagart.by.client.R;
import observer.zagart.by.client.application.accounts.ObserverAccount;
import observer.zagart.by.client.application.constants.ExceptionConstants;
import observer.zagart.by.client.application.constants.Services;
import observer.zagart.by.client.application.managers.ThreadManager;
import observer.zagart.by.client.application.utils.URIUtil;
import observer.zagart.by.client.mvp.IMvp;
import observer.zagart.by.client.mvp.models.AccountModel;
import observer.zagart.by.client.mvp.presenters.base.BasePresenter;
import observer.zagart.by.client.mvp.views.LoginActivity;
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
@SuppressWarnings("WrongConstant")
@SuppressLint("ServiceCast")
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
        App.setAccount(pAccount);
    }

    public void executeRegistration(final CharSequence pLogin, final CharSequence pPassword) {
        executeUserRequest(
                pLogin,
                pPassword,
                new RegistrationRequest(pLogin.toString(), pPassword.toString()),
                R.string.registration_failed);
    }

    public void executeAuthentication(final CharSequence pLogin, final CharSequence pPassword) {
        executeUserRequest(
                pLogin,
                pPassword,
                new AuthenticationRequest(pLogin.toString(), pPassword.toString()),
                R.string.authentication_fail);
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
                                    final int pMessageID) {
        mThreadManager.execute(
                () -> {
                    final LoginActivity view = (LoginActivity) getView().get();
                    mThreadManager.post(view::showProgressDialog);
                    final String login = pLogin.toString();
                    final String password = pPassword.toString();
                    final String token;
                    final String response;
                    try {
                        response = mHttpClient.executeRequest(pRequest);
                        token = new ObserverJsonResponse(response).extractToken();
                    } catch (IOException | JSONException pEx) {
                        throw new RuntimeException(ExceptionConstants.REQUEST_EXCEPTION);
                    } finally {
                        mThreadManager.post(view::dismissProgressDialog);
                    }
                    if (!TextUtils.isEmpty(token)) {
                        final ObserverAccount account = new ObserverAccount(login);
                        final ArrayList<ObserverAccount> accounts = new ArrayList<>();
                        accounts.add(account);
                        account.setToken(token);
                        account.setPassword(password);
                        view.onDataChanged(accounts);
                    } else {
                        view.showSnackBar(pMessageID);
                    }
                });
    }
}

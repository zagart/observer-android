package observer.zagart.by.client.mvp.presenters;

import android.accounts.Account;
import android.accounts.AccountAuthenticatorActivity;
import android.accounts.AccountManager;
import android.annotation.TargetApi;
import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import observer.zagart.by.client.App;
import observer.zagart.by.client.R;
import observer.zagart.by.client.account.ObserverAccount;
import observer.zagart.by.client.backend.api.ObserverApi;
import observer.zagart.by.client.backend.api.ObserverCallback;
import observer.zagart.by.client.http.interfaces.IHttpClient;
import observer.zagart.by.client.mvp.MVP;
import observer.zagart.by.client.threadings.ThreadWorker;
import observer.zagart.by.client.utils.IOUtil;

/**
 * Responsibility for creating and managing accounts lies on {@link AccountManager},
 * so presenter for views and models that related to account is kinda specific.
 *
 * @author zagart
 */

public class AccountPresenter implements MVP.IPresenterOperations<Account> {

    private WeakReference<MVP.IViewOperations> mView;
    private ThreadWorker mThreadWorker;

    public AccountPresenter(final MVP.IViewOperations pView) {
        mView = new WeakReference<>(pView);
        mThreadWorker = App.getState().getThreadWorker();
    }

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
                                login,
                                password
                        );
                        if (!TextUtils.isEmpty(token)) {
                            final ObserverAccount account = new ObserverAccount(login);
                            ObserverCallback.onTokenReceived(
                                    pActivity,
                                    account,
                                    password,
                                    token
                            );
                        } else {
                            final Context viewContext = mView.get().getViewContext();
                            IOUtil.showToast(
                                    viewContext,
                                    viewContext.getString(R.string.err_registration_failed)
                            );
                        }
                    }
                }
        );
    }

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
                                login,
                                password
                        );
                        if (!TextUtils.isEmpty(token)) {
                            final ObserverAccount account = new ObserverAccount(login);
                            ObserverCallback.onTokenReceived(
                                    pActivity,
                                    account,
                                    password,
                                    token
                            );
                        } else {
                            final Context viewContext = mView.get().getViewContext();
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
    public void onCreate(final MVP.IModelOperations<Account> pModel) {
    }

    @SuppressWarnings("MissingPermission")
    @Override
    public List<Account> getElementsFromModel(final Uri pUri) {
        final AccountManager accountManager = AccountManager.get(mView.get().getViewContext());
        final List<Account> accounts = new ArrayList<>();
        final Account[] accountsArray = accountManager.getAccounts();
        Collections.addAll(accounts, accountsArray);
        return accounts;
    }

    @Override
    public void synchronizeModel(final Uri pUri, final IHttpClient.IRequest<String> pRequest) {
        //TODO sync adapter instead
    }

    @SuppressWarnings("MissingPermission")
    @Override
    @TargetApi(22)
    public void clearModel(final Uri pUri) {
        final AccountManager accountManager = AccountManager.get(mView.get().getViewContext());
        final Account[] accounts = accountManager.getAccounts();
        for (Account account : accounts) {
            accountManager.removeAccount(account, null, null, null);
        }
    }
}
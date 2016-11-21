package observer.zagart.by.client.mvp.models;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import observer.zagart.by.client.App;
import observer.zagart.by.client.R;
import observer.zagart.by.client.application.accounts.ObserverAccount;
import observer.zagart.by.client.mvp.IMvp;

/**
 * IMvp model implementation for {@link ObserverAccount}.
 *
 * @author zagart
 */

public class AccountModel implements IMvp.IModelOperations<ObserverAccount> {

    @SuppressWarnings("MissingPermission")
    @Override
    public List<ObserverAccount> retrieveAll() {
        final AccountManager accountManager = AccountManager.get(App.getContext());
        final List<ObserverAccount> accounts = new ArrayList<>();
        final Account[] accountsArray = accountManager.getAccounts();
        for (Account account : accountsArray) {
            if (account.type.equals(ObserverAccount.TYPE)) {
                accounts.add((ObserverAccount) account);
            }
        }
        return accounts;
    }

    @Override
    public void persistAll(final List<ObserverAccount> pObserverAccounts) {
        for (ObserverAccount account : pObserverAccounts) {
            persist(account);
        }
    }

    @Override
    public Long persist(final ObserverAccount pAccount) {
        final AccountManager accountManager = AccountManager.get(App.getContext());
        final Bundle result = new Bundle();
        if (accountManager.addAccountExplicitly(pAccount, pAccount.getPassword(), new Bundle())) {
            result.putString(AccountManager.KEY_ACCOUNT_NAME, pAccount.name);
            result.putString(AccountManager.KEY_ACCOUNT_TYPE, pAccount.type);
            result.putString(AccountManager.KEY_AUTHTOKEN, pAccount.getToken());
            accountManager.setAuthToken(pAccount, pAccount.type, pAccount.getToken());
        } else {
            result.putString(
                    AccountManager.KEY_ERROR_MESSAGE,
                    App.getContext().getString(R.string.error_account_exists));
        }
        return null;
    }

    @SuppressWarnings({"MissingPermission", "deprecation"})
    @Override
    public void deleteAll() {
        final AccountManager accountManager = AccountManager.get(App.getContext());
        final Account[] accounts = accountManager.getAccounts();
        for (Account account : accounts) {
            if (account.type.equals(ObserverAccount.TYPE)) {
                accountManager.removeAccount(account, null, null);
            }
        }
    }
}

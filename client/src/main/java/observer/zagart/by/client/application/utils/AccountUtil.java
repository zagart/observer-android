package observer.zagart.by.client.application.utils;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.os.Bundle;

import observer.zagart.by.client.App;
import observer.zagart.by.client.application.accounts.ObserverAccount;
import observer.zagart.by.client.application.constants.ApplicationConstants;

/**
 * Utility class with methods for work with accounts.
 *
 * @author zagart
 */
public class AccountUtil {

    @SuppressWarnings("MissingPermission")
    public static Account getPersistedAccount() {
        final String accountName = SharedPreferencesUtil.retrieveStringValue(
                App.getContext(),
                ApplicationConstants.CURRENT_ACCOUNT_NAME);
        final AccountManager accountManager = AccountManager.get(App.getContext());
        for (Account account : accountManager.getAccounts()) {
            if (account.name.equals(accountName)) {
                return account;
            }
        }
        return null;
    }

    public static Bundle createAccountBundle(final String pLogin,
                                             final String pPassword,
                                             final String pToken) {
        final Bundle account = new Bundle();
        account.putString(ObserverAccount.NAME, pLogin);
        account.putString(ObserverAccount.PASSWORD, pPassword);
        account.putString(ObserverAccount.TOKEN, pToken);
        return account;
    }

    public static ObserverAccount parseAccountBundle(final Bundle pAccountBundle) {
        ObserverAccount account = new ObserverAccount(
                pAccountBundle.getString(ObserverAccount.NAME));
        account
                .setPassword(pAccountBundle.getString(ObserverAccount.PASSWORD))
                .setToken(pAccountBundle.getString(ObserverAccount.TOKEN));
        return account;
    }

    public static void setCurrentAccount(final Account pAccount) {
        if (pAccount != null) {
            SharedPreferencesUtil.persistStringValue(
                    App.getContext(),
                    ApplicationConstants.CURRENT_ACCOUNT_NAME,
                    pAccount.name);
            App.setAccount(pAccount);
        } else {
            SharedPreferencesUtil.persistStringValue(
                    App.getContext(),
                    ApplicationConstants.CURRENT_ACCOUNT_NAME,
                    null);
            App.setAccount(null);
        }
    }
}

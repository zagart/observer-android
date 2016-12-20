package observer.zagart.by.client.application.managers;

import android.accounts.Account;
import android.accounts.AccountManager;

import observer.zagart.by.client.App;
import observer.zagart.by.client.application.accounts.ObserverAccount;
import observer.zagart.by.client.application.constants.ApplicationConstants;
import observer.zagart.by.client.application.utils.SharedPreferencesUtil;

/**
 * Service for managing application accounts.
 *
 * @author zagart
 * @see ObserverAccount
 */

public class AccountService {

    private Account mAccount;

    @SuppressWarnings("MissingPermission")
    public static Account findPersistedAccount() {
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

    public Account getAccount() {
        return mAccount;
    }

    public void setAccount(final Account pAccount) {
        if (pAccount != null) {
            SharedPreferencesUtil.persistStringValue(
                    App.getContext(),
                    ApplicationConstants.CURRENT_ACCOUNT_NAME,
                    pAccount.name);
        } else {
            SharedPreferencesUtil.persistStringValue(
                    App.getContext(),
                    ApplicationConstants.CURRENT_ACCOUNT_NAME,
                    null);
        }
        mAccount = pAccount;
    }
}

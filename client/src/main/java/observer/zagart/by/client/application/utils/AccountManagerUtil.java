package observer.zagart.by.client.application.utils;

import android.accounts.Account;
import android.accounts.AccountManager;

import observer.zagart.by.client.App;
import observer.zagart.by.client.application.constants.Constants;

/**
 * Utility class with methods to access
 *
 * @author zagart
 */
public class AccountManagerUtil {

    @SuppressWarnings("MissingPermission")
    public static Account getPersistedAccount() {
        final String accountName = SharedPreferencesUtil.retrieveStringValue(
                App.getContext(),
                Constants.CURRENT_ACCOUNT_NAME);
        final AccountManager accountManager = AccountManager.get(App.getContext());
        for (Account account : accountManager.getAccounts()) {
            if (account.name.equals(accountName)) {
                return account;
            }
        }
        return null;
    }
}

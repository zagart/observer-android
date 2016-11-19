package observer.zagart.by.client.utils;

import android.accounts.Account;
import android.accounts.AccountManager;

import observer.zagart.by.client.App;
import observer.zagart.by.client.constants.Constants;

/**
 * Utility class with methods to access
 *
 * @author zagart
 */
public class AccountManagerUtil {

    @SuppressWarnings("MissingPermission")
    public static Account getPersistedAccount() {
        final String accountName = SharedPreferencesUtil.retrieveStringValue(
                App.getState().getContext(),
                Constants.CURRENT_ACCOUNT_NAME);
        AccountManager accountManager = AccountManager.get(App.getState().getContext());
        for (Account account : accountManager.getAccounts()) {
            if (account.name.equals(accountName)) {
                return account;
            }
        }
        return null;
    }
}

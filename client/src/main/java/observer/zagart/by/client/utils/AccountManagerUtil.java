package observer.zagart.by.client.utils;

import android.accounts.Account;
import android.accounts.AccountManager;

import observer.zagart.by.client.App;
import observer.zagart.by.client.singletons.ContextHolder;

/**
 * Utility class with methods to access
 *
 * @author zagart
 */
public class AccountManagerUtil {

    @SuppressWarnings("MissingPermission")
    public static Account getPersistedAccount() {
        final String accountName = SharedPreferencesUtil.retrieveStringValue(
                ContextHolder.get(),
                App.CURRENT_ACCOUNT_NAME
        );
        AccountManager accountManager = AccountManager.get(ContextHolder.get());
        for (Account account : accountManager.getAccounts()) {
            if (account.name.equals(accountName)) {
                return account;
            }
        }
        return null;
    }
}

package observer.zagart.by.client.application.singletons;

import android.accounts.Account;

/**
 * Singleton contains account of current user.
 *
 * @author zagart
 */
public enum AccountHolder {
    INSTANCE;
    private Account mAccount;

    public static Account get() {
        return INSTANCE.mAccount;
    }

    public static void set(final Account pAccount) {
        INSTANCE.mAccount = pAccount;
    }
}

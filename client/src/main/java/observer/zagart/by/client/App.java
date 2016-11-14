package observer.zagart.by.client;

import android.app.Application;

import observer.zagart.by.client.singletons.AccountHolder;
import observer.zagart.by.client.singletons.ContextHolder;
import observer.zagart.by.client.utils.AccountManagerUtil;

/**
 * Custom application file.
 */
public class App extends Application {

    public static final String CURRENT_ACCOUNT_NAME = "current_account_name";

    @Override
    public void onCreate() {
        ContextHolder.set(this);
        AccountHolder.set(AccountManagerUtil.getPersistedAccount());
    }
}

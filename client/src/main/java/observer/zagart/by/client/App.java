package observer.zagart.by.client;

import android.accounts.Account;
import android.app.Application;
import android.content.Context;

import observer.zagart.by.client.application.managers.DatabaseManager;
import observer.zagart.by.client.application.managers.ThreadManager;
import observer.zagart.by.client.application.singletons.AccountHolder;
import observer.zagart.by.client.application.singletons.ContextHolder;
import observer.zagart.by.client.application.utils.AccountManagerUtil;

/**
 * Observer main application file.
 *
 * @author zagart
 */
public class App extends Application {

    //TODO configuration of ProGuard
    //TODO should be a singletons..?
    public static Account getAccount() {
        return AccountHolder.get();
    }

    public static void setAccount(final Account pAccount) {
        AccountHolder.set(pAccount);
    }

    public static Context getContext() {
        return ContextHolder.get();
    }

    public static DatabaseManager getDatabaseManager() {
        return DatabaseManager.getDefaultInstance();
    }

    public static ThreadManager getThreadManager() {
        return ThreadManager.getDefaultInstance();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        ContextHolder.set(this);
        setAccount(AccountManagerUtil.getPersistedAccount());
    }
}

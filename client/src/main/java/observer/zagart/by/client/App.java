package observer.zagart.by.client;

import android.accounts.Account;
import android.app.Application;
import android.content.Context;

import java.util.HashMap;
import java.util.Map;

import observer.zagart.by.client.application.constants.Services;
import observer.zagart.by.client.application.managers.DatabaseManager;
import observer.zagart.by.client.application.managers.ThreadManager;
import observer.zagart.by.client.application.singletons.AccountHolder;
import observer.zagart.by.client.application.singletons.ContextHolder;
import observer.zagart.by.client.application.utils.AccountUtil;

/**
 * Observer main application file.
 *
 * @author zagart
 */
public class App extends Application {

    final private Map<String, Object> mServices = new HashMap<String, Object>() {

        {
            put(Services.DATABASE_MANAGER, new DatabaseManager(ContextHolder.get()));
            put(Services.THREAD_MANAGER, new ThreadManager<>());
        }
    };

    //TODO remove all logs by proguard
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

    public static ThreadManager getThreadManager() {
        return ThreadManager.getDefaultInstance();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        ContextHolder.set(this);
        setAccount(AccountUtil.getPersistedAccount());
    }

    @SuppressWarnings("WrongConstant")
    @Override
    public Object getSystemService(final String pName) {
        switch (pName) {
            case Services.CONTEXT:
                return ContextHolder.get();
            case Services.DATABASE_MANAGER:
                return mServices.get(Services.DATABASE_MANAGER);
            case Services.THREAD_MANAGER:
                return mServices.get(Services.THREAD_MANAGER);
            default:
                return super.getSystemService(pName);
        }
    }
}

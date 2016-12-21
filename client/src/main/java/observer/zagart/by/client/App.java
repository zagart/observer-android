package observer.zagart.by.client;

import android.accounts.Account;
import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.util.Log;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

import observer.zagart.by.client.application.constants.Services;
import observer.zagart.by.client.application.managers.AccountService;
import observer.zagart.by.client.application.managers.DatabaseManager;
import observer.zagart.by.client.application.managers.ThreadManager;
import observer.zagart.by.client.application.singletons.ContextHolder;

/**
 * Observer main application file.
 *
 * @author zagart
 */
//TODO use progress bars
//TODO configuration of ProGuard
//TODO unit&instrumental test
//TODO connection timeout handling
//TODO input/output exceptions handling
@SuppressWarnings("WrongConstant")
@SuppressLint("ServiceCast")
public class App extends Application {

    public static final String APP_PATH = "blabla";
    final private Map<String, Object> mServices = new HashMap<String, Object>() {

        {
            put(Services.DATABASE_MANAGER, new DatabaseManager(ContextHolder.get()));
            put(Services.THREAD_MANAGER, new ThreadManager<>());
            put(Services.ACCOUNT_SERVICE, new AccountService());
        }
    };

    public static Account getAccount() {
        final AccountService accountService = (AccountService) getContext()
                .getSystemService(Services.ACCOUNT_SERVICE);
        return accountService.getAccount();
    }

    public static void setAccount(final Account pAccount) {
        final AccountService accountService = (AccountService) getContext()
                .getSystemService(Services.ACCOUNT_SERVICE);
        accountService.setAccount(pAccount);
    }

    public static Context getContext() {
        return ContextHolder.get();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        ContextHolder.set(this);
        setAccount(AccountService.findPersistedAccount());
    }

    @SuppressWarnings("WrongConstant")
    @Override
    public Object getSystemService(final String pName) {
        final Object customService = mServices.get(pName);
        if (customService != null) {
            return customService;
        } else {
            return super.getSystemService(pName);
        }
    }

}

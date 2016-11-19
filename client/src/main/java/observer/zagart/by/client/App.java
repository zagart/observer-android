package observer.zagart.by.client;

import android.accounts.Account;
import android.app.Application;
import android.content.Context;

import java.lang.ref.WeakReference;

import observer.zagart.by.client.repository.helper.DbHelper;
import observer.zagart.by.client.singletons.State;
import observer.zagart.by.client.threadings.ThreadWorker;
import observer.zagart.by.client.utils.AccountManagerUtil;

/**
 * Custom application file.
 */
public class App extends Application {

    private static State mState;
    //TODO singletone
    private WeakReference<Context> mContextWeakReference;
    private Account mAccount;
    private ThreadWorker mThreadWorker;
    private DbHelper mHelper;

    public static State getState() {
        return mState;
    }

    public static Account getAccount() {
        return null;
    }

    public static DbHelper getDbHelper() {
        return null;
    }

    public static ThreadWorker getThreadWorker() {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mState = new State(this);
        mState
                .setAccount(AccountManagerUtil.getPersistedAccount())
                .setThreadWorker(new ThreadWorker())
                .setDbHelper(new DbHelper(this));
    }
}

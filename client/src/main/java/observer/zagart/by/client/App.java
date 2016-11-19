package observer.zagart.by.client;

import android.app.Application;

import observer.zagart.by.client.repository.helper.DbHelper;
import observer.zagart.by.client.singletons.State;
import observer.zagart.by.client.threadings.ThreadWorker;
import observer.zagart.by.client.utils.AccountManagerUtil;

/**
 * Custom application file.
 */
public class App extends Application {

    //TODO singletone
    private static State mState;

    public static State getState() {
        return mState;
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

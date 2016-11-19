package observer.zagart.by.client;

import android.app.Application;

import observer.zagart.by.client.application.managers.DatabaseManager;
import observer.zagart.by.client.application.managers.ThreadManager;
import observer.zagart.by.client.application.singletons.State;
import observer.zagart.by.client.application.utils.AccountManagerUtil;

/**
 * Custom application file.
 */
public class App extends Application {

    //TODO singleton
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
                .setThreadManager(new ThreadManager())
                .setDatabaseManager(new DatabaseManager(this));
    }
}

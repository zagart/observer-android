package observer.zagart.by.client.application.singletons;

import android.accounts.Account;
import android.content.Context;

import java.lang.ref.WeakReference;

import observer.zagart.by.client.application.constants.Constants;
import observer.zagart.by.client.application.managers.DatabaseManager;
import observer.zagart.by.client.application.managers.ThreadManager;

/**
 * Class is responsible for storing application's state and it's
 * main executors, such as thread manager and database helper.
 */
public class State {

    private WeakReference<Context> mContextWeakReference;
    private Account mAccount;
    private ThreadManager mThreadManager;
    private DatabaseManager mHelper;

    public State(Context pContext) {
        mContextWeakReference = new WeakReference<>(pContext);
    }

    public DatabaseManager getDatabaseManager() {
        if (mHelper == null) {
            throw new RuntimeException(Constants.DB_HELPER_IS_NULL);
        }
        return mHelper;
    }

    public State setDatabaseManager(final DatabaseManager pHelper) {
        mHelper = pHelper;
        return this;
    }

    public Account getAccount() {
        return mAccount;
    }

    public State setAccount(Account pAccount) {
        mAccount = pAccount;
        return this;
    }

    public ThreadManager getThreadManager() {
        if (mThreadManager == null) {
            throw new RuntimeException(Constants.THREAD_WORKER_IS_NULL);
        }
        return mThreadManager;
    }

    public State setThreadManager(ThreadManager pThreadManager) {
        mThreadManager = pThreadManager;
        return this;
    }

    public Context getContext() {
        if (mContextWeakReference == null) {
            throw new RuntimeException(Constants.CONTEXT_IS_NULL);
        }
        return mContextWeakReference.get();
    }
}

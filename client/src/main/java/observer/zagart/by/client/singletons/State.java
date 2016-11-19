package observer.zagart.by.client.singletons;

import android.accounts.Account;
import android.content.Context;

import java.lang.ref.WeakReference;

import observer.zagart.by.client.constants.Constants;
import observer.zagart.by.client.repository.helper.DbHelper;
import observer.zagart.by.client.threadings.ThreadWorker;

/**
 * Class is responsible for storing application's state and it's
 * main executors, such as thread manager and database helper.
 */
public class State {

    private WeakReference<Context> mContextWeakReference;
    private Account mAccount;
    private ThreadWorker mThreadWorker;
    private DbHelper mHelper;

    public State(Context pContext) {
        mContextWeakReference = new WeakReference<>(pContext);
    }

    public DbHelper getDbHelper() {
        if (mHelper == null) {
            throw new RuntimeException(Constants.DB_HELPER_IS_NULL);
        }
        return mHelper;
    }

    public State setDbHelper(final DbHelper pHelper) {
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

    public ThreadWorker getThreadWorker() {
        if (mThreadWorker == null) {
            throw new RuntimeException(Constants.THREAD_WORKER_IS_NULL);
        }
        return mThreadWorker;
    }

    public State setThreadWorker(ThreadWorker pThreadWorker) {
        mThreadWorker = pThreadWorker;
        return this;
    }

    public Context getContext() {
        if (mContextWeakReference == null) {
            throw new RuntimeException(Constants.CONTEXT_IS_NULL);
        }
        return mContextWeakReference.get();
    }
}
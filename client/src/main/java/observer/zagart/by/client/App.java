package observer.zagart.by.client;

import android.accounts.Account;
import android.app.Application;
import android.content.Context;

import java.lang.ref.WeakReference;

import observer.zagart.by.client.constants.Constants;
import observer.zagart.by.client.exceptions.ExceptionHandler;
import observer.zagart.by.client.repository.helper.DbHelper;
import observer.zagart.by.client.threadings.ThreadWorker;
import observer.zagart.by.client.utils.AccountManagerUtil;

/**
 * Custom application file.
 */
public class App extends Application {

    private static State mState;

    public static State getState() {
        return mState;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler());
        mState = new State(this);
        mState
                .setAccount(AccountManagerUtil.getPersistedAccount())
                .setThreadWorker(new ThreadWorker())
                .setDbHelper(new DbHelper(this));
    }

    /**
     * Class is responsible for storing application's state and it's
     * main executors, such as thread manager and database helper.
     */
    public class State {

        private WeakReference<Context> mContextWeakReference;
        private Account mAccount;
        private ThreadWorker mThreadWorker;
        private DbHelper mHelper;

        State(Context pContext) {
            mContextWeakReference = new WeakReference<>(pContext);
        }

        public DbHelper getDbHelper() {
            if (mHelper == null) {
                throw new RuntimeException(Constants.DB_HELPER_IS_NULL);
            }
            return mHelper;
        }

        State setDbHelper(final DbHelper pHelper) {
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

        State setThreadWorker(ThreadWorker pThreadWorker) {
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
}

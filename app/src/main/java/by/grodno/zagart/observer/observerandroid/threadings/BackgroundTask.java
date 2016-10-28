package by.grodno.zagart.observer.observerandroid.threadings;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import java.util.Locale;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import by.grodno.zagart.observer.observerandroid.BuildConfig;
import by.grodno.zagart.observer.observerandroid.interfaces.IAction;
import by.grodno.zagart.observer.observerandroid.interfaces.ICallback;
import by.grodno.zagart.observer.observerandroid.singletons.ContextHolder;

/**
 * My class for asynchronous background tasks.
 */
public class BackgroundTask<Progress, Action extends IAction, Result> {
    private static final int COUNT_CORE = Runtime.getRuntime().availableProcessors();
    private static final int DEFAULT_THREADS_NUMBER = 3;
    private static final int MAX_THREADS_NUMBER = Math.max(COUNT_CORE, DEFAULT_THREADS_NUMBER);
    private static final String STR_ROTATE = "Screen orientation changed.";
    private static int sCounter = 0;
    private final ExecutorService mPool;
    private final String mName;
    private final BlockingQueue<Action> mActions = new ArrayBlockingQueue<>(
            MAX_THREADS_NUMBER
    );
    private Handler mHandler;

    public BackgroundTask(String pName) {
        mName = pName;
        mPool = Executors.newFixedThreadPool(MAX_THREADS_NUMBER);
    }

    public void onResult(Result pResult) {
        if (BuildConfig.DEBUG) {
            Toast.makeText(ContextHolder.get(), (String) pResult, Toast.LENGTH_SHORT).show();
        }
        //handling result
    }

    public void onRotate() {
        if (BuildConfig.DEBUG) {
            Log.d(mName, STR_ROTATE);
        }
        //saving pool state
    }

    public void onStart() {
        //do restoring actions
    }

    /**
     * Method adds action for executing in new background thread. If
     * result of executing not null, then it possible print result in UI-thread
     * using method {@link #onResult(Result)}.
     *
     * @param pAction   Action for executing
     * @param pCallback Callback implementation for action
     */
    public <Param> void performAction(
            final Action pAction,
            final ICallback<Progress, Result> pCallback,
            final Param... pParam
    ) {
        mHandler = new Handler();
        final String name = String.format(Locale.ENGLISH, "%s-%d", mName, ++sCounter);
        mPool.execute(
                new Runnable() {
                    @Override
                    public void run() {
                        try {
                            mActions.offer(pAction);
                            final Result result = (Result) pAction.process(pCallback, pParam);
                            if (result != null) {
                                mHandler.post(
                                        new Runnable() {
                                            @Override
                                            public void run() {
                                                onResult(result);
                                            }
                                        }
                                );
                            }
                        } catch (Exception pEx) {
                            pCallback.onException(name, pEx);
                        }
                    }
                }
        );
    }

    public void doInUiThread(Runnable pRunnable) {
        mHandler.post(pRunnable);
    }
}

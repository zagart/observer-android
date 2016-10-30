package by.grodno.zagart.observer.observerandroid.threadings;
import android.os.Handler;
import android.util.Log;

import java.util.Locale;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import by.grodno.zagart.observer.observerandroid.BuildConfig;
import by.grodno.zagart.observer.observerandroid.interfaces.IAction;
import by.grodno.zagart.observer.observerandroid.interfaces.ICallback;

/**
 * My class for asynchronous background tasks.
 */
public class ThreadWorker<Result> {
    private static final int COUNT_CORE = Runtime.getRuntime().availableProcessors();
    private static final int DEFAULT_THREADS_NUMBER = 3;
    private static final int MAX_THREADS_NUMBER = Math.max(COUNT_CORE, DEFAULT_THREADS_NUMBER);
    private static final String STR_ROTATE = "Screen orientation changed.";
    public static final String TAG = ThreadWorker.class.getSimpleName();
    private static int sCounter = 0;
    private final ExecutorService mPool;
    private final String mName;
    private final BlockingQueue<IAction> mActions = new ArrayBlockingQueue<>(
            MAX_THREADS_NUMBER
    );
    private Handler mHandler;

    public ThreadWorker() {
        this(TAG);
    }

    public ThreadWorker(String pName) {
        mName = pName;
        mPool = Executors.newFixedThreadPool(MAX_THREADS_NUMBER);
        mHandler = new Handler();
    }

    public static ThreadWorker getDefaultInstance() {
        return SingletonHolder.WORKER_INSTANCE;
    }

    /**
     * Executes {@link Runnable} implementation on background thread.
     *
     * @param pRunnable Object to run
     */
    public void execute(Runnable pRunnable) {
        mPool.execute(pRunnable);
    }

    /**
     * When {@link #performAction(IAction, ICallback, Object[])} executes IAction implementation
     * method {@link IAction#process(ICallback, Object[])}, then result of executing will be
     * processed in <em>UI-thread</> if it is not null using that method.
     * <p>
     * Require to be override. Handle result in override method body.
     *
     * @param pResult Data to handle in UI-thread
     * @see IAction
     */
    public void onResult(Result pResult) {
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
     * using method {@link #onResult(Object)}.
     *
     * @param pAction   Action for executing
     * @param pCallback Callback implementation for action
     */
    public <Param, Progress> void performAction(
            final IAction<Param, Progress, Result> pAction,
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
                            final Result result = pAction.process(pCallback, pParam);
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

    /**
     * Executes {@link Runnable} implementation on UI-thread.
     *
     * @param pRunnable Object to run
     */
    public void post(Runnable pRunnable) {
        mHandler.post(pRunnable);
    }

    public static class SingletonHolder {
        public static final ThreadWorker WORKER_INSTANCE = new ThreadWorker(TAG);
    }
}

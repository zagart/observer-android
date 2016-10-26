package by.grodno.zagart.observer.observerandroid.threadings.impl;
import by.grodno.zagart.observer.observerandroid.threadings.IThreadAction;
import by.grodno.zagart.observer.observerandroid.threadings.IActionCallback;

import static java.lang.Thread.sleep;

/**
 * IThreadAction interface implementation.
 */
public class IThreadActionImpl implements IThreadAction<String, Integer> {
    private static final int LOOP_VALUE = 5;
    private static final int SLEEP_TIME_MILLIS = 1000;
    private Status mStatus = Status.CREATED;

    public Status getStatus() {
        return mStatus;
    }

    @Override
    public void initPersistProgress() {
        mStatus = Status.PERSISTED;
    }

    @Override
    public String process(final String pName, final IActionCallback<String, Integer> pCallback)
            throws InterruptedException {
        mStatus = Status.STARTED;
        pCallback.onStart(pName);
        String result = "";
        int i = 0;
        while (++i < LOOP_VALUE) {
            if (mStatus == Status.PERSISTED) {
                //do persist progress and exit
            } else if (mStatus != Status.RUNNING) {
                mStatus = Status.RUNNING;
            }
            pCallback.onUpdate(pName, i);
            result += i;
            sleep(SLEEP_TIME_MILLIS);
        }
        mStatus = Status.FINISHED;
        pCallback.onComplete(pName, result);
        return result;
    }

    @Override
    public Integer retrieveProgress() {
        //do retrieve progress
        return null;
    }
}

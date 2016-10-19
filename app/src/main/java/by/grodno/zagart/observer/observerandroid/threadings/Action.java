package by.grodno.zagart.observer.observerandroid.threadings;
/**
 * Interface defines behavior of classes that are responsible for
 * processing background data.
 */
public interface Action<Result, Progress> {
    Status getStatus();
    void initPersistProgress();
    Result process(final String name, final Callback<Result, Progress> pCallback) throws InterruptedException;
    Progress retrieveProgress();
    enum Status {
        CREATED, STARTED, RUNNING, FINISHED, PERSISTED
    }
}

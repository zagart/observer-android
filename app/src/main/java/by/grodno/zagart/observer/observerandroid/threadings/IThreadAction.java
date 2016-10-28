package by.grodno.zagart.observer.observerandroid.threadings;
import by.grodno.zagart.observer.observerandroid.interfaces.IAction;

/**
 * Interface defines behavior of classes that are responsible for
 * processing background data.
 */
public interface IThreadAction<Param, Progress, Result> extends IAction<Param, Progress, Result> {
    Status getStatus();
    void initPersistProgress();
    Progress retrieveProgress();
    enum Status {
        CREATED, STARTED, RUNNING, FINISHED, PERSISTED
    }
}

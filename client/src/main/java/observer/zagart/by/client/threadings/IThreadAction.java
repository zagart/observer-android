package observer.zagart.by.client.threadings;
import observer.zagart.by.client.interfaces.IAction;

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

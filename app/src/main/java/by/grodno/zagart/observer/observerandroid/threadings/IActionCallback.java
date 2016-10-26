package by.grodno.zagart.observer.observerandroid.threadings;
/**
 * Callback events for Actions.
 */
public interface IActionCallback<Progress, Result> {
    void onComplete(final String name, final Result pResult);
    void onException(final String pName, Exception pEx);
    void onStart(final String pName);
    void onUpdate(final String pName, Progress pProgress);
}

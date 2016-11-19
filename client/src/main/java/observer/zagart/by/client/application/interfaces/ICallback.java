package observer.zagart.by.client.application.interfaces;

/**
 * Callback events.
 */
public interface ICallback<Progress, Result> {

    void onComplete(final String pParam, final Result pResult);

    void onException(final String pParam, Exception pEx);

    void onStart(final String pParam);

    void onUpdate(final String pParam, Progress pProgress);
}

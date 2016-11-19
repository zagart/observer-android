package observer.zagart.by.client.application.interfaces;

/**
 * Simple result callback interface.
 *
 * @author zagart
 */
public interface IResultCallback<Param, Result> {

    Result onResult(final Param pParam);
}

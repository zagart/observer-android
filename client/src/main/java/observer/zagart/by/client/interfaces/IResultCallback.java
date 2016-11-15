package observer.zagart.by.client.interfaces;

/**
 * Simple result callback interface.
 *
 * @author zagart
 */
public interface IResultCallback<Param, Result> {

    Result onResult(final Param pParam);
}

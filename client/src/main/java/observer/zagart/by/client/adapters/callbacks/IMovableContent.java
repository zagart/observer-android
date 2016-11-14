package observer.zagart.by.client.adapters.callbacks;

/**
 * Interface shows that class allows manipulate it visible content.
 */
public interface IMovableContent {

    void onItemLock(int pStartPosition, int pEndPosition);

    void onItemRelease(int pPosition);
}

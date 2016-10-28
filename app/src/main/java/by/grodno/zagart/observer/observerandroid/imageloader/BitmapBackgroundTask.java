package by.grodno.zagart.observer.observerandroid.imageloader;
import java.io.ByteArrayOutputStream;

import by.grodno.zagart.observer.observerandroid.interfaces.IAction;
import by.grodno.zagart.observer.observerandroid.threadings.BackgroundTask;

/**
 * Background worker implementation for BitmapDrawer.
 *
 * @author zagart
 */
public class BitmapBackgroundTask extends BackgroundTask<Void, IAction, ByteArrayOutputStream> {
    public BitmapBackgroundTask(final String pName) {
        super(pName);
    }
}

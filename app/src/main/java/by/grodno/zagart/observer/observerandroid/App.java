package by.grodno.zagart.observer.observerandroid;
import android.app.Application;

import by.grodno.zagart.observer.observerandroid.singletons.ContextHolder;

/**
 * Custom application file.
 */
public class App extends Application {
    @Override
    public void onCreate() {
        ContextHolder.set(this);
    }
}

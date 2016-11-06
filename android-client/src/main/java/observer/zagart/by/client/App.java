package observer.zagart.by.client;
import android.app.Application;

import observer.zagart.by.client.singletons.ContextHolder;

/**
 * Custom application file.
 */
public class App extends Application {
    @Override
    public void onCreate() {
        ContextHolder.set(this);
    }
}

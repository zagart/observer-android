package by.grodno.zagart.observer.observerandroid;
import android.app.Application;
import android.support.v7.app.AppCompatActivity;

import by.grodno.zagart.observer.observerandroid.http.interfaces.IHttpClient;
import by.grodno.zagart.observer.observerandroid.singletons.ContextHolder;
import by.grodno.zagart.observer.observerandroid.utils.SharedPreferencesUtil;

/**
 * Custom application file.
 */
public class App extends Application {
    private static String TOKEN = SharedPreferencesUtil.retrieveStringValue(
            (AppCompatActivity) ContextHolder.get(),
            IHttpClient.IHttpData.Header.TOKEN
    );

    public static String getToken() {
        return TOKEN;
    }

    @Override
    public void onCreate() {
        ContextHolder.set(this);
    }
}

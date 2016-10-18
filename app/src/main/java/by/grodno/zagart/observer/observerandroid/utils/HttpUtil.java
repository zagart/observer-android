package by.grodno.zagart.observer.observerandroid.utils;
import android.support.v7.app.AppCompatActivity;

import java.util.Properties;

import by.grodno.zagart.observer.observerandroid.http.HttpRequestTask;
import by.grodno.zagart.observer.observerandroid.http.IHttpClient;
import by.grodno.zagart.observer.observerandroid.singletons.ContextHolder;

/**
 * HTTP-related common methods.
 */
public class HttpUtil {
    public static final String TRUSTED_USER = "trusted user";

    public static void requestObserverAuthorization(String pUrl, String pLogin, String pPassword) {
        Properties requestProperties = new Properties();
        requestProperties.put(
                IHttpClient.IRequest.Header.ACTION.name(),
                IHttpClient.IRequest.Action.AUTHORIZE.name()
        );
        requestProperties.put(
                IHttpClient.IRequest.Header.LOGIN.name(),
                pLogin
        );
        requestProperties.put(
                IHttpClient.IRequest.Header.PASSWORD.name(),
                pPassword
        );
        final HttpRequestTask task = new HttpRequestTask(
                pUrl,
                IHttpClient.IRequest.Method.POST,
                requestProperties
        );
        task.execute();
        final String result = task.getResult();
        SharedPreferencesUtil.persistStringValue(
                (AppCompatActivity) ContextHolder.get(),
                TRUSTED_USER,
                result
        );
    }
}

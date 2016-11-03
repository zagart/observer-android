package by.grodno.zagart.observer.observerandroid.utils;
import android.content.Context;
import android.widget.Toast;

import java.io.IOException;

import by.grodno.zagart.observer.observerandroid.http.HttpClientFactory;
import by.grodno.zagart.observer.observerandroid.http.observer.requests.AuthorizationRequest;
import by.grodno.zagart.observer.observerandroid.http.observer.requests.RegistrationRequest;
import by.grodno.zagart.observer.observerandroid.threadings.ThreadWorker;

/**
 * HTTP-related common methods.
 */
public class HttpUtil {
    public static final String TAG = HttpUtil.class.getSimpleName();
    private static ThreadWorker mWorker;

    static {
        mWorker = ThreadWorker.getDefaultInstance();
    }

    private HttpUtil() {
    }

    public static String sendAuthenticationRequest(
            final Context pContext,
            final String pLogin,
            final String pToken
    ) {
        final String response;
        try {
            response = HttpClientFactory.getDefaultClient().executeRequest(
                    new AuthorizationRequest(pLogin, pToken)
            );
            mWorker.post(
                    new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(
                                    pContext,
                                    response,
                                    Toast.LENGTH_SHORT
                            ).show();
                        }
                    }
            );
            return response;
        } catch (IOException pE) {
            pE.printStackTrace();
        }
        return null;
    }

    public static void sendRegistrationRequest(
            final Context pContext,
            final String pLogin,
            final String pPassword
    ) {
        final String response;
        try {
            response = HttpClientFactory.getDefaultClient().executeRequest(
                    new RegistrationRequest(pLogin, pPassword)
            );
            mWorker.post(
                    new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(
                                    pContext,
                                    response,
                                    Toast.LENGTH_SHORT
                            ).show();
                        }
                    }
            );
        } catch (IOException pE) {
            pE.printStackTrace();
        }
    }
}

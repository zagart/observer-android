package by.grodno.zagart.observer.observerandroid.utils;
import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.io.IOException;

import by.grodno.zagart.observer.observerandroid.BuildConfig;
import by.grodno.zagart.observer.observerandroid.http.HttpClientFactory;
import by.grodno.zagart.observer.observerandroid.http.interfaces.IHttpClient;
import by.grodno.zagart.observer.observerandroid.http.observer.requests.AuthenticationRequest;
import by.grodno.zagart.observer.observerandroid.http.observer.requests.RegistrationRequest;
import by.grodno.zagart.observer.observerandroid.http.observer.requests.TokenValidationRequest;
import by.grodno.zagart.observer.observerandroid.interfaces.IAction;
import by.grodno.zagart.observer.observerandroid.interfaces.ICallback;
import by.grodno.zagart.observer.observerandroid.singletons.ContextHolder;
import by.grodno.zagart.observer.observerandroid.threadings.ThreadWorker;

/**
 * HTTP-related common methods.
 */
public class HttpUtil {
    private static final String TAG = HttpUtil.class.getSimpleName();
    public static final String TOKEN_VALIDATED = "token_validated";
    public static final String TOKEN_VALIDATION_FAILED = "Token validation failed.";
    public static final String REGISTRATION = "Registration";
    public static final String AUTHENTICATION = "Authentication";
    public static final String AUTHENTICATION_FAILED = "Authentication failed";
    public static final String REGISTRATION_FAILED = "Registration failed";
    private static ThreadWorker mWorker;

    static {
        mWorker = new ThreadWorker<Boolean>() {
            @Override
            public void onResult(final Boolean pBoolean) {
                //TODO token validated logic
            }
        };
    }

    private HttpUtil() {
    }

    public static boolean isTokenValid(String pToken) {
        final boolean isValid = false;
        mWorker.performAction(
                new IAction<Void, Void, Boolean>() {
                    @Override
                    public Boolean process(final ICallback<Void, Boolean> pCallback, final Void... pParam) throws InterruptedException {
                        try {
                            final String response = HttpClientFactory
                                    .getDefaultClient()
                                    .executeRequest(
                                            new TokenValidationRequest()
                                    );
                            if (response.contains(TOKEN_VALIDATED)) {
                                return true;
                            }
                        } catch (IOException pEx) {
                            if (BuildConfig.DEBUG) {
                                Log.e(TAG, TOKEN_VALIDATION_FAILED, pEx);
                            }
                        }
                        return null;
                    }
                },
                null,
                null
        );
        return false; //TODO check if token validated
    }

    public static void sendAuthenticationRequest(
            final Context pContext,
            final String pLogin,
            final String pPassword
    ) {
        mWorker.execute(
                new Runnable() {
                    @Override
                    public void run() {
                        try {
                            final String response = HttpClientFactory
                                    .getDefaultClient()
                                    .executeRequest(
                                            new AuthenticationRequest(pLogin, pPassword)
                                    );
                            mWorker.post(
                                    new Runnable() {
                                        @Override
                                        public void run() {
                                            AlertDialog.Builder
                                                    alertDialog = new AlertDialog.Builder(pContext);
                                            alertDialog
                                                    .setTitle(AUTHENTICATION)
                                                    .setMessage(response)
                                                    .create()
                                                    .show();
                                        }
                                    }
                            );
                            SharedPreferencesUtil.persistStringValue(
                                    (AppCompatActivity) ContextHolder.get(),
                                    IHttpClient.IHttpData.Header.TOKEN,
                                    response
                            );
                        } catch (IOException pEx) {
                            if (BuildConfig.DEBUG) {
                                Log.e(TAG, AUTHENTICATION_FAILED, pEx);
                            }
                        }
                    }
                }
        );
    }

    public static void sendRegistrationRequest(
            final Context pContext,
            final String pLogin,
            final String pPassword
    ) {
        mWorker.execute(
                new Runnable() {
                    @Override
                    public void run() {
                        try {
                            final String response = HttpClientFactory
                                    .getDefaultClient()
                                    .executeRequest(
                                            new RegistrationRequest(pLogin, pPassword)
                                    );
                            mWorker.post(
                                    new Runnable() {
                                        @Override
                                        public void run() {
                                            AlertDialog.Builder
                                                    alertDialog = new AlertDialog.Builder(pContext);
                                            alertDialog
                                                    .setTitle(REGISTRATION)
                                                    .setMessage(response)
                                                    .create()
                                                    .show();
                                        }
                                    }
                            );
                            SharedPreferencesUtil.persistStringValue(
                                    (AppCompatActivity) ContextHolder.get(),
                                    IHttpClient.IHttpData.Header.TOKEN,
                                    response
                            );
                        } catch (IOException pEx) {
                            if (BuildConfig.DEBUG) {
                                Log.e(TAG, REGISTRATION_FAILED, pEx);
                            }
                        }
                    }
                }
        );
    }
}

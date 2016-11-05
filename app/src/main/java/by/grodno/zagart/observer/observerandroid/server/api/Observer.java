package by.grodno.zagart.observer.observerandroid.server.api;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.util.Locale;

import by.grodno.zagart.observer.observerandroid.BuildConfig;
import by.grodno.zagart.observer.observerandroid.http.HttpClientFactory;
import by.grodno.zagart.observer.observerandroid.http.interfaces.IHttpClient;
import by.grodno.zagart.observer.observerandroid.interfaces.IAction;
import by.grodno.zagart.observer.observerandroid.interfaces.ICallback;
import by.grodno.zagart.observer.observerandroid.interfaces.IResultCallback;
import by.grodno.zagart.observer.observerandroid.server.requests.AuthenticationRequest;
import by.grodno.zagart.observer.observerandroid.server.requests.RegistrationRequest;
import by.grodno.zagart.observer.observerandroid.singletons.ContextHolder;
import by.grodno.zagart.observer.observerandroid.threadings.ThreadWorker;

/**
 * Class that provides methods to work with Observer's HTTP-server.
 *
 * @author zagart
 */
public class Observer {
    private static final String FAILED_TO_REGISTER = "Failed to register.";
    private final String RESPONSE = "Server response: %s";
    private final String FAILED_TO_SIGN_IN = "Failed to sign in.";
    private final String TAG = Observer.class.getSimpleName();
    private ThreadWorker mDefaultWorker = ThreadWorker.getDefaultInstance();
    private IHttpClient mClient = HttpClientFactory.get(HttpClientFactory.Type.HTTP_PURE);

    public static Observer getDefaultInstance() {
        return SingletonHolder.OBSERVER_INSTANCE;
    }

    public void onResponse(final String pResponse) {
        mDefaultWorker.post(
                new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(ContextHolder.get(), pResponse, Toast.LENGTH_LONG).show();
                    }
                }
        );
    }

    public void signIn(final String pLogin, final String pPassword) {
        mDefaultWorker.<String, String>execute(
                new SignInAction(pLogin, pPassword),
                new ObserverResultCallback()
        );
    }

    public void signUp(final String pLogin, final String pPassword) {
        mDefaultWorker.<String, String>execute(
                new SignUpAction(pLogin, pPassword),
                new ObserverResultCallback()
        );
    }

    public static class SingletonHolder {
        public static final Observer OBSERVER_INSTANCE = new Observer();
    }

    private class ObserverResultCallback implements IResultCallback<String, String> {
        @Override
        public String onResult(final String pActionResult) {
            if (BuildConfig.DEBUG) {
                Log.d(
                        TAG,
                        java.lang.String.format(
                                Locale.getDefault(),
                                RESPONSE,
                                pActionResult
                        )
                );
            }
            onResponse(pActionResult);
            return pActionResult;
        }
    }

    private class SignInAction implements IAction<Void, Void, String> {
        private String mLogin;
        private String mPassword;

        public SignInAction(final String pLogin, final String pPassword) {
            mLogin = pLogin;
            mPassword = pPassword;
        }

        @Override
        public String process(
                final ICallback<Void, String> pCallback,
                final Void... pParam
        ) throws InterruptedException {
            String response = null;
            try {
                response = mClient.executeRequest(
                        new AuthenticationRequest(mLogin, mPassword)
                );
            } catch (IOException pEx) {
                if (BuildConfig.DEBUG) {
                    Log.e(TAG, FAILED_TO_SIGN_IN, pEx);
                }
            }
            return response;
        }
    }

    private class SignUpAction implements IAction<Void, Void, String> {
        private String mLogin;
        private String mPassword;

        public SignUpAction(final String pLogin, final String pPassword) {
            mLogin = pLogin;
            mPassword = pPassword;
        }

        @Override
        public String process(
                final ICallback<Void, String> pCallback,
                final Void... pParam
        ) throws InterruptedException {
            String response = null;
            try {
                response = mClient.executeRequest(
                        new RegistrationRequest(mLogin, mPassword)
                );
            } catch (IOException pEx) {
                if (BuildConfig.DEBUG) {
                    Log.e(TAG, FAILED_TO_REGISTER, pEx);
                }
            }
            return response;
        }
    }
}

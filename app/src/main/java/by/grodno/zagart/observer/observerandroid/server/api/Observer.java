package by.grodno.zagart.observer.observerandroid.server.api;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

import by.grodno.zagart.observer.observerandroid.BuildConfig;
import by.grodno.zagart.observer.observerandroid.http.HttpClientFactory;
import by.grodno.zagart.observer.observerandroid.http.interfaces.IHttpClient;
import by.grodno.zagart.observer.observerandroid.server.requests.AuthenticationRequest;
import by.grodno.zagart.observer.observerandroid.server.requests.RegistrationRequest;
import by.grodno.zagart.observer.observerandroid.threadings.ThreadWorker;

/**
 * Class that provides methods to work with Observer's HTTP-server.
 *
 * @author zagart
 */
public class Observer {
    private static final String TOKEN = "token";
    private final String TAG = Observer.class.getSimpleName();
    private ThreadWorker mDefaultWorker = ThreadWorker.getDefaultInstance();
    private IHttpClient mClient = HttpClientFactory.get(HttpClientFactory.Type.HTTP_PURE);
    private Context mContext;

    public Observer(final Context pContext) {
        mContext = pContext;
    }

    public static String signIn(
            final Context pContext,
            final String pLogin,
            final String pPassword
    ) {
        return new Observer(pContext).signIn(pLogin, pPassword);
    }

    public static String signUp(
            final Context pContext,
            final String pLogin,
            final String pPassword
    ) {
        return new Observer(pContext).signUp(pLogin, pPassword);
    }

    @Nullable
    private String getTokenFromResponseString(final String pResponse) {
        try {
            final JSONObject jsonResponse = new JSONObject(pResponse);
            if (jsonResponse.has(TOKEN)) {
                return jsonResponse.getString(TOKEN);
            } else {
                return null;
            }
        } catch (JSONException pEx) {
            if (BuildConfig.DEBUG) {
                Log.e(TAG, pEx.getMessage(), pEx);
            }
            return null;
        }
    }

    @Nullable
    private String requestToServer(final IHttpClient.IRequest pRequest) {
        try {
            return (String) mDefaultWorker.submit(
                    new Callable<String>() {
                        @Override
                        public String call() {
                            try {
                                return mClient.executeRequest(pRequest);
                            } catch (IOException pEx) {
                                if (BuildConfig.DEBUG) {
                                    Log.e(TAG, pEx.getMessage(), pEx);
                                }
                                return null;
                            }
                        }
                    }
            );
        } catch (ExecutionException | InterruptedException pEx) {
            if (BuildConfig.DEBUG) {
                Log.e(TAG, pEx.getMessage(), pEx);
            }
            return null;
        }
    }

    public String signIn(final String pLogin, final String pPassword) {
        return getTokenFromResponseString(
                requestToServer(new AuthenticationRequest(pLogin, pPassword))
        );
    }

    public String signUp(final String pLogin, final String pPassword) {
        return getTokenFromResponseString(
                requestToServer(new RegistrationRequest(pLogin, pPassword))
        );
    }
}

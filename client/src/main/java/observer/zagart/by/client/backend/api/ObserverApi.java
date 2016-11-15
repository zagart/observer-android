package observer.zagart.by.client.backend.api;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

import observer.zagart.by.client.App;
import observer.zagart.by.client.BuildConfig;
import observer.zagart.by.client.backend.requests.AuthenticationRequest;
import observer.zagart.by.client.backend.requests.RegistrationRequest;
import observer.zagart.by.client.http.HttpClientFactory;
import observer.zagart.by.client.http.interfaces.IHttpClient;
import observer.zagart.by.client.threadings.ThreadWorker;

/**
 * Class that provides methods to work with ObserverApi's HTTP-server.
 *
 * @author zagart
 */
public class ObserverApi {

    private static final String TOKEN = "token";
    private final String TAG = ObserverApi.class.getSimpleName();
    private ThreadWorker mWorker;
    private IHttpClient mClient = HttpClientFactory.get(HttpClientFactory.Type.HTTP_PURE);
    private Context mContext;

    {
        mWorker = App.getState().getThreadWorker();
    }

    public ObserverApi(final Context pContext) {
        mContext = pContext;
    }

    @Nullable
    public static String logIn(
            final Context pContext,
            final String pLogin,
            final String pPassword
    ) {
        return new ObserverApi(pContext).logIn(pLogin, pPassword);
    }

    @Nullable
    public static String signUp(
            final Context pContext,
            final String pLogin,
            final String pPassword
    ) {
        return new ObserverApi(pContext).signUp(pLogin, pPassword);
    }

    @Nullable
    private String getTokenFromResponseString(final String pResponse) {
        if (pResponse == null) {
            return null;
        }
        try {
            JSONObject jsonResponse = new JSONObject(pResponse);
            if (jsonResponse.has(TOKEN)) {
                return jsonResponse.getString(TOKEN);
            }
            return null;
        } catch (JSONException pEx) {
            if (BuildConfig.DEBUG) {
                Log.e(TAG, pEx.getMessage(), pEx);
            }
            return null;
        }
    }

    @Nullable
    private String logIn(final String pLogin, final String pPassword) {
        return getTokenFromResponseString(
                requestToServer(new AuthenticationRequest(pLogin, pPassword))
        );
    }

    @Nullable
    private String requestToServer(final IHttpClient.IRequest<String> pRequest) {
        try {
            return (String) mWorker.submit(
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

    @Nullable
    private String signUp(final String pLogin, final String pPassword) {
        return getTokenFromResponseString(
                requestToServer(new RegistrationRequest(pLogin, pPassword))
        );
    }
}

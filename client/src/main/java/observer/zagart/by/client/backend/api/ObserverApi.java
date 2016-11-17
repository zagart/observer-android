package observer.zagart.by.client.backend.api;

import android.support.annotation.Nullable;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

import observer.zagart.by.client.App;
import observer.zagart.by.client.BuildConfig;
import observer.zagart.by.client.backend.Criteria;
import observer.zagart.by.client.backend.requests.AuthenticationRequest;
import observer.zagart.by.client.backend.requests.RegistrationRequest;
import observer.zagart.by.client.constants.Constants;
import observer.zagart.by.client.http.HttpClientFactory;
import observer.zagart.by.client.http.interfaces.IHttpClient;
import observer.zagart.by.client.threadings.ThreadWorker;

/**
 * Class that provides methods to work with Observer's HTTP-server.
 *
 * @author zagart
 */
public class ObserverApi {

    private final String TAG = ObserverApi.class.getSimpleName();
    private ThreadWorker mWorker;
    private IHttpClient mClient = HttpClientFactory.get(HttpClientFactory.Type.HTTP_PURE);

    {
        mWorker = App.getState().getThreadWorker();
    }

    @Nullable
    public static String logIn(
            final String pLogin,
            final String pPassword
    ) {
        return new ObserverApi().requestLogIn(pLogin, pPassword);
    }

    @Nullable
    public static String signUp(
            final String pLogin,
            final String pPassword
    ) {
        return new ObserverApi().requestSignUp(pLogin, pPassword);
    }

    public static String getReflectedAction(String pServerResponse) {
        //TODO get real server response reflection
        return Criteria.GET_STANDS;
    }

    @Nullable
    private String getTokenFromResponseString(final String pResponse) {
        if (pResponse == null) {
            return null;
        }
        try {
            JSONObject jsonResponse = new JSONObject(pResponse);
            if (jsonResponse.has(Constants.TOKEN)) {
                return jsonResponse.getString(Constants.TOKEN);
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
    private String requestLogIn(final String pLogin, final String pPassword) {
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
                                return null;
                            }
                        }
                    }
            );
        } catch (ExecutionException | InterruptedException pEx) {
            return null;
        }
    }

    @Nullable
    private String requestSignUp(final String pLogin, final String pPassword) {
        return getTokenFromResponseString(
                requestToServer(new RegistrationRequest(pLogin, pPassword))
        );
    }
}

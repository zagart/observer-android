package observer.zagart.by.client.server.api;
import android.accounts.Account;
import android.accounts.AccountAuthenticatorActivity;
import android.accounts.AccountManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

import observer.zagart.by.client.App;
import observer.zagart.by.client.BuildConfig;
import observer.zagart.by.client.R;
import observer.zagart.by.client.activities.MainActivity;
import observer.zagart.by.client.http.HttpClientFactory;
import observer.zagart.by.client.http.interfaces.IHttpClient;
import observer.zagart.by.client.server.requests.AuthenticationRequest;
import observer.zagart.by.client.server.requests.RegistrationRequest;
import observer.zagart.by.client.singletons.AccountHolder;
import observer.zagart.by.client.singletons.ContextHolder;
import observer.zagart.by.client.threadings.ThreadWorker;
import observer.zagart.by.client.utils.AndroidUtil;
import observer.zagart.by.client.utils.SharedPreferencesUtil;

import static android.app.Activity.RESULT_OK;

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

    public static void onTokenReceived(
            AccountAuthenticatorActivity pActivity,
            Account account,
            String password,
            String token
    ) {
        final AccountManager accountManager = AccountManager.get(pActivity);
        final Bundle result = new Bundle();
        if (accountManager.addAccountExplicitly(account, password, new Bundle())) {
            result.putString(AccountManager.KEY_ACCOUNT_NAME, account.name);
            result.putString(AccountManager.KEY_ACCOUNT_TYPE, account.type);
            result.putString(AccountManager.KEY_AUTHTOKEN, token);
            accountManager.setAuthToken(account, account.type, token);
        } else {
            result.putString(
                    AccountManager.KEY_ERROR_MESSAGE,
                    pActivity.getString(R.string.error_account_exists)
            );
        }
        SharedPreferencesUtil.persistStringValue(
                ContextHolder.get(),
                App.CURRENT_ACCOUNT_NAME,
                account.name
        );
        AccountHolder.set(account);
        pActivity.setAccountAuthenticatorResult(result);
        pActivity.setResult(RESULT_OK);
        AndroidUtil.startActivity(MainActivity.class);
    }

    @Nullable
    public static String logIn(
            final Context pContext,
            final String pLogin,
            final String pPassword
    ) {
        return new Observer(pContext).logIn(pLogin, pPassword);
    }

    @Nullable
    public static String signUp(
            final Context pContext,
            final String pLogin,
            final String pPassword
    ) {
        return new Observer(pContext).signUp(pLogin, pPassword);
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
    private String requestToServer(final IHttpClient.IRequest<String> pRequest) {
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

    @Nullable
    private String logIn(final String pLogin, final String pPassword) {
        return getTokenFromResponseString(
                requestToServer(new AuthenticationRequest(pLogin, pPassword))
        );
    }

    @Nullable
    private String signUp(final String pLogin, final String pPassword) {
        return getTokenFromResponseString(
                requestToServer(new RegistrationRequest(pLogin, pPassword))
        );
    }
}

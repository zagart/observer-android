package observer.zagart.by.client.application.accounts;

import android.accounts.AbstractAccountAuthenticator;
import android.accounts.Account;
import android.accounts.AccountAuthenticatorResponse;
import android.accounts.AccountManager;
import android.accounts.NetworkErrorException;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import org.json.JSONException;

import java.io.IOException;

import observer.zagart.by.client.App;
import observer.zagart.by.client.mvp.views.LoginActivity;
import observer.zagart.by.client.network.http.HttpFactory;
import observer.zagart.by.client.network.http.requests.observer.AuthenticationRequest;
import observer.zagart.by.client.network.http.responses.ObserverJsonResponse;

/**
 * Application authenticator.
 *
 * @author zagart
 */
class ObserverAuthenticator extends AbstractAccountAuthenticator {

    private Context mContext;

    ObserverAuthenticator(final Context pContext) {
        super(pContext);
        mContext = pContext;
    }

    @Override
    public Bundle editProperties(final AccountAuthenticatorResponse response,
                                 final String accountType) {
        return null;
    }

    @Override
    public Bundle addAccount(
            final AccountAuthenticatorResponse pResponse,
            final String pAccountType,
            final String pAuthTokenType,
            final String[] pRequiredFeatures,
            final Bundle pOptions) throws NetworkErrorException {
        final Intent intent = new Intent(mContext, LoginActivity.class);
        intent.putExtra(ObserverAccount.EXTRA_TOKEN_TYPE, pAccountType);
        intent.putExtra(AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE, pResponse);
        final Bundle bundle = new Bundle();
        if (pOptions != null) {
            bundle.putAll(pOptions);
        }
        bundle.putParcelable(AccountManager.KEY_INTENT, intent);
        return bundle;
    }

    @Override
    public Bundle confirmCredentials(
            final AccountAuthenticatorResponse response,
            final Account account,
            final Bundle options) throws NetworkErrorException {
        return null;
    }

    @Override
    public Bundle getAuthToken(
            final AccountAuthenticatorResponse pResponse,
            final Account pAccount,
            final String pAuthTokenType,
            final Bundle pOptions) throws NetworkErrorException {
        final Bundle result = new Bundle();
        final AccountManager accountManager = AccountManager.get(mContext.getApplicationContext());
        String authToken = accountManager.peekAuthToken(pAccount, pAuthTokenType);
        if (TextUtils.isEmpty(authToken)) {
            final String password = accountManager.getPassword(pAccount);
            if (!TextUtils.isEmpty(password)) {
                try {
                    final String response = HttpFactory.getDefaultClient().executeRequest(
                            new AuthenticationRequest(pAccount.name, password)
                    );
                    authToken = new ObserverJsonResponse(response).extractToken();
                } catch (IOException | JSONException pEx) {
                    authToken = null;
                }
            }
        }
        if (!TextUtils.isEmpty(authToken)) {
            result.putString(AccountManager.KEY_ACCOUNT_NAME, pAccount.name);
            result.putString(AccountManager.KEY_ACCOUNT_TYPE, pAccount.type);
            result.putString(AccountManager.KEY_AUTHTOKEN, authToken);
        } else {
            accountManager.invalidateAuthToken(
                    pAccount.type,
                    accountManager.peekAuthToken(pAccount, ObserverAccount.TOKEN)
            );
            final Intent intent = new Intent(mContext, LoginActivity.class);
            intent.putExtra(AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE, pResponse);
            intent.putExtra(ObserverAccount.EXTRA_TOKEN_TYPE, pAuthTokenType);
            final Bundle bundle = new Bundle();
            bundle.putParcelable(AccountManager.KEY_INTENT, intent);
        }
        return result;
    }

    @Override
    public String getAuthTokenLabel(final String authTokenType) {
        return null;
    }

    @Override
    public Bundle updateCredentials(
            final AccountAuthenticatorResponse response,
            final Account account,
            final String authTokenType,
            final Bundle options) throws NetworkErrorException {
        return null;
    }

    @Override
    public Bundle hasFeatures(
            final AccountAuthenticatorResponse response,
            final Account account,
            final String[] features) throws NetworkErrorException {
        return null;
    }

    @SuppressLint("ServiceCast")
    @SuppressWarnings("WrongConstant")
    @Override
    public Bundle getAccountRemovalAllowed(
            final AccountAuthenticatorResponse response,
            final Account account) throws NetworkErrorException {
        App.setAccount(null);
        return super.getAccountRemovalAllowed(response, account);
    }
}

package by.grodno.zagart.observer.observerandroid.accounts;
import android.accounts.AbstractAccountAuthenticator;
import android.accounts.Account;
import android.accounts.AccountAuthenticatorResponse;
import android.accounts.AccountManager;
import android.accounts.NetworkErrorException;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import by.grodno.zagart.observer.observerandroid.activities.LoginActivity;
import by.grodno.zagart.observer.observerandroid.activities.RegistrationActivity;
import by.grodno.zagart.observer.observerandroid.singletons.ContextHolder;

/**
 * Authenticator for Observer HTTP-server.
 *
 * @author zagart
 */
public class ObserverAuthenticator extends AbstractAccountAuthenticator {
    private Context mContext = ContextHolder.get();

    public ObserverAuthenticator(final Context pContext) {
        super(pContext);
        mContext = pContext;
    }

    @Override
    public Bundle editProperties(
            final AccountAuthenticatorResponse response,
            final String accountType
    ) {
        return null;
    }

    @Override
    public Bundle addAccount(
            final AccountAuthenticatorResponse pResponse,
            final String pAccountType,
            final String pAuthTokenType,
            final String[] pRequiredFeatures,
            final Bundle pOptions
    ) throws NetworkErrorException {
        final Intent intent = new Intent(mContext, RegistrationActivity.class);
        intent.putExtra(RegistrationActivity.EXTRA_TOKEN_TYPE, pAccountType);
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
            final Bundle options
    ) throws NetworkErrorException {
        return null;
    }

    @Override
    public Bundle getAuthToken(
            final AccountAuthenticatorResponse pResponse,
            final Account pAccount,
            final String pAuthTokenType,
            final Bundle pOptions
    ) throws NetworkErrorException {
        final Bundle result = new Bundle();
        final AccountManager am = AccountManager.get(mContext.getApplicationContext());
        String authToken = null;
        try {
            authToken = am.peekAuthToken(pAccount, pAuthTokenType);
        } catch (Exception pEx) {
        }
        if (TextUtils.isEmpty(authToken)) {
            final String password = am.getPassword(pAccount);
            if (!TextUtils.isEmpty(password)) {
                //TODO authentication request
            }
        }
        if (!TextUtils.isEmpty(authToken)) {
            result.putString(AccountManager.KEY_ACCOUNT_NAME, pAccount.name);
            result.putString(AccountManager.KEY_ACCOUNT_TYPE, pAccount.type);
            result.putString(AccountManager.KEY_AUTHTOKEN, authToken);
        } else {
            final Intent intent = new Intent(mContext, LoginActivity.class);
            intent.putExtra(AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE, pResponse);
            intent.putExtra(LoginActivity.EXTRA_TOKEN_TYPE, pAuthTokenType);
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
            final Bundle options
    ) throws NetworkErrorException {
        return null;
    }

    @Override
    public Bundle hasFeatures(
            final AccountAuthenticatorResponse response,
            final Account account,
            final String[] features
    ) throws NetworkErrorException {
        return null;
    }
}

package observer.zagart.by.client.account;

import android.accounts.AbstractAccountAuthenticator;
import android.accounts.Account;
import android.accounts.AccountAuthenticatorResponse;
import android.accounts.AccountManager;
import android.accounts.NetworkErrorException;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import observer.zagart.by.client.App;
import observer.zagart.by.client.activities.AuthenticationActivity;
import observer.zagart.by.client.backend.api.ObserverApi;
import observer.zagart.by.client.constants.Constants;
import observer.zagart.by.client.utils.SharedPreferencesUtil;

/**
 * Authenticator to ObserverApi HTTP-server.
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
    public Bundle editProperties(AccountAuthenticatorResponse response, String accountType) {
        return null;
    }

    @Override
    public Bundle addAccount(
            AccountAuthenticatorResponse pResponse,
            String pAccountType,
            String pAuthTokenType,
            String[] pRequiredFeatures,
            Bundle pOptions
    ) throws NetworkErrorException {
        final Intent intent = new Intent(mContext, AuthenticationActivity.class);
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
            AccountAuthenticatorResponse response,
            Account account,
            Bundle options
    ) throws NetworkErrorException {
        return null;
    }

    @Override
    public Bundle getAuthToken(
            AccountAuthenticatorResponse pResponse,
            Account pAccount,
            String pAuthTokenType,
            Bundle pOptions
    ) throws NetworkErrorException {
        final Bundle result = new Bundle();
        final AccountManager am = AccountManager.get(mContext.getApplicationContext());
        String authToken = am.peekAuthToken(pAccount, pAuthTokenType);
        if (TextUtils.isEmpty(authToken)) {
            final String password = am.getPassword(pAccount);
            if (!TextUtils.isEmpty(password)) {
                authToken = ObserverApi.logIn(mContext, pAccount.name, password);
            }
        }
        if (!TextUtils.isEmpty(authToken)) {
            result.putString(AccountManager.KEY_ACCOUNT_NAME, pAccount.name);
            result.putString(AccountManager.KEY_ACCOUNT_TYPE, pAccount.type);
            result.putString(AccountManager.KEY_AUTHTOKEN, authToken);
        } else {
            am.invalidateAuthToken(
                    pAccount.type,
                    am.peekAuthToken(pAccount, ObserverAccount.AUTH_TOKEN_TYPE
                    )
            );
            final Intent intent = new Intent(mContext, AuthenticationActivity.class);
            intent.putExtra(AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE, pResponse);
            intent.putExtra(ObserverAccount.EXTRA_TOKEN_TYPE, pAuthTokenType);
            final Bundle bundle = new Bundle();
            bundle.putParcelable(AccountManager.KEY_INTENT, intent);
        }
        return result;
    }

    @Override
    public String getAuthTokenLabel(String authTokenType) {
        return null;
    }

    @Override
    public Bundle updateCredentials(
            AccountAuthenticatorResponse response,
            Account account,
            String authTokenType, Bundle options
    ) throws NetworkErrorException {
        return null;
    }

    @Override
    public Bundle hasFeatures(
            AccountAuthenticatorResponse response,
            Account account,
            String[] features
    ) throws NetworkErrorException {
        return null;
    }

    @Override
    public Bundle getAccountRemovalAllowed(
            final AccountAuthenticatorResponse response,
            final Account account
    ) throws NetworkErrorException {
        SharedPreferencesUtil.persistStringValue(
                App.getState().getContext(),
                Constants.CURRENT_ACCOUNT_NAME,
                null
        );
        App.getState().setAccount(null);
        return super.getAccountRemovalAllowed(response, account);
    }
}

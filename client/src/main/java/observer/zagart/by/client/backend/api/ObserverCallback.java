package observer.zagart.by.client.backend.api;

import android.accounts.Account;
import android.accounts.AccountAuthenticatorActivity;
import android.accounts.AccountManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import observer.zagart.by.client.App;
import observer.zagart.by.client.R;
import observer.zagart.by.client.backend.Criteria;
import observer.zagart.by.client.constants.Constants;
import observer.zagart.by.client.mvp.views.MyAccountActivity;
import observer.zagart.by.client.utils.SharedPreferencesUtil;

import static android.app.Activity.RESULT_OK;

/**
 * @author zagart
 */
//TODO it's not callback
//TODO move working with json to another class
//ObserverCallback shouln't work with accountManager
public class ObserverCallback {

    @SuppressWarnings("unchecked")
    @Nullable
    public static <Entity> List<Entity> onResponseReceived(final String pServerResponse)
            throws JSONException {
        final JSONObject jsonObjectResponse = new JSONObject(pServerResponse);
        final String reflectedAction = jsonObjectResponse.getString(Constants.REFLECTION);
        if (pServerResponse == null || reflectedAction == null) {
            return null;
        }
        switch (reflectedAction) {
            case Criteria.GET_STANDS:
                return (List<Entity>) ObserverJsonParser.parseStandsResponse(jsonObjectResponse);
            case Criteria.GET_MODULES:
                return (List<Entity>) ObserverJsonParser.parseModulesResponse(jsonObjectResponse);
            default:
                return null;
        }
    }

    public static void onTokenReceived(
            final AccountAuthenticatorActivity pActivity,
            final Account pAccount,
            final String pPassword,
            final String pToken) {
        final AccountManager accountManager = AccountManager.get(pActivity);
        final Bundle result = new Bundle();
        if (accountManager.addAccountExplicitly(pAccount, pPassword, new Bundle())) {
            result.putString(AccountManager.KEY_ACCOUNT_NAME, pAccount.name);
            result.putString(AccountManager.KEY_ACCOUNT_TYPE, pAccount.type);
            result.putString(AccountManager.KEY_AUTHTOKEN, pToken);
            accountManager.setAuthToken(pAccount, pAccount.type, pToken);
        } else {
            result.putString(
                    AccountManager.KEY_ERROR_MESSAGE,
                    pActivity.getString(R.string.error_account_exists));
        }
        SharedPreferencesUtil.persistStringValue(
                App.getState().getContext(),
                Constants.CURRENT_ACCOUNT_NAME,
                pAccount.name);
        App.getState().setAccount(pAccount);
        pActivity.setAccountAuthenticatorResult(result);
        pActivity.setResult(RESULT_OK);
        final Intent intent = new Intent(pActivity, MyAccountActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_NO_HISTORY);
        pActivity.getApplicationContext().startActivity(intent);
    }
}

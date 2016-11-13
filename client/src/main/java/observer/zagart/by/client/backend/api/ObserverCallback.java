package observer.zagart.by.client.backend.api;
import android.accounts.Account;
import android.accounts.AccountAuthenticatorActivity;
import android.accounts.AccountManager;
import android.content.Intent;
import android.os.Bundle;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import observer.zagart.by.client.App;
import observer.zagart.by.client.R;
import observer.zagart.by.client.activities.MyAccountActivity;
import observer.zagart.by.client.repository.model.Stand;
import observer.zagart.by.client.repository.model.contracts.StandContract;
import observer.zagart.by.client.singletons.AccountHolder;
import observer.zagart.by.client.singletons.ContextHolder;
import observer.zagart.by.client.utils.SharedPreferencesUtil;

import static android.app.Activity.RESULT_OK;

/**
 * @author zagart
 */
public class ObserverCallback {
    public static List<Stand> onStandsReceived(String pServerResponse) throws JSONException {
        List<Stand> stands = new ArrayList<>();
        JSONObject root = new JSONObject(pServerResponse);
        final Iterator<String> keys = root.keys();
        while (keys.hasNext()) {
            final String key = keys.next();
            Stand stand = new Stand();
            stand.setId(Long.valueOf(Integer.valueOf(key)));
            final String value = root.getString(key);
            final JSONObject data = new JSONObject(value);
            stand.setNumber(data.getString(StandContract.NUMBER));
            stand.setDescription(data.getString(StandContract.DESCRIPTION));
            stands.add(stand);
        }
        return stands;
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
        final Intent intent = new Intent(pActivity, MyAccountActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_NO_HISTORY);
        pActivity.getApplicationContext().startActivity(intent);
    }
}

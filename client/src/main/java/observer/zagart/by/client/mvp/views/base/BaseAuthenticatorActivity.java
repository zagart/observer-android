package observer.zagart.by.client.mvp.views.base;

import android.accounts.AccountAuthenticatorActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import observer.zagart.by.client.App;
import observer.zagart.by.client.application.accounts.ObserverAccount;
import observer.zagart.by.client.application.constants.Constants;
import observer.zagart.by.client.application.utils.SharedPreferencesUtil;
import observer.zagart.by.client.mvp.IMvp;
import observer.zagart.by.client.mvp.views.MyAccountActivity;

/**
 * Base authenticator activity of application.
 *
 * @author zagart
 */
abstract public class BaseAuthenticatorActivity
        extends AccountAuthenticatorActivity implements IMvp.IViewOperations {

    @Override
    public Context getViewContext() {
        return this;
    }

    @Override
    public void onDataChanged(final Bundle pParameters) {
        ObserverAccount account = new ObserverAccount(pParameters.getString(ObserverAccount.NAME));
        account
                .setPassword(pParameters.getString(ObserverAccount.PASSWORD))
                .setToken(pParameters.getString(ObserverAccount.TOKEN));
        SharedPreferencesUtil.persistStringValue(
                App.getContext(),
                Constants.CURRENT_ACCOUNT_NAME,
                account.name);
        App.setAccount(account);
        final Intent intent = new Intent(this, MyAccountActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
    }
}

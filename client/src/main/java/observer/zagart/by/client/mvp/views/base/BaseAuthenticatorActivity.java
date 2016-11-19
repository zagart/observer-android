package observer.zagart.by.client.mvp.views.base;

import android.accounts.AccountAuthenticatorActivity;
import android.content.Context;
import android.util.Log;

import observer.zagart.by.client.application.constants.LogConstants;
import observer.zagart.by.client.mvp.IMvp;

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
    public void onDataChanged() {
        Log.i(
                BaseAuthenticatorActivity.class.getSimpleName(),
                LogConstants.VIEW_AUTHENTICATOR_ON_DATA_CHANGED);
    }
}

package observer.zagart.by.client.application.accounts;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import observer.zagart.by.client.App;

/**
 * Service for ObserverAuthenticator.
 *
 * @author zagart
 */
public class ObserverAuthenticatorService extends Service {

    private ObserverAuthenticator mAuthenticator;

    public ObserverAuthenticatorService() {
        super.onCreate();
        mAuthenticator = new ObserverAuthenticator(
                App.getState().getContext().getApplicationContext());
    }

    @Nullable
    @Override
    public IBinder onBind(final Intent pIntent) {
        return mAuthenticator.getIBinder();
    }
}

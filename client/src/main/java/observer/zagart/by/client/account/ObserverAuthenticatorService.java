package observer.zagart.by.client.account;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Service for ObserverAuthenticator.
 *
 * @author zagart
 */
public class ObserverAuthenticatorService extends Service {
    private ObserverAuthenticator mAuthenticator;

    public ObserverAuthenticatorService() {
        super.onCreate();
        mAuthenticator = new ObserverAuthenticator(getApplicationContext());
    }

    @Nullable
    @Override
    public IBinder onBind(final Intent pIntent) {
        return mAuthenticator.getIBinder();
    }
}

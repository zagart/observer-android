package by.grodno.zagart.observer.observerandroid.accounts;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Service for {@link ObserverAuthenticator}.
 *
 * @author zagart
 */
public class ObserverAuthenticatorService extends Service {
    private ObserverAuthenticator mAuthenticator;

    @Override
    public void onCreate() {
        super.onCreate();
        mAuthenticator = new ObserverAuthenticator(getApplicationContext());
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mAuthenticator.getIBinder();
    }
}

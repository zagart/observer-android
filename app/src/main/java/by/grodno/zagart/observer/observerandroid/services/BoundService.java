package by.grodno.zagart.observer.observerandroid.services;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

import java.util.Random;

/**
 * Bound service example.
 */

public class BoundService extends Service {

    private final Random random = new Random();

    public class LocalBinder extends Binder {
        public BoundService getService() {
            return BoundService.this;
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new LocalBinder();
    }

    public long getRandomLong() {
        return random.nextLong();
    }

}

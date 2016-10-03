package by.grodno.zagart.observer.observerandroid.services;

import android.app.IntentService;
import android.content.Intent;

import by.grodno.zagart.observer.observerandroid.DisplayMessageActivity;
import by.grodno.zagart.observer.observerandroid.MainActivity;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

/**
 * Started service example.
 */

public class StartedService extends IntentService {

    public StartedService() {
        super("StartedService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        Intent newIntent = new Intent(this, DisplayMessageActivity.class);
        newIntent.putExtra(MainActivity.EXTRA_MESSAGE, message);
        newIntent.addFlags(FLAG_ACTIVITY_NEW_TASK);
        startActivity(newIntent);
        stopSelf();
    }

}

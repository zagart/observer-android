package observer.zagart.by.client.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;

import observer.zagart.by.client.utils.IOUtil;

/**
 * Base activity for application. All its implementations registered
 * on common broadcast messages.
 *
 * @author zagart
 */
abstract public class BaseActivity extends AppCompatActivity {

    public static final String AUTHENTICATION_RESULT = "authentication_result";
    public static final String REGISTRATION_RESULT = "registration_result";
    public static final String SERVER_TIMEOUT_ERROR = "server_timeout_error";
    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            String authenticationResult = intent.getStringExtra(AUTHENTICATION_RESULT);
            String registrationResult = intent.getStringExtra(REGISTRATION_RESULT);
            String timeoutResult = intent.getStringExtra(SERVER_TIMEOUT_ERROR);
            IOUtil.showToast(BaseActivity.this, authenticationResult);
            IOUtil.showToast(BaseActivity.this, registrationResult);
            IOUtil.showToast(BaseActivity.this, timeoutResult);
        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        LocalBroadcastManager.getInstance(this)
                .registerReceiver(mMessageReceiver, new IntentFilter(AUTHENTICATION_RESULT));
        LocalBroadcastManager.getInstance(this)
                .registerReceiver(mMessageReceiver, new IntentFilter(REGISTRATION_RESULT));
        LocalBroadcastManager.getInstance(this)
                .registerReceiver(mMessageReceiver, new IntentFilter(SERVER_TIMEOUT_ERROR));
        onViewsVisibilityCheck();
    }

    @Override
    protected void onStop() {
        super.onStop();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);
    }

    protected void onViewsVisibilityCheck() {
        //activity specific buttons visibility check
    }
}

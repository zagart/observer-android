package observer.zagart.by.client.activities;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

/**
 * @author zagart
 */
abstract public class BaseActivity extends AppCompatActivity {
    public static final String AUTHENTICATION_RESULT = "authentication_result";
    public static final String REGISTRATION_RESULT = "registration_result";
    public static final String TIMEOUT_ERROR = "timeout_error";
    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String authenticationResult = intent.getStringExtra(AUTHENTICATION_RESULT);
            String registrationResult = intent.getStringExtra(REGISTRATION_RESULT);
            String timeout = intent.getStringExtra(TIMEOUT_ERROR);
            if (authenticationResult != null) {
                Toast.makeText(BaseActivity.this, authenticationResult, Toast.LENGTH_LONG).show();
            }
            if (registrationResult != null) {
                Toast.makeText(BaseActivity.this, registrationResult, Toast.LENGTH_LONG).show();
            }
            if (timeout != null) {
                Toast.makeText(BaseActivity.this, timeout, Toast.LENGTH_LONG).show();
            }
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
                .registerReceiver(mMessageReceiver, new IntentFilter(TIMEOUT_ERROR));
    }

    @Override
    protected void onStop() {
        super.onStop();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);
    }
}

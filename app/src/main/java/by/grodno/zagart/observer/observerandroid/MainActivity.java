package by.grodno.zagart.observer.observerandroid;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import by.grodno.zagart.observer.observerandroid.services.BoundService;
import by.grodno.zagart.observer.observerandroid.services.StartedService;

/**
 * Main activity of the application.
 */
public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "Extra message!";
    public static final String CREATE = " create ";
    public static final String ON_SAVE_STATE = " onSaveState ";
    public static final String ON_RESTORE_STATE = " onRestoreState ";
    public static final String PAUSE = " pause ";
    public static final String RESUME = " resume ";
    public static final String STOP = " stop ";
    public static final String START = " start ";
    public static final String RESTART = " restart ";
    public static final String NEVER_BE_SEEN = " never be seen ";
    private EditText editText;
    private String message;
    private String status;
    private BoundService service = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        status = CREATE;
        setContentView(R.layout.activity_main);
        editText = (EditText) findViewById(R.id.edit_message);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        status += ON_SAVE_STATE;
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        status += ON_RESTORE_STATE;
        super.onRestoreInstanceState(savedInstanceState);
    }

    public void sendMessage(View view) {
        message = editText.getText().toString();
        Intent serviceIntent = new Intent(this, StartedService.class);
        serviceIntent.putExtra(EXTRA_MESSAGE, message);
        startService(serviceIntent);
    }

    public void showToast(View view) {
        String output = status;
        if (service != null) {
            output += "(Service bound)";
        }
        Toast.makeText(this, output, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onPause() {
        status += PAUSE;
        //do something when activity is obscured
        super.onPause();
    }

    @Override
    protected void onResume() {
        status += RESUME;
        //do something when activity had focus
        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
        status += STOP;
        unbindService(connection);
    }

    @Override
    protected void onStart() {
        super.onStart();
        status += START;
        Intent intent = new Intent(this, BoundService.class);
        bindService(intent, connection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onRestart() {
        status += RESTART;
        //actually do not required, because by default it calls method onStart()
        super.onRestart();
    }

    @Override
    protected void onDestroy() {
        status += NEVER_BE_SEEN;
        //usually all job already done in method OnStop() but it's still last chance to release resources
        super.onDestroy();
    }

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            BoundService.LocalBinder binder = ((BoundService.LocalBinder) service);
            MainActivity.this.service = binder.getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

}

package by.grodno.zagart.observer.observerandroid;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import by.grodno.zagart.observer.observerandroid.activities.A1;
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
    public static final String ON_DESTROY = " never be seen ";
    public static final String EMPTY = "";
    public static final String STATUS = "status";
    private String message = EMPTY;
    private String status = EMPTY;
    private BoundService service = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.hide();
        status += CREATE;
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        status += ON_SAVE_STATE;
        outState.putString(STATUS, status);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        status = savedInstanceState.getString(STATUS);
        status += ON_RESTORE_STATE;
    }

    public void nextActivity(View view) {
        Intent intent = new Intent(this, A1.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    public void showToast(View view) {
        String output = status;
        if (service != null) {
            output += "(Service bound)";
        }
        Toast.makeText(this, output, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        status += PAUSE;
        //do something when activity is obscured
    }

    @Override
    protected void onResume() {
        super.onResume();
        status += RESUME;
        //do something when activity had focus
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
        super.onRestart();
        status += RESTART;
        //actually do not required, because by default it calls method onStart()
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        status += ON_DESTROY;
        //usually all job already done in method OnStop() but it's still last chance to release resources
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

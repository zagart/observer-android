package by.grodno.zagart.observer.observerandroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import by.grodno.zagart.observer.observerandroid.services.TestService;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

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
    private TextView textViewStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        status = CREATE;
        setContentView(R.layout.activity_main);
        editText = (EditText) findViewById(R.id.edit_message);
        textViewStatus = ((TextView) findViewById(R.id.activity_status));
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
        Intent serviceIntent = new Intent(this, TestService.class);
        serviceIntent.putExtra(EXTRA_MESSAGE, message + status);
        startService(serviceIntent);
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
        textViewStatus.setText(status);
        //do something when activity had focus
        super.onResume();
    }

    @Override
    protected void onStop() {
        status += STOP;
        //do something like releasing resources and saving current progress
        super.onStop();
    }

    @Override
    protected void onStart() {
        status += START;
        //if activity require some features be turned on - this is place for them
        super.onStart();
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



}

package by.grodno.zagart.observer.observerandroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Main activity of the application.
 */
public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "Extra message!";
    private EditText editText;
    private String message;
    private String status;
    private TextView textViewStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        status = " create ";
        setContentView(R.layout.activity_main);
        editText = (EditText) findViewById(R.id.edit_message);
        textViewStatus = ((TextView) findViewById(R.id.activity_status));
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        status += " onSaveState ";
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        status += " onRestoreState ";
        super.onRestoreInstanceState(savedInstanceState);
    }

    public void sendMessage(View view) {
        Intent intent = new Intent(this, DisplayMessageActivity.class);
        message = editText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message + status);
        startActivity(intent);
    }

    @Override
    protected void onPause() {
        super.onPause();
        status += " pause ";
        //do something when activity is obscured
    }

    @Override
    protected void onResume() {
        super.onResume();
        status += " resume ";
        textViewStatus.setText(status);
        //do something when activity had focus
    }

    @Override
    protected void onStop() {
        super.onStop();
        status += " stop ";
        //do something like releasing resources and saving current progress
    }

    @Override
    protected void onStart() {
        super.onStart();
        status += " start ";
        //if activity require some features be turned on - this is place for them
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        status += " restart ";
        //actually do not required, because by default it calls method onStart()
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        status += " never be seen ";
        //usually all job already done in method OnStop() but it's still last chance to release resources
    }



}

package by.grodno.zagart.observer.observerandroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

/**
 * Main activity of the application.
 */
public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "Extra message!";
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = (EditText) findViewById(R.id.edit_message);
    }

    @Override
    protected void onPause() {
        super.onPause();
        //do something when activity is obscured
    }

    @Override
    protected void onResume() {
        super.onResume();
        //do something when activity had focus
    }

    @Override
    protected void onStop() {
        super.onStop();
        //do something like releasing resources and saving current progress
    }

    @Override
    protected void onStart() {
        super.onStart();
        //if activity require some features be turned on - this is place for them
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        //actually do not required, because by default it calls method onStart()
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //usually all job already done in method OnStop() but it's still last chance to release resources
    }

    public void sendMessage(View view) {
        Intent intent = new Intent(this, DisplayMessageActivity.class);
        String message = editText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }

}

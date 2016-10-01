package by.grodno.zagart.observer.observerandroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

/**
 * Commit text #1 for rebase.
 * Commit text #2 for rebase.
 * Commit text #3 for rebase.
 * Commit text #4 for rebase.
 */
public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "Extra message!";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void SendMessage(View view) {
        Intent intent = new Intent(this, DisplayMessageActivity.class);
        EditText editText = (EditText) findViewById(R.id.edit_message);
        String message = editText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }

}

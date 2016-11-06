package observer.zagart.by.client.activities;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import observer.zagart.by.client.R;
import observer.zagart.by.client.server.api.Observer;
import observer.zagart.by.client.threadings.ThreadWorker;

/**
 * Activity with UI for executing process of registration
 * for users.
 *
 * @author zagart
 */
public class RegistrationActivity extends AppCompatActivity {
    private EditText mPasswordView;
    private EditText mLoginView;
    private ThreadWorker mWorker;

    public void onConfirmClick(View pView) {
        String login = mLoginView.getText().toString();
        String password = mPasswordView.getText().toString();
        //TODO fields check
        Toast.makeText(this, Observer.signUp(this, login, password), Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mWorker = ThreadWorker.getDefaultInstance();
        setContentView(R.layout.registration_activity);
        mWorker.execute(
                new Runnable() {
                    @Override
                    public void run() {
                        mLoginView = (EditText) findViewById(R.id.registration_login);
                        mPasswordView = (EditText) findViewById(R.id.registration_password);
                    }
                }
        );
    }
}

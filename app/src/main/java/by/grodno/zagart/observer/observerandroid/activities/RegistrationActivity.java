package by.grodno.zagart.observer.observerandroid.activities;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import by.grodno.zagart.observer.observerandroid.R;
import by.grodno.zagart.observer.observerandroid.singletons.ContextHolder;
import by.grodno.zagart.observer.observerandroid.threadings.ThreadWorker;

import static by.grodno.zagart.observer.observerandroid.utils.HttpUtil.sendRegistrationRequest;

/**
 * Activity with UI for executing process of registration
 * for users.
 *
 * @author zagart
 */
public class RegistrationActivity extends AppCompatActivity {
    public static final String EXTRA_TOKEN_TYPE = "";
    private EditText mPasswordView;
    private EditText mLoginView;
    private ThreadWorker mWorker;

    public void onConfirmClick(View pView) {
        final String login = mLoginView.getText().toString();
        final String password = mPasswordView.getText().toString();
        sendRegistrationRequest(ContextHolder.get(), login, password);
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

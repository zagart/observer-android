package by.grodno.zagart.observer.observerandroid.activities;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.util.Locale;

import by.grodno.zagart.observer.observerandroid.BuildConfig;
import by.grodno.zagart.observer.observerandroid.R;
import by.grodno.zagart.observer.observerandroid.http.HttpClientFactory;
import by.grodno.zagart.observer.observerandroid.http.observer.requests.RegistrationRequest;
import by.grodno.zagart.observer.observerandroid.threadings.ThreadWorker;

/**
 * Activity with UI for executing process of registration
 * fro users.
 *
 * @author zagart
 */
public class RegistrationActivity extends AppCompatActivity {
    public static final String TAG = RegistrationActivity.class.getSimpleName();
    public static final String TOAST = "Login: %s\nPassword: %s";
    private EditText mPasswordView;
    private EditText mLoginView;
    private ThreadWorker mWorker;

    public void onConfirmClick(View pView) {
        final String login = mLoginView.getText().toString();
        final String password = mPasswordView.getText().toString();
        Toast.makeText(
                this,
                String.format(
                        Locale.getDefault(),
                        TOAST,
                        login,
                        password),
                Toast.LENGTH_SHORT
        ).show();
        mWorker.execute(
                new Runnable() {
                    @Override
                    public void run() {
                        try {
                            HttpClientFactory.getDefaultClient().executeRequest(
                                    new RegistrationRequest(login, password)
                            );
                        } catch (IOException pEx) {
                            if (BuildConfig.DEBUG) {
                                Log.e(TAG, pEx.getMessage());
                            }
                            pEx.printStackTrace();
                        }
                    }
                }
        );
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

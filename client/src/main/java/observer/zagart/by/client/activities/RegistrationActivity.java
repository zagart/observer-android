package observer.zagart.by.client.activities;
import android.accounts.AccountAuthenticatorActivity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import observer.zagart.by.client.R;
import observer.zagart.by.client.account.ObserverAccount;
import observer.zagart.by.client.server.api.Observer;
import observer.zagart.by.client.singletons.ContextHolder;
import observer.zagart.by.client.threadings.ThreadWorker;
import observer.zagart.by.client.utils.AndroidUtil;

/**
 * Activity with UI for executing process of registration
 * for users.
 *
 * @author zagart
 */
public class RegistrationActivity extends AccountAuthenticatorActivity {
    private EditText mPasswordView;
    private EditText mLoginView;
    private ThreadWorker mWorker;

    public void onConfirmClick(View pView) {
        final CharSequence charLogin = mLoginView.getText();
        final CharSequence charPassword = mPasswordView.getText();
        if (TextUtils.isEmpty(charLogin) || TextUtils.isEmpty(charPassword)) {
            AndroidUtil.showMessage(R.string.error_login_fields_empty);
        } else {
            executeRegistration(charLogin, charPassword);
        }
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

    @Override
    protected void onStart() {
        super.onStart();
        ContextHolder.set(this);
    }

    private void executeRegistration(final CharSequence pCharLogin, final CharSequence pCharPassword) {
        mWorker.execute(
                new Runnable() {
                    @Override
                    public void run() {
                        final String login = pCharLogin.toString();
                        final String password = pCharPassword.toString();
                        final String token = Observer.signUp(
                                RegistrationActivity.this,
                                login,
                                password
                        );
                        if (!TextUtils.isEmpty(token)) {
                            ObserverAccount account = new ObserverAccount(login);
                            Observer.onTokenReceived(
                                    RegistrationActivity.this,
                                    account,
                                    password,
                                    token
                            );
                        } else {
                            AndroidUtil.postMessage(R.string.err_registration_failed);
                        }
                    }
                }
        );
    }
}

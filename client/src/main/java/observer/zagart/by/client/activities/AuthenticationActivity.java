package observer.zagart.by.client.activities;
import android.accounts.AccountAuthenticatorActivity;
import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import observer.zagart.by.client.R;
import observer.zagart.by.client.account.ObserverAccount;
import observer.zagart.by.client.backend.api.ObserverApi;
import observer.zagart.by.client.backend.api.ObserverCallback;
import observer.zagart.by.client.threadings.ThreadWorker;
import observer.zagart.by.client.utils.BroadcastUtil;

/**
 * Activity that provides UI for user authorization.
 */
public class AuthenticationActivity extends AccountAuthenticatorActivity {
    public static String EXTRA_TOKEN_TYPE = "by.zagart.observer.EXTRA_TOKEN";
    private TextView mLoginView;
    private TextView mPasswordView;
    private ThreadWorker mWorker = ThreadWorker.getDefaultInstance();

    private void executeAuthentication(
            final CharSequence pCharLogin,
            final CharSequence pCharPassword
    ) {
        mWorker.execute(
                new Runnable() {
                    @Override
                    public void run() {
                        final String login = pCharLogin.toString();
                        final String password = pCharPassword.toString();
                        final String token = ObserverApi.logIn(
                                AuthenticationActivity.this,
                                login,
                                password
                        );
                        if (!TextUtils.isEmpty(token)) {
                            ObserverAccount account = new ObserverAccount(login);
                            ObserverCallback.onTokenReceived(
                                    AuthenticationActivity.this,
                                    account,
                                    password,
                                    token
                            );
                        } else {
                            BroadcastUtil.sendBroadcast(
                                    AuthenticationActivity.this,
                                    BaseActivity.AUTHENTICATION_RESULT,
                                    getString(R.string.msg_failed_authentication)
                            );
                        }
                    }
                }
        );
    }

    @SuppressWarnings("MissingPermission")
    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ActionBar bar = getActionBar();
        if (bar != null) {
            bar.hide();
        }
        setContentView(R.layout.login_activity);
        mLoginView = (TextView) findViewById(R.id.login_login);
        mPasswordView = (TextView) findViewById(R.id.login_password);
    }

    public void onGuestClick(View view) {
        Toast.makeText(this, getString(R.string.msg_dummy), Toast.LENGTH_LONG).show();
    }

    public void onLogInClick(View view) {
        final CharSequence charLogin = mLoginView.getText();
        final CharSequence charPassword = mPasswordView.getText();
        if (TextUtils.isEmpty(charLogin) || TextUtils.isEmpty(charPassword)) {
            Toast.makeText(
                    this,
                    getString(R.string.error_login_fields_empty),
                    Toast.LENGTH_LONG
            ).show();
        } else {
            executeAuthentication(charLogin, charPassword);
        }
    }

    public void onSignUpClick(View pView) {
        Intent intent = new Intent(this, RegistrationActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
    }
}

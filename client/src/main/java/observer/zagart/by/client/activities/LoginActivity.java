package observer.zagart.by.client.activities;
import android.accounts.AccountAuthenticatorActivity;
import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import observer.zagart.by.client.R;
import observer.zagart.by.client.account.ObserverAccount;
import observer.zagart.by.client.server.api.Observer;
import observer.zagart.by.client.singletons.ContextHolder;
import observer.zagart.by.client.utils.MessengerUtil;

/**
 * Activity that provides UI for user authorization.
 */
public class LoginActivity extends AccountAuthenticatorActivity {
    public static String EXTRA_TOKEN_TYPE = "by.zagart.observer.EXTRA_TOKEN";
    private TextView mLoginView;
    private TextView mPasswordView;

    @SuppressWarnings("MissingPermission")
    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ActionBar bar = getActionBar();
        if (bar != null) {
            bar.hide();
        }
        setContentView(R.layout.login_activity);
        ContextHolder.set(this);
        mLoginView = (TextView) findViewById(R.id.login_login);
        mPasswordView = (TextView) findViewById(R.id.login_password);
    }

    public void onGuestClick(View view) {
        Intent intent = new Intent(this, GuestActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }

    public void onSignInClick(View view) {
        final CharSequence charLogin = mLoginView.getText();
        final CharSequence charPassword = mPasswordView.getText();
        if (TextUtils.isEmpty(charLogin) || TextUtils.isEmpty(charPassword)) {
            MessengerUtil.showMessage(R.string.error_login_fields_empty);
        } else {
            final String login = charLogin.toString();
            final String password = charPassword.toString();
            final String token = Observer.signIn(this, login, password);
            if (!TextUtils.isEmpty(token)) {
                ObserverAccount account = new ObserverAccount(login);
                Observer.onTokenReceived(this, account, password, token);
            } else {
                MessengerUtil.showMessage(R.string.msg_failed_authentication);
            }
        }
    }

    public void onSignUpClick(View pView) {
        Intent intent = new Intent(this, RegistrationActivity.class);
        startActivity(intent);
    }
}

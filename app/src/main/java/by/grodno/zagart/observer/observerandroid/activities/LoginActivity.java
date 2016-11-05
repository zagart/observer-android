package by.grodno.zagart.observer.observerandroid.activities;
import android.accounts.AccountAuthenticatorActivity;
import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import by.grodno.zagart.observer.observerandroid.R;
import by.grodno.zagart.observer.observerandroid.accounts.ObserverAuthenticator;
import by.grodno.zagart.observer.observerandroid.server.api.Observer;
import by.grodno.zagart.observer.observerandroid.singletons.ContextHolder;

/**
 * Activity that provides UI for user authorization.
 */
public class LoginActivity extends AccountAuthenticatorActivity {
    public static final String ALREADY_EXISTS = "Already exists";
    public static final String EXTRA_TOKEN_TYPE = "by.zagart.observer.EXTRA_TOKEN_TYPE";
    private ObserverAuthenticator mAuthenticator = new ObserverAuthenticator(this);
    private TextView mLoginView;
    private TextView mPasswordView;
    private Observer mServer = Observer.getDefaultInstance();

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
        Intent intent = new Intent(this, GuestActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }

    public void onSignInClick(View view) {
        String login = "";
        String password = "";
        final CharSequence loginViewText = mLoginView.getText();
        final CharSequence passwordViewText = mPasswordView.getText();
        if (loginViewText != null) {
            login = loginViewText.toString();
        }
        if (passwordViewText != null) {
            password = passwordViewText.toString();
        }
        ContextHolder.set(this);
        mServer.signIn(login, password);
    }

    public void onSignUpClick(View pView) {
        Intent intent = new Intent(this, RegistrationActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }
}

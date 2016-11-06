package observer.zagart.by.client.activities;
import android.accounts.AccountAuthenticatorActivity;
import android.accounts.AccountManager;
import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import observer.zagart.by.client.R;
import observer.zagart.by.client.account.ObserverAccount;

/**
 * Activity that provides UI for user authorization.
 */
public class LoginActivity extends AccountAuthenticatorActivity {
    private static final String ACCOUNT_ALREADY_EXISTS = "Account already exists";
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
        mLoginView = (TextView) findViewById(R.id.login_login);
        mPasswordView = (TextView) findViewById(R.id.login_password);
    }

    public void onGuestClick(View view) {
        Intent intent = new Intent(this, GuestActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }

    public void onSignInClick(View view) {
        final AccountManager accountManager = AccountManager.get(this);
        ObserverAccount account = new ObserverAccount("account");
        accountManager.addAccountExplicitly(account, "password", null);
    }

    public void onSignUpClick(View pView) {
        Intent intent = new Intent(this, RegistrationActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }
}

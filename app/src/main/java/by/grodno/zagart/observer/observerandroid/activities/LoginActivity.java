package by.grodno.zagart.observer.observerandroid.activities;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import by.grodno.zagart.observer.observerandroid.R;
import by.grodno.zagart.observer.observerandroid.threadings.ThreadWorker;

/**
 * Activity that provides UI for user authorization.
 */
public class LoginActivity extends AppCompatActivity {
    private static final String TAG = LoginActivity.class.getSimpleName();
    public static final String ALREADY_EXISTS = "Already exists";
    public static final String EXTRA_TOKEN_TYPE = "";
    private ThreadWorker mWorker;

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ActionBar bar = getSupportActionBar();
        if (bar != null) {
            bar.hide();
        }
        setContentView(R.layout.login_activity);
        mWorker = ThreadWorker.getDefaultInstance();
    }

    public void onGuestClick(View view) {
        Intent intent = new Intent(this, GuestActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }

    public void onSignInClick(View view) {
        final TextView loginView = (TextView) findViewById(R.id.login_login);
        final TextView passwordView = (TextView) findViewById(R.id.login_password);
    }

    public void onSignUpClick(View pView) {
        Intent intent = new Intent(this, RegistrationActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }

    public void onTokenReceived(Account pAccount, String pPassword, String pToken) {
        final AccountManager am = AccountManager.get(this);
        final Bundle result = new Bundle();
        if (am.addAccountExplicitly(pAccount, pPassword, new Bundle())) {
            result.putString(AccountManager.KEY_ACCOUNT_NAME, pAccount.name);
            result.putString(AccountManager.KEY_ACCOUNT_TYPE, pAccount.type);
            result.putString(AccountManager.KEY_AUTHTOKEN, pToken);
            am.setAuthToken(pAccount, pAccount.type, pToken);
        } else {
            result.putString(AccountManager.KEY_ERROR_MESSAGE, ALREADY_EXISTS);
        }
        // TODO setAccountAuthenticatorResult(result);
        setResult(RESULT_OK);
        finish();
    }
}

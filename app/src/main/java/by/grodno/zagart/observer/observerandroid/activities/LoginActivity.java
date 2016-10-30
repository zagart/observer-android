package by.grodno.zagart.observer.observerandroid.activities;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import by.grodno.zagart.observer.observerandroid.R;
import by.grodno.zagart.observer.observerandroid.singletons.ContextHolder;
import by.grodno.zagart.observer.observerandroid.utils.HttpUtil;
import by.grodno.zagart.observer.observerandroid.utils.ObserverUtil;

/**
 * Activity that provides UI for user authorization.
 */
public class LoginActivity extends AppCompatActivity {
    public static final String URL = "http://10.0.2.2:8080/Observer/AndroidRequestHandler";

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ActionBar bar = getSupportActionBar();
        if (bar != null) {
            bar.hide();
        }
        setContentView(R.layout.login_activity);
    }

    public void onGuestClick(View view) {
        Intent intent = new Intent(this, GuestActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }

    public void onSignInClick(View view) {
        ContextHolder.set(this);
        final TextView loginView = (TextView) findViewById(R.id.login_login);
        final TextView passwordView = (TextView) findViewById(R.id.login_password);
        final String login = loginView.getText().toString();
        final String password = passwordView.getText().toString();
        final String token = ObserverUtil.generateToken(login, password);
        HttpUtil.requestObserverAuthorization(URL, token);
    }

    public void onSignUpClick(View pView) {
        Intent intent = new Intent(this, RegistrationActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }
}

package by.grodno.zagart.observer.observerandroid.activities;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import java.util.Properties;

import by.grodno.zagart.observer.observerandroid.R;
import by.grodno.zagart.observer.observerandroid.http.HttpRequestTask;
import by.grodno.zagart.observer.observerandroid.http.IHttpClient;
import by.grodno.zagart.observer.observerandroid.singletons.ContextHolder;

/**
 * Activity that provides UI for user authorization.
 */
public class LoginActivity extends AppCompatActivity {
    public static final String URL = "http://10.0.2.2:8080/Observer/AndroidRequestHandler";

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        Properties requestProperties = new Properties();
        requestProperties.put(
                IHttpClient.IRequest.Header.ACTION.name(),
                IHttpClient.IRequest.Action.AUTHORIZE.name()
        );
        final String login = loginView.getText().toString();
        requestProperties.put(
                IHttpClient.IRequest.Header.LOGIN.name(),
                login
        );
        final String password = passwordView.getText().toString();
        requestProperties.put(
                IHttpClient.IRequest.Header.PASSWORD.name(),
                password
        );
        final HttpRequestTask task = new HttpRequestTask(
                URL,
                IHttpClient.IRequest.Method.POST,
                requestProperties
        );
        task.execute();
    }
}

package by.grodno.zagart.observer.observerandroid.activities;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import by.grodno.zagart.observer.observerandroid.R;

/**
 * Activity that provides UI for user authorization.
 */
public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
    }
}

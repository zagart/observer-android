package by.grodno.zagart.observer.observerandroid.activities;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

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

    public void onGuestClick(View view) {
        Intent intent = new Intent(this, GuestActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }
}

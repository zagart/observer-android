package by.grodno.zagart.observer.observerandroid;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import by.grodno.zagart.observer.observerandroid.utils.SharedPreferencesUtil;

/**
 * Main activity.
 */
public class MainActivity extends AppCompatActivity {

    public static final String TRUSTED_USER = "Trusted user";

    public void onClickExit(View view) {
        finish();
    }

    public void onClickReject(View view) {
        SharedPreferencesUtil.clearPersistedValues(this);
        finish();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
    }
}

package by.grodno.zagart.observer.observerandroid;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import static by.grodno.zagart.observer.observerandroid.utils.SharedPreferencesUtil.clearPersistedValues;
import static by.grodno.zagart.observer.observerandroid.utils.SharedPreferencesUtil.persistValue;


/**
 * Main activity.
 */
public class MainActivity extends AppCompatActivity {

    public static final String TRUSTED_USER = "Trusted user";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        persistValue(this, TRUSTED_USER, true);
        setContentView(R.layout.main_activity);
    }

    public void onClickReject(View view) {
        clearPersistedValues(this);
        finish();
    }

    public void onClickExit(View view) {
        finish();
    }

}

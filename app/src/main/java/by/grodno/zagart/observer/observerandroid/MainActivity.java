package by.grodno.zagart.observer.observerandroid;
import android.content.Intent;
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

    public void onClickExit(View view) {
        finish();
    }

    public void onClickReject(View view) {
        clearPersistedValues(this);
        persistValue(this, TRUSTED_USER, false);
        finish();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        persistValue(this, TRUSTED_USER, true);
        setContentView(R.layout.main_activity);
    }

    public void onHappyClick(View view) {
        Intent intent = new Intent(this, SadActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }
}

package by.grodno.zagart.observer.observerandroid.activities;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK;
import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;
import static by.grodno.zagart.observer.observerandroid.utils.SharedPreferencesUtil.retrieveValue;

/**
 * Activity without UI that runs at startup.
 */
public class ZeroActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Class target = A1.class;
        if ((boolean) retrieveValue(this, MainActivity.TRUSTED_USER, Boolean.class)) {
            target = MainActivity.class;
        }
        final Intent intent = new Intent(this, target);
        intent.addFlags(FLAG_ACTIVITY_CLEAR_TASK | FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}

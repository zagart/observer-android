package by.grodno.zagart.observer.observerandroid;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import by.grodno.zagart.observer.observerandroid.activities.A1;
import by.grodno.zagart.observer.observerandroid.utils.SharedPreferencesUtil;

/**
 * Activity without UI that runs at startup.
 */
public class ZeroActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Class target = A1.class;
        if (SharedPreferencesUtil.retrieveBooleanValue(this, MainActivity.TRUSTED_USER)) {
            target = MainActivity.class;
        }
        final Intent intent = new Intent(this, target);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}

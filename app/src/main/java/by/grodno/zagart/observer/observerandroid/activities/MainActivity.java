package by.grodno.zagart.observer.observerandroid.activities;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import by.grodno.zagart.observer.observerandroid.BuildConfig;
import by.grodno.zagart.observer.observerandroid.R;
import by.grodno.zagart.observer.observerandroid.threadings.BackgroundTask;
import by.grodno.zagart.observer.observerandroid.threadings.impl.IThreadActionImpl;
import by.grodno.zagart.observer.observerandroid.threadings.impl.IActionCallbackImpl;

/**
 * Application main activity.
 */
public class MainActivity extends AppCompatActivity {
    public static final String SLAVE_NAME = "SlaveThread";
    public static final String CONFIGURATION_CHANGED = "Configuration changed.";
    public static final String MAIN_TAG = "MainActivity";
    private BackgroundTask mBackgroundTask = new BackgroundTask(SLAVE_NAME);

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ActionBar bar = getSupportActionBar();
        if (bar != null) {
            bar.hide();
        }
        setContentView(R.layout.main_activity);
        //call BackgroundTask method onStart()..?
        mBackgroundTask.onStart();
    }

    public void onExitClick(View view) {
        this.finish();
    }

    public void onInfoClick(View view) {
        final String appInfo = String.format(
                getString(R.string.app_info_toast),
                BuildConfig.BUILD_TYPE,
                BuildConfig.FLAVOR_country,
                BuildConfig.APP_COST);
        Toast.makeText(this, appInfo, Toast.LENGTH_LONG).show();
    }

    public void onLoginClick(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }

    @Override
    public Object onRetainCustomNonConfigurationInstance() {
        if (BuildConfig.DEBUG) {
            Log.d(MAIN_TAG, CONFIGURATION_CHANGED);
        }
        //call BackgroundTask method onRotate..?
        mBackgroundTask.onRotate();
        return super.onRetainCustomNonConfigurationInstance();
    }

    public void onSettingsClick(View view) {
        mBackgroundTask.performAction(new IThreadActionImpl(), new IActionCallbackImpl());
    }
}

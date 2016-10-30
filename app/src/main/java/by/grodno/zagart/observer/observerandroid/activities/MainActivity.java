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
import by.grodno.zagart.observer.observerandroid.cache.helper.DbHelper;
import by.grodno.zagart.observer.observerandroid.cache.model.User;
import by.grodno.zagart.observer.observerandroid.threadings.ThreadWorker;

/**
 * Application main activity.
 */
public class MainActivity extends AppCompatActivity {
    public static final String BACKGROUND_TASK_NAME = ThreadWorker.class.getSimpleName();
    public static final String CONFIGURATION_CHANGED = "Configuration changed.";
    public static final String MAIN_TAG = MainActivity.class.getSimpleName();
    private ThreadWorker mThreadWorker = new ThreadWorker(BACKGROUND_TASK_NAME);

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ActionBar bar = getSupportActionBar();
        if (bar != null) {
            bar.hide();
        }
        setContentView(R.layout.main_activity);
        //call ThreadWorker method onStart()..?
        mThreadWorker.onStart();
        DbHelper.getTableCreateQuery(User.UserContract.class);
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

    public void onMyAccountClick(View view) {
        Intent intent = new Intent(this, MyAccountActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }

    @Override
    public Object onRetainCustomNonConfigurationInstance() {
        if (BuildConfig.DEBUG) {
            Log.d(MAIN_TAG, CONFIGURATION_CHANGED);
        }
        //call ThreadWorker method onRotate..?
        mThreadWorker.onRotate();
        return super.onRetainCustomNonConfigurationInstance();
    }

    public void onSettingsClick(View view) {
    }
}

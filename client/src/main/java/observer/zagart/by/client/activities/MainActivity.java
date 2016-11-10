package observer.zagart.by.client.activities;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.IOException;

import observer.zagart.by.client.BuildConfig;
import observer.zagart.by.client.R;
import observer.zagart.by.client.http.HttpClientFactory;
import observer.zagart.by.client.server.requests.GetStandsRequest;
import observer.zagart.by.client.singletons.AccountHolder;
import observer.zagart.by.client.singletons.ContextHolder;
import observer.zagart.by.client.threadings.ThreadWorker;
import observer.zagart.by.client.utils.AndroidUtil;

/**
 * Application main activity.
 */
public class MainActivity extends AppCompatActivity {
    public static final String CONFIGURATION_CHANGED = "Configuration changed.";
    public static final String MAIN_TAG = MainActivity.class.getSimpleName();
    private Button mModulesButton;
    private Button mStandsButton;
    private ThreadWorker mWorker = ThreadWorker.getDefaultInstance();

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ActionBar bar = getSupportActionBar();
        if (bar != null) {
            bar.hide();
        }
        setContentView(R.layout.main_activity);
        mModulesButton = (Button) findViewById(R.id.main_menu_btn_modules);
        mStandsButton = (Button) findViewById(R.id.main_menu_btn_stands);
    }

    @Override
    protected void onStart() {
        super.onStart();
        ContextHolder.set(this);
        if (AccountHolder.get() != null) {
            mModulesButton.setVisibility(View.VISIBLE);
            mStandsButton.setVisibility(View.VISIBLE);
        } else {
            mModulesButton.setVisibility(View.GONE);
            mStandsButton.setVisibility(View.GONE);
        }
    }

    public void onExitClick(View view) {
        moveTaskToBack(true);
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(1);
    }

    public void onModulesClick(View view) {
        mWorker.execute(
                new Runnable() {
                    @Override
                    public void run() {
                        try {
                            final String result = HttpClientFactory
                                    .getDefaultClient()
                                    .executeRequest(
                                            new GetStandsRequest()
                                    );
                            if (!TextUtils.isEmpty(result)) {
                                AndroidUtil.postMessage(result);
                            } else {
                                AndroidUtil.postMessage(R.string.err_failed_get_data);
                            }
                        } catch (IOException pEx) {
                            if (BuildConfig.DEBUG) {
                                Log.e(MainActivity.class.getSimpleName(), pEx.getMessage(), pEx);
                            }
                        }
                    }
                }
        );
    }

    public void onMyAccountClick(View view) {
        AndroidUtil.startActivity(MyAccountActivity.class);
    }

    @Override
    public Object onRetainCustomNonConfigurationInstance() {
        if (BuildConfig.DEBUG) {
            Log.d(MAIN_TAG, CONFIGURATION_CHANGED);
        }
        return super.onRetainCustomNonConfigurationInstance();
    }

    public void onStandsClick(View view) {
        AndroidUtil.startActivity(StandsActivity.class);
    }
}

package observer.zagart.by.client.mvp.views;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.widget.Button;

import observer.zagart.by.client.App;
import observer.zagart.by.client.R;

/**
 * Application main activity.
 */
public class MainActivity extends BaseActivity {

    private Button mModulesButton;
    private Button mStandsButton;

    public void onExitClick(View view) {
        moveTaskToBack(true);
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(1);
    }

    public void onModulesClick(View view) {
        startActivity(new Intent(this, ModulesActivity.class));
    }

    public void onMyAccountClick(View view) {
        final Intent intent = new Intent(this, MyAccountActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
    }

    public void onStandsClick(View view) {
        startActivity(new Intent(this, StandsActivity.class));
    }

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
    public void onViewsVisibilityCheck() {
        if (App.getState().getAccount() != null) {
            mModulesButton.setVisibility(View.VISIBLE);
            mStandsButton.setVisibility(View.VISIBLE);
        } else {
            mModulesButton.setVisibility(View.GONE);
            mStandsButton.setVisibility(View.GONE);
        }
    }
}

package observer.zagart.by.client.activities;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import observer.zagart.by.client.App;
import observer.zagart.by.client.R;
import observer.zagart.by.client.singletons.AccountHolder;
import observer.zagart.by.client.utils.SharedPreferencesUtil;

/**
 * Activity for settings of user account.
 *
 * @author zagart
 */
public class MyAccountActivity extends BaseActivity {
    private Button mLogInView;
    private Button mLogOutView;
    private TextView mUserLabel;
    private TextView mUserLogin;

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ActionBar bar = getSupportActionBar();
        if (bar != null) {
            bar.hide();
        }
        setContentView(R.layout.my_account_activity);
        mLogInView = (Button) findViewById(R.id.my_account_btn_log_in);
        mLogOutView = (Button) findViewById(R.id.my_account_btn_log_out);
        mUserLabel = (TextView) findViewById(R.id.my_account_login_label);
        mUserLogin = (TextView) findViewById(R.id.my_account_login);
    }

    public void onLoginClick(View view) {
        Intent intent = new Intent(this, AuthenticationActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
    }

    public void onLogoutClick(View pView) {
        AccountHolder.set(null);
        SharedPreferencesUtil.persistStringValue(this, App.CURRENT_ACCOUNT_NAME, null);
        onStart();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (AccountHolder.get() != null) {
            mLogInView.setVisibility(View.GONE);
            mLogOutView.setVisibility(View.VISIBLE);
            mUserLabel.setVisibility(View.VISIBLE);
            mUserLogin.setVisibility(View.VISIBLE);
            mUserLogin.setText(AccountHolder.get().name);
        } else {
            mLogInView.setVisibility(View.VISIBLE);
            mLogOutView.setVisibility(View.GONE);
            mUserLabel.setVisibility(View.GONE);
            mUserLogin.setVisibility(View.GONE);
            mUserLogin.setText("");
        }
    }
}

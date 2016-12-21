package observer.zagart.by.client.mvp.views;

import android.accounts.Account;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import observer.zagart.by.client.App;
import observer.zagart.by.client.R;
import observer.zagart.by.client.application.accounts.ObserverAccount;
import observer.zagart.by.client.mvp.IMvp;
import observer.zagart.by.client.mvp.views.base.BaseNavigationActivity;

import static observer.zagart.by.client.application.constants.ApplicationConstants.EMPTY_STRING;

/**
 * Application main activity.
 */
public class MainActivity
        extends BaseNavigationActivity implements IMvp.IViewOperations<ObserverAccount> {

    private Button mLogInButton;
    private Button mLogOutButton;
    private TextView mUserLabel;
    private TextView mUserLogin;

    @Override
    public Context getViewContext() {
        return this;
    }

    @Override
    public void onDataChanged(final List<ObserverAccount> pAccounts) {
        onAccountChanged();
    }

    public void onMenuItemClick(final MenuItem pItem) {
        switch (pItem.getItemId()) {
            case R.id.menu_item_my_account:
                closeDrawers();
                break;
            case R.id.menu_item_content:
                startActivity(new Intent(this, ContentActivity.class));
                break;
            case R.id.menu_item_settings:
                startActivity(new Intent(this, SettingsActivity.class));
                break;
        }
        closeDrawers();
    }

    @Override
    public void onAccountChanged() {
        super.onAccountChanged();
        final Account account = App.getAccount();
        if (account != null) {
            mLogInButton.setVisibility(View.GONE);
            mLogOutButton.setVisibility(View.VISIBLE);
            mUserLabel.setVisibility(View.VISIBLE);
            mUserLogin.setVisibility(View.VISIBLE);
            mUserLogin.setText(account.name);
        } else {
            mLogInButton.setVisibility(View.VISIBLE);
            mLogOutButton.setVisibility(View.GONE);
            mUserLabel.setVisibility(View.GONE);
            mUserLogin.setVisibility(View.GONE);
            mUserLogin.setText(EMPTY_STRING);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        onAccountChanged();
    }

    @Override
    protected void onCreate(@Nullable final Bundle pSavedInstanceState) {
        super.onCreate(pSavedInstanceState);
        setContentView(R.layout.activity_main);
        addToolbar();
        addNavigationMenu();
        mLogInButton = (Button) findViewById(R.id.button_log_in);
        mLogInButton.setOnClickListener(pView -> onLogInClick());
        mLogOutButton = (Button) findViewById(R.id.button_log_out);
        mLogOutButton.setOnClickListener(pView -> onLogoutClick());
        mUserLabel = (TextView) findViewById(R.id.textview_my_account_user_label);
        mUserLogin = (TextView) findViewById(R.id.textview_my_account_user_login);
    }

    private void onLogInClick() {
        final Intent intent = new Intent(this, AuthenticationActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
    }

    private void onLogoutClick() {
        App.setAccount(null);
        onAccountChanged();
    }
}

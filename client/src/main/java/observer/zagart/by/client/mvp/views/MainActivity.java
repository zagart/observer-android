package observer.zagart.by.client.mvp.views;

import android.accounts.Account;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.view.MenuItem;
import android.view.View;

import java.util.List;

import observer.zagart.by.client.App;
import observer.zagart.by.client.R;
import observer.zagart.by.client.application.accounts.ObserverAccount;
import observer.zagart.by.client.application.utils.AnimationUtil;
import observer.zagart.by.client.mvp.IMvp;
import observer.zagart.by.client.mvp.views.base.BaseNavigationActivity;

/**
 * Application main activity.
 */
public class MainActivity
        extends BaseNavigationActivity implements IMvp.IViewOperations<ObserverAccount> {

    private AppCompatButton mLogInButton;
    private AppCompatButton mLogOutButton;
    private AppCompatButton mReviewContent;
    private AppCompatTextView mUserLogin;

    @Override
    public Context getViewContext() {
        return this;
    }

    @Override
    public void onDataChanged(final List<ObserverAccount> pAccounts) {
        checkAccount();
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

    public void onLogInClick(final View pView) {
        final Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
    }

    @Override
    public void checkAccount() {
        super.checkAccount();
        final Account account = App.getAccount();
        if (account != null) {
            mUserLogin.setText(account.name);
            mLogInButton.setVisibility(View.GONE);
            mLogOutButton.setVisibility(View.VISIBLE);
            mReviewContent.setVisibility(View.VISIBLE);
            mUserLogin.setVisibility(View.VISIBLE);
        } else {
            AnimationUtil.fadeOut(mLogOutButton);
            mLogOutButton.setVisibility(View.GONE);
            AnimationUtil.makeOut(false, mReviewContent);
            mReviewContent.setVisibility(View.INVISIBLE);
            AnimationUtil.makeOut(true, mUserLogin);
            mUserLogin.setVisibility(View.INVISIBLE);
            mLogInButton.setVisibility(View.VISIBLE);
        }
    }

    public void onLogOutClick(final View pView) {
        App.setAccount(null);
        checkAccount();
    }

    @Override
    protected void onStart() {
        super.onStart();
        checkAccount();
    }

    @Override
    protected void onCreate(@Nullable final Bundle pSavedInstanceState) {
        super.onCreate(pSavedInstanceState);
        setContentView(R.layout.activity_main);
        addToolbar().addNavigationMenu();
        mLogInButton = (AppCompatButton) findViewById(R.id.button_log_in);
        mLogOutButton = (AppCompatButton) findViewById(R.id.button_log_out);
        mUserLogin = (AppCompatTextView) findViewById(R.id.textview_user_login);
        mReviewContent = (AppCompatButton) findViewById(R.id.button_review_content);
        mReviewContent.setOnClickListener((pEvent) ->
                startActivity(new Intent(this, ContentActivity.class)));
    }
}

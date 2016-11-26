package observer.zagart.by.client.mvp.views;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.widget.Button;

import java.util.List;

import observer.zagart.by.client.App;
import observer.zagart.by.client.R;
import observer.zagart.by.client.application.accounts.ObserverAccount;
import observer.zagart.by.client.mvp.IMvp;
import observer.zagart.by.client.mvp.presenters.base.BasePresenter;
import observer.zagart.by.client.mvp.views.base.BaseView;

/**
 * Application main activity.
 */
public class MainActivity extends BaseView implements IMvp.IViewOperations<ObserverAccount> {

    private Button mModulesButton;
    private Button mStandsButton;

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
    public void onAccountCheck() {
        if (App.getAccount() != null) {
            mModulesButton.setVisibility(View.VISIBLE);
            mStandsButton.setVisibility(View.VISIBLE);
        } else {
            mModulesButton.setVisibility(View.GONE);
            mStandsButton.setVisibility(View.GONE);
        }
    }

    @Override
    public void onDataChanged(final List<ObserverAccount> pAccounts) {
        onAccountCheck();
    }

    @Override
    protected void onStart() {
        super.onStart();
        onAccountCheck();
    }

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ActionBar bar = getSupportActionBar();
        if (bar != null) {
            bar.hide();
        }
        setContentView(R.layout.activity_main);
        mModulesButton = (Button) findViewById(R.id.main_menu_btn_modules);
        mStandsButton = (Button) findViewById(R.id.main_menu_btn_stands);
    }
}

package observer.zagart.by.client.mvp.views;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.util.List;

import observer.zagart.by.client.R;
import observer.zagart.by.client.application.accounts.ObserverAccount;
import observer.zagart.by.client.mvp.IMvp;
import observer.zagart.by.client.mvp.presenters.base.BasePresenter;
import observer.zagart.by.client.mvp.views.base.BaseView;

/**
 * Application main activity.
 */
public class MainActivity extends BaseView
        implements IMvp.IViewOperations<ObserverAccount> {

    private Button mDataButton;
    private Button mModulesButton;
    private Button mStandsButton;

    public void onModulesClick(final View pView) {
        startActivity(new Intent(this, ModulesActivity.class));
    }

    public void onNavigationClick(final View pView) {
    }

    public void onDataClick(final View pView) {
        startActivity(new Intent(this, DataActivity.class));
    }

    public void onMyAccountClick(final View pView) {
        final Intent intent = new Intent(this, MyAccountActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
    }

    public void onStandsClick(final View pView) {
        startActivity(new Intent(this, StandsActivity.class));
    }

    @Override
    public void onAccountCheck() {
        //TODO remake
//        if (App.getAccount() != null) {
//            mDataButton.setVisibility(View.VISIBLE);
//            mModulesButton.setVisibility(View.VISIBLE);
//            mStandsButton.setVisibility(View.VISIBLE);
//        } else {
//            mDataButton.setVisibility(View.GONE);
//            mModulesButton.setVisibility(View.GONE);
//            mStandsButton.setVisibility(View.GONE);
//        }
    }

    @Override
    public void onDataChanged(final List<ObserverAccount> pAccounts) {
        onAccountCheck();
    }

    public void onMyAccountItemClick(MenuItem pItem) {
        onMyAccountClick(null);
    }

    public void onDataItemClick(MenuItem pItem) {
        onDataClick(null);
    }

    public void onModulesItemClick(MenuItem pItem) {
        onModulesClick(null);
    }

    public void onStandsItemClick(MenuItem pItem) {
        onStandsClick(null);
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
    protected void onCreate(@Nullable final Bundle pSavedInstanceState) {
        super.onCreate(pSavedInstanceState);
        setContentView(R.layout.navigation_container);
        mDataButton = (Button) findViewById(R.id.main_menu_btn_data);
        mModulesButton = (Button) findViewById(R.id.main_menu_btn_modules);
        mStandsButton = (Button) findViewById(R.id.main_menu_btn_stands);
        final DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.main_drawer_layout);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        final ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                toolbar,
                R.string.drawer_opened,
                R.string.drawer_closed);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.setDrawerIndicatorEnabled(true);
    }
}

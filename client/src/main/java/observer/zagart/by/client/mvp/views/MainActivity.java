package observer.zagart.by.client.mvp.views;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.List;

import observer.zagart.by.client.App;
import observer.zagart.by.client.R;
import observer.zagart.by.client.application.accounts.ObserverAccount;
import observer.zagart.by.client.application.constants.ApplicationConstants;
import observer.zagart.by.client.mvp.IMvp;
import observer.zagart.by.client.mvp.views.base.BaseView;
import observer.zagart.by.client.mvp.views.fragments.DataFragment;
import observer.zagart.by.client.mvp.views.fragments.ModulesFragment;
import observer.zagart.by.client.mvp.views.fragments.MyAccountFragment;
import observer.zagart.by.client.mvp.views.fragments.SettingsFragment;
import observer.zagart.by.client.mvp.views.fragments.StandsFragment;

/**
 * Application main activity.
 */
public class MainActivity
        extends AppCompatActivity implements IMvp.IViewOperations<ObserverAccount> {

    private DrawerLayout mDrawerLayout;
    private MyAccountFragment mFragmentContainer;
    private Menu mMenu;
    private ActionBarDrawerToggle mDrawerToggle;

    @Override
    public Context getViewContext() {
        return this;
    }

    @Override
    public void onDataChanged(final List<ObserverAccount> pAccounts) {
        mFragmentContainer.onAccountCheck();
    }

    public void onMenuItemClick(MenuItem pItem) {
        switch (pItem.getItemId()) {
            case R.id.menu_item_my_account:
                mFragmentContainer.changeFragment(this, new MyAccountFragment());
                break;
            case R.id.menu_item_settings:
                mFragmentContainer.changeFragment(this, new SettingsFragment());
                break;
            case R.id.menu_item_data:
                mFragmentContainer.changeFragment(this, new DataFragment());
                break;
            case R.id.menu_item_modules:
                mFragmentContainer.changeFragment(this, new ModulesFragment());
                break;
            case R.id.menu_item_stands:
                mFragmentContainer.changeFragment(this, new StandsFragment());
                break;
        }
        mDrawerLayout.closeDrawers();
    }

    @Override
    public boolean onPrepareOptionsMenu(final Menu pMenu) {
        super.onPrepareOptionsMenu(pMenu);
        final NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
        final TextView navSubtitle = (TextView) navigationView
                .getHeaderView(0)
                .findViewById(R.id.header_subtitle);
        if (pMenu != null && pMenu.size() > 0) {
            if (App.getAccount() != null) {
                navSubtitle.setText(App.getAccount().name);
                updateMenuItemsVisibility(pMenu, true);
            } else {
                navSubtitle.setText(R.string.user_login_default);
                updateMenuItemsVisibility(pMenu, false);
            }
        }
        return true;
    }

    public void updateMenuItemsVisibility(final Menu pMenu, final boolean pIsVisible) {
        if (pMenu == null) {
            mMenu.getItem(ApplicationConstants.MENU_DATA_ITEM_INDEX).setVisible(pIsVisible);
        } else {
            pMenu.getItem(ApplicationConstants.MENU_DATA_ITEM_INDEX).setVisible(pIsVisible);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mFragmentContainer.onAccountCheck();
    }

    @Override
    protected void onCreate(@Nullable final Bundle pSavedInstanceState) {
        super.onCreate(pSavedInstanceState);
        setContentView(R.layout.activity_main_navigation);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.main_drawer_layout);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);
        mDrawerToggle = getDrawerToggle(toolbar);
        mDrawerToggle.setDrawerIndicatorEnabled(true);
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        mDrawerLayout.addDrawerListener(mDrawerToggle);
        final NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
        mMenu = navigationView.getMenu();
        mFragmentContainer = (MyAccountFragment) BaseView.setUpContainer(this);
    }

    @Override
    protected void onPostCreate(@Nullable final Bundle pSavedInstanceState) {
        super.onPostCreate(pSavedInstanceState);
        mDrawerToggle.syncState();
    }

    @NonNull
    private ActionBarDrawerToggle getDrawerToggle(final Toolbar pToolbar) {
        return new ActionBarDrawerToggle(
                this,
                mDrawerLayout,
                pToolbar,
                R.string.drawer_opened,
                R.string.drawer_closed);
    }
}

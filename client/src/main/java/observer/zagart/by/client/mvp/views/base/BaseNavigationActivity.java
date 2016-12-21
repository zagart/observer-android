package observer.zagart.by.client.mvp.views.base;

import android.accounts.Account;
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
import android.widget.TextView;

import observer.zagart.by.client.App;
import observer.zagart.by.client.R;
import observer.zagart.by.client.application.constants.ApplicationConstants;

/**
 * Base activity provides access to navigation menu and action bar.
 *
 * @author zagart
 */

public class BaseNavigationActivity extends AppCompatActivity {

    private static final int HEADER_SUBTITLE_INDEX = 0;

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private TextView mAccountName;
    private Toolbar mToolbar;
    private Menu mMenu;

    public void onAccountChanged() {
        final Account account = App.getAccount();
        if (mMenu != null) {
            if (account != null) {
                mAccountName.setText(account.name);
                updateMenuItemsVisibility(mMenu, true);
            } else {
                mAccountName.setText(getString(R.string.user_login_default));
                updateMenuItemsVisibility(mMenu, false);
            }
            invalidateOptionsMenu();
        }
    }

    @Override
    public boolean onPrepareOptionsMenu(final Menu pMenu) {
        super.onPrepareOptionsMenu(pMenu);
        if (pMenu != null && pMenu.size() > 0) {
            if (App.getAccount() != null) {
                updateMenuItemsVisibility(pMenu, true);
            } else {
                updateMenuItemsVisibility(pMenu, false);
            }
        }
        return true;
    }

    protected Toolbar getToolbar() {
        return mToolbar;
    }

    protected void addToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(mToolbar);
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    protected void onPostCreate(@Nullable final Bundle pSavedInstanceState) {
        super.onPostCreate(pSavedInstanceState);
        if (mDrawerToggle != null) {
            mDrawerToggle.syncState();
        }
    }

    protected void closeDrawers() {
        if (mDrawerLayout != null) {
            mDrawerLayout.closeDrawers();
        }
    }

    protected void addNavigationMenu() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.main_drawer_layout);
        mDrawerToggle = getDrawerToggle(getToolbar());
        mDrawerLayout.addDrawerListener(mDrawerToggle);
        mDrawerToggle.setDrawerIndicatorEnabled(true);
        final NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
        mMenu = navigationView.getMenu();
        mAccountName = (TextView) navigationView
                .getHeaderView(HEADER_SUBTITLE_INDEX)
                .findViewById(R.id.header_subtitle);
    }

    private void updateMenuItemsVisibility(final Menu pMenu, final boolean pIsVisible) {
        if (mMenu != null) {
            mMenu.getItem(ApplicationConstants.MENU_CONTENT_ITEM_INDEX).setVisible(pIsVisible);
        } else if (pMenu != null) {
            pMenu.getItem(ApplicationConstants.MENU_CONTENT_ITEM_INDEX).setVisible(pIsVisible);
        }
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

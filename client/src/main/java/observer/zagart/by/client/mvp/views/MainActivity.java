package observer.zagart.by.client.mvp.views;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import java.util.List;

import observer.zagart.by.client.R;
import observer.zagart.by.client.application.accounts.ObserverAccount;
import observer.zagart.by.client.mvp.IMvp;
import observer.zagart.by.client.mvp.views.fragments.DataFragment;
import observer.zagart.by.client.mvp.views.fragments.MainFragment;
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
    private MainFragment mMainFragment;

    @Override
    public Context getViewContext() {
        return this;
    }

    @Override
    public void onDataChanged(final List<ObserverAccount> pAccounts) {
        mMainFragment.onAccountCheck();
    }

    public void onMenuItemClick(MenuItem pItem) {
        switch (pItem.getItemId()) {
            case R.id.menu_item_my_account:
                mMainFragment.changeMainFragment(this, new MyAccountFragment());
                break;
            case R.id.menu_item_settings:
                mMainFragment.changeMainFragment(this, new SettingsFragment());
                break;
            case R.id.menu_item_data:
                mMainFragment.changeMainFragment(this, new DataFragment());
                break;
            case R.id.menu_item_modules:
                mMainFragment.changeMainFragment(this, new ModulesFragment());
                break;
            case R.id.menu_item_stands:
                mMainFragment.changeMainFragment(this, new StandsFragment());
                break;
        }
        mDrawerLayout.closeDrawers();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mMainFragment.onAccountCheck();
    }

    @Override
    protected void onCreate(@Nullable final Bundle pSavedInstanceState) {
        super.onCreate(pSavedInstanceState);
        setContentView(R.layout.activity_navigation);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.main_drawer_layout);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        final ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(
                this,
                mDrawerLayout,
                toolbar,
                R.string.drawer_opened,
                R.string.drawer_closed);
        mDrawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.setDrawerIndicatorEnabled(true);
        mMainFragment = new MainFragment();
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.drawer_container, mMainFragment)
                .commit();
    }
}

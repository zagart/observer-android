package observer.zagart.by.client.mvp.views;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MenuItem;

import java.util.List;

import observer.zagart.by.client.R;
import observer.zagart.by.client.application.accounts.ObserverAccount;
import observer.zagart.by.client.mvp.IMvp;
import observer.zagart.by.client.mvp.views.base.BaseNavigationActivity;
import observer.zagart.by.client.mvp.views.fragments.DataFragment;
import observer.zagart.by.client.mvp.views.fragments.ModulesFragment;
import observer.zagart.by.client.mvp.views.fragments.MyAccountFragment;
import observer.zagart.by.client.mvp.views.fragments.SettingsFragment;
import observer.zagart.by.client.mvp.views.fragments.StandsFragment;

/**
 * Application main activity.
 */
public class MainActivity
        extends BaseNavigationActivity implements IMvp.IViewOperations<ObserverAccount> {

    private MyAccountFragment mFragmentContainer;

    @Override
    public Context getViewContext() {
        return this;
    }

    @Override
    public void onDataChanged(final List<ObserverAccount> pAccounts) {
        mFragmentContainer.onAccountCheck();
    }

    public void onMenuItemClick(final MenuItem pItem) {
        switch (pItem.getItemId()) {
            case R.id.menu_item_my_account:
                mFragmentContainer.changeFragment(this, new MyAccountFragment());
                break;
            case R.id.menu_item_settings:
                mFragmentContainer.changeFragment(this, new SettingsFragment());
                break;
            case R.id.menu_item_content:
                startActivity(new Intent(this, ContentActivity.class));
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
        closeDrawers();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mFragmentContainer.onAccountCheck();
    }

    @Override
    protected void onCreate(@Nullable final Bundle pSavedInstanceState) {
        super.onCreate(pSavedInstanceState);
        setContentView(R.layout.activity_main);
        addToolbar();
        addNavigationMenu();
        mFragmentContainer = (MyAccountFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragment_container);
    }
}

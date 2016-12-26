package observer.zagart.by.client.mvp.views;

import android.os.Bundle;
import android.support.annotation.Nullable;

import observer.zagart.by.client.R;
import observer.zagart.by.client.mvp.views.base.BaseNavigationActivity;
import observer.zagart.by.client.mvp.views.fragments.SettingsFragment;

/**
 * Dummy settings activity.
 *
 * @author zagart
 */

public class SettingsActivity extends BaseNavigationActivity {

    @Override
    protected void onCreate(@Nullable final Bundle pSavedInstanceState) {
        super.onCreate(pSavedInstanceState);
        setContentView(R.layout.activity_settings_container);
        addToolbar().setToolbarTitle(R.string.settings);
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.settings_container, new SettingsFragment())
                .commit();
    }
}

package observer.zagart.by.client.mvp.views;

import android.os.Bundle;
import android.support.annotation.Nullable;

import observer.zagart.by.client.R;
import observer.zagart.by.client.application.constants.ApplicationConstants;
import observer.zagart.by.client.application.utils.TestUtil;
import observer.zagart.by.client.mvp.views.base.BaseNavigationActivity;

/**
 * Dummy settings activity.
 *
 * @author zagart
 */

public class SettingsActivity extends BaseNavigationActivity {

    @Override
    protected void onCreate(@Nullable final Bundle pSavedInstanceState) {
        super.onCreate(pSavedInstanceState);
        setContentView(TestUtil.getDummyLayout(
                this,
                getString(R.string.settings),
                ApplicationConstants.EMPTY_STRING));
        addToolbar();
    }

    @Override
    protected void onStart() {
        super.onStart();
        setTitle(R.string.settings);
    }
}

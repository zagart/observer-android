package observer.zagart.by.client.mvp.views;

import android.os.Bundle;
import android.support.annotation.Nullable;

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
        addToolbar();
        setContentView(TestUtil.getDummyLayout(this, "Settings", "No body"));
    }
}

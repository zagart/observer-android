package observer.zagart.by.client.mvp.views;

import android.os.Bundle;
import android.support.annotation.Nullable;

import observer.zagart.by.client.R;
import observer.zagart.by.client.mvp.views.base.BaseNavigationActivity;

/**
 * Activity which shows to user content of application.
 *
 * @author zagart
 */

public class ContentActivity extends BaseNavigationActivity {

    @Override
    protected void onCreate(@Nullable final Bundle pSavedInstanceState) {
        super.onCreate(pSavedInstanceState);
        setContentView(R.layout.activity_content);
        addToolbar();
        setTitle(R.string.content);
    }
}

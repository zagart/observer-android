package observer.zagart.by.client.mvp.views;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import observer.zagart.by.client.R;
import observer.zagart.by.client.application.constants.UIConstants;
import observer.zagart.by.client.mvp.views.adapters.ContentPagerAdapter;
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
        addToolbar().setToolbarTitle(R.string.content);
        final ViewPager contentViewPager = (ViewPager) findViewById(R.id.view_pager_content);
        contentViewPager.setAdapter(new ContentPagerAdapter(getSupportFragmentManager()));
        final TabLayout contentTabLayout = (TabLayout) findViewById(R.id.tab_layout_content);
        setUpContentTabLayout(contentTabLayout, contentViewPager);
    }

    @Override
    protected void onStart() {
        super.onStart();
        checkAccount();
    }

    @SuppressWarnings("ConstantConditions")
    private void setUpContentTabLayout(final TabLayout pTabLayout,
                                       final ViewPager pContentViewPager) {
        pTabLayout.setupWithViewPager(pContentViewPager);
        pTabLayout.getTabAt(UIConstants.DATA_PAGE_INDEX).setIcon(R.drawable.ic_data_24dp);
        pTabLayout.getTabAt(UIConstants.MODULES_PAGE_INDEX).setIcon(R.drawable.ic_module_24dp);
        pTabLayout.getTabAt(UIConstants.STANDS_PAGE_INDEX).setIcon(R.drawable.ic_stand_24dp);
    }
}

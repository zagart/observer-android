package observer.zagart.by.client.mvp.views.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatDelegate;

import observer.zagart.by.client.App;
import observer.zagart.by.client.R;
import observer.zagart.by.client.application.constants.UIConstants;
import observer.zagart.by.client.mvp.views.ContentActivity;
import observer.zagart.by.client.mvp.views.fragments.DataFragment;
import observer.zagart.by.client.mvp.views.fragments.ModulesFragment;
import observer.zagart.by.client.mvp.views.fragments.StandsFragment;

/**
 * Adapter for {@link ContentActivity} view pager.
 *
 * @author zagart
 * @see ViewPager
 */

public class ContentPagerAdapter extends FragmentStatePagerAdapter {

    public ContentPagerAdapter(final FragmentManager pFragmentManager) {
        super(pFragmentManager);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    @Override
    public CharSequence getPageTitle(final int pPosition) {
        switch (pPosition) {
            case UIConstants.DATA_PAGE_INDEX:
                return App.getContext().getString(R.string.data);
            case UIConstants.MODULES_PAGE_INDEX:
                return App.getContext().getString(R.string.modules);
            case UIConstants.STANDS_PAGE_INDEX:
                return App.getContext().getString(R.string.stands);
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return UIConstants.TABS_COUNT;
    }

    @Override
    public Fragment getItem(final int pPosition) {
        switch (pPosition) {
            case UIConstants.DATA_PAGE_INDEX:
                return new DataFragment();
            case UIConstants.MODULES_PAGE_INDEX:
                return new ModulesFragment();
            case UIConstants.STANDS_PAGE_INDEX:
                return new StandsFragment();
            default:
                return null;
        }
    }
}

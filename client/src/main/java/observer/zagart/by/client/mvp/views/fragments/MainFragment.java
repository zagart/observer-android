package observer.zagart.by.client.mvp.views.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import observer.zagart.by.client.application.utils.TestUtil;
import observer.zagart.by.client.mvp.presenters.base.BasePresenter;
import observer.zagart.by.client.mvp.views.base.BaseView;

/**
 * Fragment with initial content of application.
 *
 * @author zagart
 */

public class MainFragment extends BaseView {

    private static final String HEADER = "Main menu";
    private static final String BODY = "Empty body.";

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater pInflater,
                             final ViewGroup pContainer,
                             final Bundle pSavedInstanceState) {
        return TestUtil.getDummyLayout(this, HEADER, BODY);
    }

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }
}

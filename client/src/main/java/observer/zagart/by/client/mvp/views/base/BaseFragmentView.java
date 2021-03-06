package observer.zagart.by.client.mvp.views.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import observer.zagart.by.client.R;
import observer.zagart.by.client.mvp.presenters.base.BasePresenter;

/**
 * Base activity of application.
 *
 * @author zagart
 */
abstract public class BaseFragmentView extends Fragment {

    private View mCoordinatorLayout;
    private ProgressBar mProgressBar;

    public Context getViewContext() {
        return getActivity();
    }

    public void onDataLoadSuccess() {
        showSnackBar(R.string.success_load_data);
    }

    public void onDataLoadFail() {
        showSnackBar(R.string.failed_load_data);
    }

    @Override
    public void onResume() {
        super.onResume();
        final BasePresenter presenter = getPresenter();
        if (presenter != null) {
            presenter.registerObserver();
        }
    }

    @Override
    public void onActivityCreated(@Nullable final Bundle pSavedInstanceState) {
        super.onActivityCreated(pSavedInstanceState);
        mProgressBar = (ProgressBar) getActivity().findViewById(getProgressBarResId());
        mCoordinatorLayout = getActivity().findViewById(R.id.activity_content);
    }

    @Override
    public void onPause() {
        super.onPause();
        final BasePresenter presenter = getPresenter();
        if (presenter != null) {
            getPresenter().unregisterObserver();
        }
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater pInflater,
                             final ViewGroup pContainer,
                             final Bundle pSavedInstanceState) {
        setHasOptionsMenu(true);
        return super.onCreateView(pInflater, pContainer, pSavedInstanceState);
    }

    public void showProgressBar() {
        if (mProgressBar != null) {
            mProgressBar.setVisibility(View.VISIBLE);
        }
    }

    public void hideProgressBar() {
        if (mProgressBar != null) {
            mProgressBar.setVisibility(View.INVISIBLE);
        }
    }

    protected View getLayoutWithPanel(final LayoutInflater pInflater,
                                      final ViewGroup pContainer,
                                      final int pResId,
                                      final View.OnClickListener pClearListener,
                                      final View.OnClickListener pReloadListener) {
        final View layout = pInflater.inflate(pResId, pContainer, false);
        final ImageButton clearButton = (ImageButton) layout.findViewById(R.id.top_panel_clear);
        clearButton.setOnClickListener(pClearListener);
        final ImageButton reloadButton = (ImageButton) layout.findViewById(R.id.top_panel_reload);
        reloadButton.setOnClickListener(pReloadListener);
        return layout;
    }

    abstract protected BasePresenter getPresenter();

    abstract protected int getProgressBarResId();

    private void showSnackBar(final int pResID) {
        if (mCoordinatorLayout instanceof CoordinatorLayout) {
            final Snackbar snackBar = Snackbar.make(
                    mCoordinatorLayout,
                    pResID,
                    Snackbar.LENGTH_INDEFINITE);
            snackBar.show();
        }
    }
}

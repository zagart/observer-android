package observer.zagart.by.client.mvp.views.base;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import observer.zagart.by.client.App;
import observer.zagart.by.client.R;
import observer.zagart.by.client.application.utils.IOUtil;
import observer.zagart.by.client.mvp.presenters.base.BasePresenter;
import observer.zagart.by.client.mvp.views.fragments.MainFragment;

/**
 * Base activity of application.
 *
 * @author zagart
 */
abstract public class BaseView extends Fragment {

    public static Fragment setUpContainer(final Activity pActivity) {
        final MainFragment fragment = new MainFragment();
        pActivity.getFragmentManager()
                .beginTransaction()
                .replace(R.id.drawer_container, fragment)
                .commit();
        return fragment;
    }

    public void onAccountCheck() {
        if (App.getAccount() == null) {
            changeFragment(getActivity(), new MainFragment());
            IOUtil.showToast(getString(R.string.msg_fragment_redirection));
        }
    }

    public Context getViewContext() {
        return getActivity();
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
    public void onPause() {
        super.onPause();
        final BasePresenter presenter = getPresenter();
        if (presenter != null) {
            getPresenter().unregisterObserver();
        }
    }

    public void changeFragment(final Activity pActivity, final Fragment pFragment) {
        pActivity.getFragmentManager()
                .beginTransaction()
                .replace(R.id.drawer_container, pFragment)
                .addToBackStack(null)
                .commit();
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater pInflater,
                             final ViewGroup pContainer,
                             final Bundle pSavedInstanceState) {
        setHasOptionsMenu(true);
        return super.onCreateView(pInflater, pContainer, pSavedInstanceState);
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
}

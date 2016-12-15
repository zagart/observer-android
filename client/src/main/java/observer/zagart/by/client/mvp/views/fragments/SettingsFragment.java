package observer.zagart.by.client.mvp.views.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import observer.zagart.by.client.R;
import observer.zagart.by.client.application.utils.TestUtil;

/**
 * Fragment with content for configuring user references.
 *
 * @author zagart
 */

public class SettingsFragment extends Fragment {

    private static final String HEADER = "Settings";
    private static final String BODY = "No settings body.";

    @Override
    public void onStart() {
        super.onStart();
        getActivity().setTitle(R.string.settings);
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater pInflater,
                             final ViewGroup pContainer,
                             final Bundle pSavedInstanceState) {
        super.onCreateView(pInflater, pContainer, pSavedInstanceState);
        return TestUtil.getDummyLayout(this, HEADER, BODY);
    }
}

package observer.zagart.by.client.mvp.views.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import observer.zagart.by.client.application.utils.TestUtil;

/**
 * @author zagart
 */

public class MainFragment extends Fragment {

    private static final String HEADER = "Main menu";
    private static final String BODY = "Empty body.";

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater pInflater,
                             final ViewGroup pContainer,
                             final Bundle pSavedInstanceState) {
        return TestUtil.getDummyLayout(this, HEADER, BODY);
    }
}

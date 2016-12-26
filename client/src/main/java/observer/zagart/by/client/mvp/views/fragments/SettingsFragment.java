package observer.zagart.by.client.mvp.views.fragments;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import observer.zagart.by.client.R;

/**
 * Fragment for {@link observer.zagart.by.client.mvp.views.SettingsActivity}.
 *
 * @author zagart
 */

public class SettingsFragment extends PreferenceFragment {

    @Override
    public void onCreate(final Bundle pSavedInstanceState) {
        super.onCreate(pSavedInstanceState);
        addPreferencesFromResource(R.xml.preference_settings);
    }
}

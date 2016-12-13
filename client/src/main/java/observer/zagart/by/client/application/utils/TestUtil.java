package observer.zagart.by.client.application.utils;

import android.app.Activity;
import android.app.Fragment;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import observer.zagart.by.client.R;

/**
 * Utility class with methods for with temporary classes and views.
 *
 * @author zagart
 */

public class TestUtil {

    public static View getDummyLayout(final Fragment pView,
                                      final String pHeader,
                                      final String pBody) {
        return getView(pHeader, pBody, pView.getActivity());
    }

    @NonNull
    private static View getView(final String pHeader,
                                final String pBody,
                                final Activity pActivity) {
        final LayoutInflater inflater = pActivity.getLayoutInflater();
        final View layout = inflater.inflate(
                R.layout.fragment_dummy,
                (ViewGroup) pActivity.findViewById(R.id.fragment_dummy_layout),
                false);
        final TextView header = (TextView) layout.findViewById(R.id.dummy_header);
        final TextView body = (TextView) layout.findViewById(R.id.dummy_body);
        header.setText(pHeader);
        body.setText(pBody);
        return layout;
    }
}
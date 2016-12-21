package observer.zagart.by.client.application.utils;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import java.util.List;

import observer.zagart.by.client.R;
import observer.zagart.by.client.mvp.views.fragments.MyAccountFragment;

/**
 * Utility class with methods for serving fragments.
 *
 * @author zagart
 */

public class FragmentUtil {

    private static final int NAME_POSITION = 0;

    public static void printBackStack(final FragmentManager pFragmentManager) {
        final List<Fragment> fragments = pFragmentManager.getFragments();
        for (int i = 0; i < fragments.size(); i++) {
            final Fragment fragment = fragments.get(i);
            if (fragment != null) {
                Log.i(fragment.getClass().getSimpleName(), String.valueOf(fragment.isAdded()));
            }
        }
    }

    public static void replaceFragment(final FragmentManager pFragmentManager,
                                       final Fragment pFragment,
                                       final boolean pDoAddToBackStack,
                                       final String... pTransactionName) {
        if (pDoAddToBackStack) {
            String name = null;
            if (pTransactionName.length > 0) {
                name = pTransactionName[NAME_POSITION];
            }
            pFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, pFragment)
                    .addToBackStack(name)
                    .commit();
        } else {
            pFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, pFragment)
                    .commit();
        }
    }

    public static void addFragment(final FragmentManager pFragmentManager,
                                   final Fragment pFragment,
                                   final boolean pDoAddToBackStack,
                                   final String... pTransactionName) {
        if (pDoAddToBackStack) {
            String name = null;
            if (pTransactionName.length > 0) {
                name = pTransactionName[NAME_POSITION];
            }
            pFragmentManager.beginTransaction()
                    .add(R.id.fragment_container, pFragment)
                    .addToBackStack(name)
                    .commit();
        } else {
            pFragmentManager.beginTransaction()
                    .add(R.id.fragment_container, pFragment)
                    .commit();
        }
    }

    public static void updateBackStack(final FragmentManager pFragmentManager,
                                       final Fragment... pTopLevelFragments) {
        pFragmentManager.popBackStackImmediate();
        replaceFragment(pFragmentManager, new MyAccountFragment(), true);
        if (pTopLevelFragments.length > 0) {
            final FragmentTransaction transaction = pFragmentManager.beginTransaction();
            for (final Fragment fragment : pTopLevelFragments) {
                transaction.replace(R.id.fragment_container, fragment);
            }
            transaction.commit();
        }
    }
}

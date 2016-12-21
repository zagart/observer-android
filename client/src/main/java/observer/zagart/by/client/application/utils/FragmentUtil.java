package observer.zagart.by.client.application.utils;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;

import java.util.List;

/**
 * Utility class with methods for serving fragments.
 *
 * @author zagart
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public class FragmentUtil {

    public static final int CONTAINER_RES_ID = 0;
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
                    .replace(CONTAINER_RES_ID, pFragment)
                    .addToBackStack(name)
                    .commit();
        } else {
            pFragmentManager.beginTransaction()
                    .replace(CONTAINER_RES_ID, pFragment)
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
                    .add(CONTAINER_RES_ID, pFragment)
                    .addToBackStack(name)
                    .commit();
        } else {
            pFragmentManager.beginTransaction()
                    .add(CONTAINER_RES_ID, pFragment)
                    .commit();
        }
    }
}

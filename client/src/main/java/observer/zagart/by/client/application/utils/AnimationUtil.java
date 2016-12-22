package observer.zagart.by.client.application.utils;

import android.view.View;
import android.view.animation.AnimationUtils;

/**
 * Utility class with methods for processing animations.
 *
 * @author zagart
 */
@SuppressWarnings("unused")
public class AnimationUtil {

    public static void fadeIn(final View... pViews) {
        processBasicAnimation(android.R.anim.fade_in, pViews);
    }

    public static void fadeOut(final View... pViews) {
        processBasicAnimation(android.R.anim.fade_out, pViews);
    }

    public static void makeIn(final boolean pFromLeft, final View... pViews) {
        for (final View view : pViews) {
            if (view.getVisibility() == View.VISIBLE) {
                view.startAnimation(AnimationUtils.makeInAnimation(view.getContext(), pFromLeft));
            }
        }
    }

    public static void makeOut(final boolean pFromRight, final View... pViews) {
        for (final View view : pViews) {
            if (view.getVisibility() == View.VISIBLE) {
                view.startAnimation(AnimationUtils.makeOutAnimation(view.getContext(), pFromRight));
            }
        }
    }

    private static void processBasicAnimation(final int pResId, final View... pViews) {
        for (final View view : pViews) {
            if (view.getVisibility() == View.VISIBLE) {
                view.startAnimation(AnimationUtils.loadAnimation(view.getContext(), pResId));
            }
        }
    }
}

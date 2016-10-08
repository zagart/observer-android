package by.grodno.zagart.observer.observerandroid.utils;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.Date;

import by.grodno.zagart.observer.observerandroid.classes.HappinessFactory;
import by.grodno.zagart.observer.observerandroid.classes.HappyButton;

/**
 * Utility class for managing mood.
 */
public class MoodUtil {

    public static final int SAD_BACKGROUND = Color.rgb(0, 51, 102);

    /**
     * Method adds happiness to sad layout! :)
     *
     * @param sadLayout       Sad layout we will make be more happy
     * @param happinessDegree Level of adding happiness
     * @param sadContext      Reason of sadness layout
     */
    public static void addHappinessToSadLayout(LinearLayout sadLayout,
                                               int happinessDegree,
                                               Context sadContext) {
        HappinessFactory<HappyButton> factory = new HappinessFactory<>(new Date());
        while (happinessDegree > 0) {
            sadLayout.addView(factory.shareHappiness(sadContext));
            happinessDegree--;
        }
    }

    /**
     * Method generates sad layout :(
     *
     * @param sadContext Sad context
     * @return Sad layout
     */
    @NonNull
    public static LinearLayout getSadLinearLayout(Context sadContext) {
        LinearLayout layout = new LinearLayout(sadContext);
        layout.setBackgroundColor(SAD_BACKGROUND);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setGravity(Gravity.CENTER_HORIZONTAL);
        return layout;
    }

    /**
     * Converting pixels into dips.
     *
     * @param context Activity context
     * @param value   Value in pixels
     * @return Value in dips
     */
    public static int pixelsInDip(Context context, int value) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, dm);
    }

    /**
     * Setting event happy listener on view. We must to know, when happiness happens!
     *
     * @param view View for setting
     */
    public static void setHappyOnClickListener(View view) {
        view.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        view.setVisibility(View.GONE);
                    }
                }
        );
    }

    /**
     * Setting width and height of view.
     *
     * @param view   View object
     * @param width  Width in dips
     * @param height Height in dips
     */
    public static void setViewSizeInDip(View view, int width, int height) {
        view.setLayoutParams(
                new ViewGroup.LayoutParams(
                        pixelsInDip(view.getContext(), width),
                        pixelsInDip(view.getContext(), height)
                )
        );
    }
}

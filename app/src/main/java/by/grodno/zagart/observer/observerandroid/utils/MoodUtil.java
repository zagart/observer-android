package by.grodno.zagart.observer.observerandroid.utils;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewManager;
import android.view.Window;
import android.widget.RelativeLayout;

import java.util.Date;
import java.util.Random;

import by.grodno.zagart.observer.observerandroid.SadActivity;
import by.grodno.zagart.observer.observerandroid.classes.HappinessFactory;
import by.grodno.zagart.observer.observerandroid.classes.HappyButton;

import static by.grodno.zagart.observer.observerandroid.classes.HappinessFactory.HAPPY_VIEW_SIZE;

/**
 * Utility class for managing mood.
 */
public class MoodUtil {

    private static final int SAD_BACKGROUND = Color.rgb(0, 51, 102);

    /**
     * Method adds happiness to sad layout! :)
     *
     * @param sadLayout       Sad layout we will make be more happy
     * @param happinessDegree Level of adding happiness
     * @param sadContext      Reason of sadness layout
     */
    public static void addHappinessToSadLayout(RelativeLayout sadLayout,
                                               int happinessDegree,
                                               Context sadContext) {
        HappinessFactory<HappyButton> factory = new HappinessFactory<>(new Date());
        while (happinessDegree > 0) {
            final View view = factory.produceHappiness(sadContext);
            sadLayout.addView(view);
            newHappyPosition(view);
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
    public static RelativeLayout getSadRelativeLayout(Context sadContext) {
        RelativeLayout layout = new RelativeLayout(sadContext);
        layout.setBackgroundColor(SAD_BACKGROUND);
        return layout;
    }

    /**
     * Method sends view of happiness to the position, where are already wait its sender.
     *
     * @param view View for relocating
     */
    public static void newHappyPosition(View view) {
        final Random random = new Random();
        AppCompatActivity activity = (AppCompatActivity) view.getContext();
        Point point = new Point();
        view.getLayoutParams();
        final ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        p.setMargins(
                random.nextInt(point.y - HAPPY_VIEW_SIZE * 4),
                random.nextInt(point.y - HAPPY_VIEW_SIZE * 5),
                random.nextInt(1),
                random.nextInt(1)
        );
        view.requestLayout();
    }

    /**
     * Converting pixels into dips.
     *
     * @param context Activity context
     * @param value   Value in pixels
     * @return Value in dips
     */
    private static int pixelsInDip(Context context, int value) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, metrics);
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
                        final Context context = view.getContext();
                        HappinessFactory.editRemainingHappiness(context, -1);
                        ((ViewManager) view.getParent()).removeView(view);
                        SadActivity activity = (SadActivity) context;
                        if (activity.isSadnessDefeated()) {
                            activity.finish();
                        }
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

package by.grodno.zagart.observer.observerandroid.utils;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewManager;
import android.widget.RelativeLayout;

import java.util.Date;
import java.util.Random;

import by.grodno.zagart.observer.observerandroid.HappyEndActivity;
import by.grodno.zagart.observer.observerandroid.SadActivity;
import by.grodno.zagart.observer.observerandroid.classes.HappinessFactory;
import by.grodno.zagart.observer.observerandroid.classes.HappyButton;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK;
import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;
import static by.grodno.zagart.observer.observerandroid.classes.HappinessFactory.HAPPY_VIEW_SIZE;

/**
 * Utility class for managing mood.
 */
public class MoodUtil {

    /*
    Good solution is bind values for randomize to screen real parameters, but it is
    require time to sort out, but I haven't that time now. So I just hardcoded it.
     */
    private static final int HEIGHT_FOR_RANDOM = 1300;
    private static final int SAD_BACKGROUND = Color.rgb(0, 51, 102);
    private static final int WIDTH_FOR_RANDOM = 900;

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
    private static void newHappyPosition(View view) {
        final Random random = new Random();
        int width = WIDTH_FOR_RANDOM;
        int height = HEIGHT_FOR_RANDOM;
        final ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        p.setMargins(
                Math.abs(random.nextInt(width)) - HAPPY_VIEW_SIZE,
                Math.abs(random.nextInt(height)) - HAPPY_VIEW_SIZE,
                1,
                1
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
                            Intent intent = new Intent(activity, HappyEndActivity.class);
                            intent.addFlags(FLAG_ACTIVITY_CLEAR_TASK | FLAG_ACTIVITY_NEW_TASK);
                            activity.startActivity(intent);
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

package by.grodno.zagart.observer.observerandroid.utils;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.widget.LinearLayout;

import java.util.Date;

import by.grodno.zagart.observer.observerandroid.classes.HappinessFactory;
import by.grodno.zagart.observer.observerandroid.classes.HappyButton;

/**
 * Utility class for managing mood.
 */
public class MoodUtil {

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
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setGravity(Gravity.CENTER_HORIZONTAL);
        return layout;
    }
}

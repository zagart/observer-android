package by.grodno.zagart.observer.observerandroid.classes;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import java.util.Date;

import by.grodno.zagart.observer.observerandroid.R;
import by.grodno.zagart.observer.observerandroid.interfaces.IClock;
import by.grodno.zagart.observer.observerandroid.interfaces.IHappiness;

import static by.grodno.zagart.observer.observerandroid.utils.MoodUtil.setHappyOnClickListener;
import static by.grodno.zagart.observer.observerandroid.utils.MoodUtil.setViewSizeInDip;

/**
 * Factory that produces infinite happiness!
 */
public class HappinessFactory<Type extends TextView> implements IClock, IHappiness {

    public static final int HAPPY_VIEW_SIZE = 96;
    public static final String HAPPINESS_COUNTER = "Happiness counter";
    private Date mDate;

    public HappinessFactory(@NonNull Date date) {
        mDate = date;
    }

    /**
     * That way we know how much happiness left!
     *
     * @param activity Activity for retrieving intent
     * @return Happiness counter
     */
    public static int happinessRemaining(Activity activity) {
        return activity.getIntent().getIntExtra(HAPPINESS_COUNTER, 0);
    }

    /**
     * Method changes happiness remained and save it in views' intent.
     *
     * @param context Context to retrieve intent
     */
    public static void editRemainingHappiness(Context context, int value) {
        final Activity activity = (Activity) context;
        final Intent intent = activity.getIntent();
        intent.putExtra(HAPPINESS_COUNTER, intent.getIntExtra(HAPPINESS_COUNTER, 0) + value);
    }

    /**
     * Required to inject mock in class. The only variant for Mockito to mock
     * time that I've found. Definitely that is code smell.
     *
     * @return Current time.
     */
    @Override
    public long getTime() {
        mDate.setTime(System.currentTimeMillis());
        return mDate.getTime();
    }

    /**
     * Method that defines happiness!
     *
     * @return true if even, else odd
     */
    public boolean isHappyTime() {
        return getTime() % 2 == 0;
    }

    /**
     * It comes to make you happy! But only if its happy time :(
     *
     * @param sadContext Very sad context...
     * @return Happiness in the way!
     */
    @Override
    public Type produceHappiness(Context sadContext) {
        final TextView happyView = new TextView(sadContext);
        happyView.setText(HAPPINESS_MESSAGE);
        happyView.setGravity(Gravity.CENTER);
        happyView.setBackgroundResource(R.drawable.ic_image_wb_sunny);
        setViewSizeInDip(happyView, HAPPY_VIEW_SIZE, HAPPY_VIEW_SIZE);
        setHappyOnClickListener(happyView);
        resetVisibility(happyView);
        if (happyView.getVisibility() == View.VISIBLE) {
            editRemainingHappiness(happyView.getContext(), 1);
        }
        return (Type) happyView;
    }

    /**
     * Method resets visibility of View depending on happiness of current time.
     *
     * @param view View with updated visibility
     */
    public void resetVisibility(View view) {
        if (!isHappyTime()) {
            view.setVisibility(View.INVISIBLE);
        } else {
            view.setVisibility(View.VISIBLE);
        }
    }
}

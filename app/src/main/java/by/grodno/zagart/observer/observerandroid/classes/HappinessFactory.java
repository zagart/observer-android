package by.grodno.zagart.observer.observerandroid.classes;
import android.content.Context;
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
    private Date mDate;

    public HappinessFactory(@NonNull Date date) {
        mDate = date;
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
     * @return
     */
    public boolean isHappyTime() {
        if (getTime() % 2 == 0) {
            return true;
        }
        return false;
    }

    /**
     * Method resets visibility of View depending on happiness of current time.
     *
     * @param view
     */
    public void resetVisibility(View view) {
        if (!isHappyTime()) {
            view.setVisibility(View.INVISIBLE);
        } else {
            view.setVisibility(View.VISIBLE);
        }
    }

    /**
     * It comes to make you happy!
     *
     * @param sadContext Very sad context...
     * @return Happiness in the way!
     */
    @Override
    public Type shareHappiness(Context sadContext) {
        final TextView happyView = new TextView(sadContext);
        happyView.setText(HAPPINESS_MESSAGE);
        happyView.setBackgroundResource(R.drawable.ic_image_wb_sunny);
        happyView.setGravity(Gravity.CENTER);
        setViewSizeInDip(happyView, HAPPY_VIEW_SIZE, HAPPY_VIEW_SIZE);
        setHappyOnClickListener(happyView);
        resetVisibility(happyView);
        return (Type) happyView;
    }
}

package by.grodno.zagart.observer.observerandroid.classes;
import android.support.annotation.NonNull;
import android.view.View;

import java.util.Date;

import by.grodno.zagart.observer.observerandroid.interfaces.Clock;

/**
 * Class that can influence on View's children behavior.
 */
public class ViewController<Type extends View> implements Clock {

    private Type mView;
    private Date mDate;

    public ViewController(@NonNull Type view) {
        mView = view;
        resetVisibility();
    }

    public ViewController(@NonNull Type view, @NonNull Date date) {
        mView = view;
        mDate = date;
        resetVisibility();
    }

    @Override
    public long getTime() {
        return mDate.getTime();
    }

    public boolean isViewVisible() {
        if (getTime() % 2 == 0) {
            return true;
        }
        return false;
    }

    public void resetVisibility() {
        if (!isViewVisible()) {
            mView.setVisibility(View.INVISIBLE);
        } else {
            mView.setVisibility(View.VISIBLE);
        }
    }
}

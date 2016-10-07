package by.grodno.zagart.observer.observerandroid.classes;
import android.content.Context;
import android.widget.Button;

import java.util.Date;

import by.grodno.zagart.observer.observerandroid.interfaces.Clock;

/**
 * Button class for testing.
 */
public class HappyButton extends Button implements Clock {

    private Date mDate = new Date();

    public HappyButton(Context context) {
        super(context);
    }

    public HappyButton(Context context, Date date) {
        super(context);
        mDate = date;
    }

    public boolean isVisible() {
        if (getTime() % 2 == 0) {
            return true;
        }
        return false;
    }

    @Override
    public long getTime() {
        return mDate.getTime();
    }
}

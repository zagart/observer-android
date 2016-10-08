package by.grodno.zagart.observer.observerandroid.classes;
import android.annotation.TargetApi;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;

/**
 * Happy button. Whole class just for it. Isn't that happiness?
 */
public class HappyButton extends Button {

    public HappyButton(Context context) {
        super(context);
    }

    public HappyButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HappyButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(21)
    public HappyButton(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public CharSequence getAccessibilityClassName() {
        return super.getAccessibilityClassName();
    }
}

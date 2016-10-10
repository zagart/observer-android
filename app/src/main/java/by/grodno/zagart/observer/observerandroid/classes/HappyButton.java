package by.grodno.zagart.observer.observerandroid.classes;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatDrawableManager;
import android.support.v7.widget.TintTypedArray;
import android.util.AttributeSet;
import android.widget.Button;

import by.grodno.zagart.observer.observerandroid.R;

/**
 * Happy button. Whole class just for it. Isn't that happiness?
 */
public class HappyButton extends Button {

    public HappyButton(Context context) {
        super(context);
    }

    public HappyButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, R.attr.happyButtonStyle);
    }

    public HappyButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs, defStyleAttr);
    }

    private void init(final AttributeSet attrs, final int defStyleAttr) {
        final TintTypedArray a = TintTypedArray.obtainStyledAttributes(getContext(), attrs,
                R.styleable.HappyButtonStyle, defStyleAttr, 0);
        try {
            final AppCompatDrawableManager drawableManager = AppCompatDrawableManager.get();
            Drawable leftIcon = a.getDrawableIfKnown(R.styleable.HappyButtonStyle_leftIcon);
            int id = a.getResourceId(R.styleable.HappyButtonStyle_leftIcon, -1);
            if (id != -1) {
                leftIcon = drawableManager.getDrawable(getContext(), id);
            }
            Drawable topIcon = a.getDrawableIfKnown(R.styleable.HappyButtonStyle_topIcon);
            id = a.getResourceId(R.styleable.HappyButtonStyle_topIcon, -1);
            if (id != -1) {
                topIcon = drawableManager.getDrawable(getContext(), id);
            }
            Drawable rightIcon = a.getDrawableIfKnown(R.styleable.HappyButtonStyle_rightIcon);
            id = a.getResourceId(R.styleable.HappyButtonStyle_rightIcon, -1);
            if (id != -1) {
                rightIcon = drawableManager.getDrawable(getContext(), id);
            }
            Drawable bottomIcon = a.getDrawableIfKnown(R.styleable.HappyButtonStyle_bottomIcon);
            id = a.getResourceId(R.styleable.HappyButtonStyle_bottomIcon, -1);
            if (id != -1) {
                bottomIcon = drawableManager.getDrawable(getContext(), id);
            }
            setCompoundDrawablesWithIntrinsicBounds(leftIcon, topIcon, rightIcon, bottomIcon);
        } finally {
            a.recycle();
        }
    }
}

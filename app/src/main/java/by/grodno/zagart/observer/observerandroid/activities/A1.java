package by.grodno.zagart.observer.observerandroid.activities;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import by.grodno.zagart.observer.observerandroid.R;

/**
 * Activity #1.
 */
public class A1 extends A {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mNextActivity = A2.class;
        mMessageTextView.setText(R.string.question_1);
    }
}

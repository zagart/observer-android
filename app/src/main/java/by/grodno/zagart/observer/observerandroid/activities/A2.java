package by.grodno.zagart.observer.observerandroid.activities;
import android.os.Bundle;
import android.support.annotation.Nullable;

import by.grodno.zagart.observer.observerandroid.R;

/**
 * Activity #2.
 */
public class A2 extends A {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mNextActivity = A3.class;
        mMessageTextView.setText(R.string.question_2);
    }
}

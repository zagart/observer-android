package by.grodno.zagart.observer.observerandroid.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * Activity #1.
 */
public class A1 extends A {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        nextActivity = A2.class;
    }

}

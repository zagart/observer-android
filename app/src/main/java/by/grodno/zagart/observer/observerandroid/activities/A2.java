package by.grodno.zagart.observer.observerandroid.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * Activity #2.
 */
public class A2 extends A {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        nextActivity = A3.class;
    }

}

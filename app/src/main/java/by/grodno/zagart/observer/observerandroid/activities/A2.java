package by.grodno.zagart.observer.observerandroid.activities;

import android.os.Bundle;

public class A2 extends A {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setNextActivity(A3.class);
        setPreviousActivity(A1.class);
    }

}

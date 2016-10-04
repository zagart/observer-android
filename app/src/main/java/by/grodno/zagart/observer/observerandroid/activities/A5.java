package by.grodno.zagart.observer.observerandroid.activities;

import android.os.Bundle;

public class A5 extends A {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setNextActivity(LastActivity.class);
        setPreviousActivity(A4.class);
    }

}

package by.grodno.zagart.observer.observerandroid.activities;

import android.os.Bundle;

import by.grodno.zagart.observer.observerandroid.MainActivity;

public class A3 extends A {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setNextActivity(A4.class);
    }

}

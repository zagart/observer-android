package by.grodno.zagart.observer.observerandroid.activities;

import android.os.Bundle;

/**
 * First activity.
 */

public class A1 extends A {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setNextActivity(A2.class);
    }

    @Override
    protected void onStart() {
        super.onStart();
        addProgress("progress message.");
    }

}

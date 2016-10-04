package by.grodno.zagart.observer.observerandroid.activities;

import android.os.Bundle;

/**
 * Created by ZAGART on 04.10.2016.
 */

public class A2 extends A {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onStart() {
        super.onStart();
        addProgress("Hello :)");
    }

}

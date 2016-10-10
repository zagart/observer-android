package by.grodno.zagart.observer.observerandroid.activities;

import android.content.Intent;
import android.os.Bundle;

public class A5 extends A {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setDefaultFlag(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        setNextActivity(LastActivity.class);
    }

}

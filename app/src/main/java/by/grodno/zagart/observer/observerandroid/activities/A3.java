package by.grodno.zagart.observer.observerandroid.activities;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import by.grodno.zagart.observer.observerandroid.MainActivity;
import by.grodno.zagart.observer.observerandroid.utils.SharedPreferencesUtil;

/**
 * Activity #3.
 */
public class A3 extends A {

    @Override
    public void onClickNext(final View view) {
        SharedPreferencesUtil.persistBooleanValue(this, MainActivity.TRUSTED_USER, true);
        super.onClickNext(view);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setNextActivity(MainActivity.class);
    }

    @Override
    protected void openNextActivity(int flags) {
        super.openNextActivity(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
    }

    @Override
    public void setNextActivity(final Class nextActivity) {
        super.setNextActivity(nextActivity);
    }
}

package by.grodno.zagart.observer.observerandroid.activities;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import by.grodno.zagart.observer.observerandroid.MainActivity;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK;
import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;
import static by.grodno.zagart.observer.observerandroid.MainActivity.TRUSTED_USER;
import static by.grodno.zagart.observer.observerandroid.utils.SharedPreferencesUtil.persistBooleanValue;

/**
 * Activity #3.
 */
public class A3 extends A {

    @Override
    public void onClickNext(final View view) {
        persistBooleanValue(this, TRUSTED_USER, true);
        super.onClickNext(view);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setNextActivity(MainActivity.class);
    }

    @Override
    protected void openNextActivity(int flags) {
        super.openNextActivity(FLAG_ACTIVITY_CLEAR_TASK | FLAG_ACTIVITY_NEW_TASK);
    }

    @Override
    public void setNextActivity(final Class nextActivity) {
        super.setNextActivity(nextActivity);
    }
}

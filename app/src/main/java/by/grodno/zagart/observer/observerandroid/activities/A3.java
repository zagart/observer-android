package by.grodno.zagart.observer.observerandroid.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;

import by.grodno.zagart.observer.observerandroid.MainActivity;
import by.grodno.zagart.observer.observerandroid.ZeroActivity;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK;
import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

/**
 * Activity #3.
 */
public class A3 extends A {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        nextActivity = MainActivity.class;
    }

    @Override
    protected void openNextActivity(int flags) {
        SharedPreferences replies = getSharedPreferences(ZeroActivity.PREF_FILE, MODE_PRIVATE);
        SharedPreferences.Editor editor = replies.edit();
        editor.clear();
        editor.apply();
        activityReply = null;
        super.openNextActivity(FLAG_ACTIVITY_CLEAR_TASK | FLAG_ACTIVITY_NEW_TASK);
    }
}

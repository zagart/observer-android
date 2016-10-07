package by.grodno.zagart.observer.observerandroid.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;

import by.grodno.zagart.observer.observerandroid.MainActivity;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK;
import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;
import static by.grodno.zagart.observer.observerandroid.utils.SharedPreferencesUtil.PREF_FILE;

/**
 * Activity #3.
 */
public class A3 extends A {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mNextActivity = MainActivity.class;
    }

    @Override
    protected void openNextActivity(int flags) {
        SharedPreferences replies = getSharedPreferences(PREF_FILE, MODE_PRIVATE);
        SharedPreferences.Editor editor = replies.edit();
        editor.clear();
        editor.apply();
        mActivityReply = null;
        super.openNextActivity(FLAG_ACTIVITY_CLEAR_TASK | FLAG_ACTIVITY_NEW_TASK);
    }

}

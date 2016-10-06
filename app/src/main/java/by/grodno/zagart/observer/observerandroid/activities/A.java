package by.grodno.zagart.observer.observerandroid.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import by.grodno.zagart.observer.observerandroid.R;
import by.grodno.zagart.observer.observerandroid.ZeroActivity;

/**
 * Abstract class represents transitional activities.
 */
abstract public class A extends AppCompatActivity {

    private String activityAnswer = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences answers = getSharedPreferences(ZeroActivity.PREF_FILE, MODE_PRIVATE);
        activityAnswer = answers.getString(getClass().getSimpleName(), null);
        setContentView(R.layout.default_activity);
    }

    @Override
    protected void onStop() {
        super.onStop();
        restoreAnswer();
    }

    private void restoreAnswer() {
        SharedPreferences answers = getSharedPreferences(ZeroActivity.PREF_FILE, MODE_PRIVATE);
        SharedPreferences.Editor editor = answers.edit();
        editor.putString(getClass().getSimpleName(), activityAnswer);
        editor.commit();
    }

}

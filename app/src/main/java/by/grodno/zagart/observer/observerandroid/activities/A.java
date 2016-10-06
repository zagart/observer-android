package by.grodno.zagart.observer.observerandroid.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import by.grodno.zagart.observer.observerandroid.R;
import by.grodno.zagart.observer.observerandroid.ZeroActivity;

import static android.graphics.Color.GREEN;
import static android.graphics.Color.RED;

/**
 * Abstract class represents transitional activities.
 */
abstract public class A extends AppCompatActivity {

    public static final String YES = "agreed";
    public static final String NO = "not agreed";
    private String activityReply = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ActionBar bar = getSupportActionBar();
        if (bar != null) {
            bar.hide();
        }
        retrieveReplay();
        setContentView(R.layout.default_activity);
    }

    @Override
    protected void onStop() {
        super.onStop();
        persistReply();
    }

    private void persistReply() {
        SharedPreferences answers = getSharedPreferences(ZeroActivity.PREF_FILE, MODE_PRIVATE);
        SharedPreferences.Editor editor = answers.edit();
        editor.putString(getClass().getSimpleName(), activityReply);
        editor.apply();
    }

    private void retrieveReplay() {
        SharedPreferences replies = getSharedPreferences(ZeroActivity.PREF_FILE, MODE_PRIVATE);
        activityReply = replies.getString(getClass().getSimpleName(), null);
    }

    public void onClickYes(View view) {
        activityReply = YES;
        refreshAnswer(GREEN);
    }

    public void onClickNo(View view) {
        activityReply = NO;
        refreshAnswer(RED);
    }

    private void refreshAnswer(int color) {
        final TextView replyStatus = (TextView) findViewById(R.id.reply_status);
        replyStatus.setText(activityReply);
        replyStatus.setBackgroundColor(color);
    }

}

package by.grodno.zagart.observer.observerandroid.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import by.grodno.zagart.observer.observerandroid.R;

import static android.content.Intent.FLAG_ACTIVITY_SINGLE_TOP;
import static android.graphics.Color.GREEN;
import static android.graphics.Color.RED;
import static by.grodno.zagart.observer.observerandroid.utils.SharedPreferencesUtil.persistValue;
import static by.grodno.zagart.observer.observerandroid.utils.SharedPreferencesUtil.retrieveValue;

/**
 * Abstract class represents transitional activities.
 */
abstract public class A extends AppCompatActivity {

    public static final String YES = "agreed";
    public static final String NO = "not agreed";
    protected Class nextActivity = this.getClass();
    protected String activityReply = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ActionBar bar = getSupportActionBar();
        if (bar != null) {
            bar.hide();
        }
        setContentView(R.layout.default_activity);
    }

    @Override
    protected void onStart() {
        super.onStart();
        showTitle();
        updateReplay();
    }

    private void showTitle() {
        final TextView title = (TextView) findViewById(R.id.title_name);
        title.setText(getClass().getSimpleName());
    }

    private void updateReplay() {
        activityReply = (String) retrieveValue(this, getClass().getSimpleName(), String.class);
        if (YES.equals(activityReply)) {
            refreshReplayTextView(GREEN);
        }
        if (NO.equals(activityReply)) {
            refreshReplayTextView(RED);
        }
    }

    private void refreshReplayTextView(int color) {
        final TextView replyStatus = (TextView) findViewById(R.id.reply_status);
        replyStatus.setText(activityReply);
        replyStatus.setBackgroundColor(color);
    }

    public void onClickYes(View view) {
        activityReply = YES;
        persistValue(this, getClass().getSimpleName(), activityReply);
        refreshReplayTextView(GREEN);
    }

    public void onClickNo(View view) {
        activityReply = NO;
        persistValue(this, getClass().getSimpleName(), activityReply);
        refreshReplayTextView(RED);
    }

    public void onClickNext(View view) {
        if (activityReply != null) {
            openNextActivity(FLAG_ACTIVITY_SINGLE_TOP);
        }
    }

    protected void openNextActivity(int flags) {
        final Intent intent = new Intent(this, nextActivity);
        intent.addFlags(flags);
        startActivity(intent);
    }

}

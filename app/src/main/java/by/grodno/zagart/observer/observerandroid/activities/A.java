package by.grodno.zagart.observer.observerandroid.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import by.grodno.zagart.observer.observerandroid.R;
import by.grodno.zagart.observer.observerandroid.ZeroActivity;

import static android.content.Intent.FLAG_ACTIVITY_SINGLE_TOP;
import static android.graphics.Color.GREEN;
import static android.graphics.Color.RED;

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
        showTitle();
        retrieveReplay();
    }

    private void showTitle() {
        final TextView title = (TextView) findViewById(R.id.title_name);
        title.setText(getClass().getSimpleName());
    }

    private void retrieveReplay() {
        SharedPreferences replies = getSharedPreferences(ZeroActivity.PREF_FILE, MODE_PRIVATE);
        activityReply = replies.getString(getClass().getSimpleName(), null);
        if (YES.equals(activityReply)) {
            refreshReplay(GREEN);
        }
        if (NO.equals(activityReply)) {
            refreshReplay(RED);
        }
    }

    private void refreshReplay(int color) {
        final TextView replyStatus = (TextView) findViewById(R.id.reply_status);
        replyStatus.setText(activityReply);
        replyStatus.setBackgroundColor(color);
    }

    @Override
    protected void onStop() {
        super.onStop();
        persistReply();
    }

    private void persistReply() {
        SharedPreferences replies = getSharedPreferences(ZeroActivity.PREF_FILE, MODE_PRIVATE);
        SharedPreferences.Editor editor = replies.edit();
        editor.putString(getClass().getSimpleName(), activityReply);
        editor.apply();
    }

    public void onClickYes(View view) {
        activityReply = YES;
        refreshReplay(GREEN);
    }

    public void onClickNo(View view) {
        activityReply = NO;
        refreshReplay(RED);
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

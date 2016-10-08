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
 * Abstract class represents transitiona
 * l activities.
 */
abstract public class A extends AppCompatActivity {

    public static final String YES = "agreed";
    public static final String NO = "not agreed";
    protected String mActivityReply = null;
    protected Class mNextActivity = this.getClass();
    protected TextView mMessageTextView = null;

    public void onClickNext(View view) {
        if (mActivityReply != null) {
            openNextActivity(FLAG_ACTIVITY_SINGLE_TOP);
        }
    }

    public void onClickNo(View view) {
        mActivityReply = NO;
        persistValue(this, getClass().getSimpleName(), mActivityReply);
        refreshReplayTextView(RED);
    }

    public void onClickYes(View view) {
        mActivityReply = YES;
        persistValue(this, getClass().getSimpleName(), mActivityReply);
        refreshReplayTextView(GREEN);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ActionBar bar = getSupportActionBar();
        if (bar != null) {
            bar.hide();
        }
        setContentView(R.layout.default_activity);
        mMessageTextView = (TextView) findViewById(R.id.message_window);
    }

    @Override
    protected void onStart() {
        super.onStart();
        showTitle();
        updateReplay();
    }

    protected void openNextActivity(int flags) {
        final Intent intent = new Intent(this, mNextActivity);
        intent.addFlags(flags);
        startActivity(intent);
    }

    private void refreshReplayTextView(int color) {
        final TextView replyStatus = (TextView) findViewById(R.id.reply_status);
        replyStatus.setText(mActivityReply);
        replyStatus.setBackgroundColor(color);
    }

    private void showTitle() {
        final TextView title = (TextView) findViewById(R.id.title_name);
        title.setText(getClass().getSimpleName());
    }

    private void updateReplay() {
        mActivityReply = (String) retrieveValue(this, getClass().getSimpleName(), String.class);
        if (YES.equals(mActivityReply)) {
            refreshReplayTextView(GREEN);
        }
        if (NO.equals(mActivityReply)) {
            refreshReplayTextView(RED);
        }
    }
}

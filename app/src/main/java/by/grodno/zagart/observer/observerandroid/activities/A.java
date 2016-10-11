package by.grodno.zagart.observer.observerandroid.activities;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import by.grodno.zagart.observer.observerandroid.R;
import by.grodno.zagart.observer.observerandroid.utils.SharedPreferencesUtil;

/**
 * Abstract class represents transitional activities.
 */
abstract public class A extends AppCompatActivity {

    public static final String YES = "agreed";
    public static final String NO = "not agreed";
    protected String mActivityReply = null;
    private Class mNextActivity = this.getClass();

    public void onClickNext(View view) {
        if (mActivityReply != null) {
            openNextActivity(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        }
    }

    public void onClickNo(View view) {
        mActivityReply = NO;
        SharedPreferencesUtil.persistStringValue(this, getClass().getSimpleName(), mActivityReply);
        refreshReplayTextView(Color.RED);
    }

    public void onClickYes(View view) {
        mActivityReply = YES;
        SharedPreferencesUtil.persistStringValue(this, getClass().getSimpleName(), mActivityReply);
        refreshReplayTextView(Color.GREEN);
    }

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

    public void setNextActivity(final Class nextActivity) {
        mNextActivity = nextActivity;
    }

    private void showTitle() {
        final TextView title = (TextView) findViewById(R.id.title_name);
        title.setText(getClass().getSimpleName());
    }

    private void updateReplay() {
        mActivityReply = SharedPreferencesUtil.retrieveStringValue(this, getClass().getSimpleName());
        if (YES.equals(mActivityReply)) {
            refreshReplayTextView(Color.GREEN);
        }
        if (NO.equals(mActivityReply)) {
            refreshReplayTextView(Color.RED);
        }
    }
}

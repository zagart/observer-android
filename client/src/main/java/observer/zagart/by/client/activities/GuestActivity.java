package observer.zagart.by.client.activities;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import observer.zagart.by.client.BuildConfig;
import observer.zagart.by.client.R;
import observer.zagart.by.client.cache.helper.DbHelper;
import observer.zagart.by.client.cache.model.Module;
import observer.zagart.by.client.singletons.ContextHolder;
import observer.zagart.by.client.utils.MessengerUtil;

/**
 * Test activity.
 */
public class GuestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ActionBar bar = getSupportActionBar();
        if (bar != null) {
            bar.hide();
        }
        setContentView(R.layout.guest_activity);
        ContextHolder.set(this);
    }

    public void onCreateClick(View view) {

    }

    public void onDeleteClick(View view) {
        MessengerUtil.showMessage(R.string.msg_dummy);
    }

    public void onRecyclerViewClick(View view) {
        Intent intent = new Intent(this, ResultActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }

    public void onRetrieveClick(View view) {
        MessengerUtil.showMessage(R.string.msg_dummy);
    }

    public void onUpdateClick(View view) {
        MessengerUtil.showMessage(R.string.msg_dummy);
    }
}

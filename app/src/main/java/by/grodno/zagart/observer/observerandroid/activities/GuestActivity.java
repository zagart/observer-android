package by.grodno.zagart.observer.observerandroid.activities;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import by.grodno.zagart.observer.observerandroid.R;

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
    }

    public void onCreateClick(View view) {
    }

    public void onDeleteClick(View view) {
    }

    public void onRecyclerViewClick(View view) {
        Intent intent = new Intent(this, ResultActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }

    public void onRetrieveClick(View view) {
    }

    public void onUpdateClick(View view) {
    }
}

package by.grodno.zagart.observer.observerandroid.activities;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import by.grodno.zagart.observer.observerandroid.R;

/**
 * There is always happy end :)
 */
public class HappyEndActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ActionBar bar = getSupportActionBar();
        if (bar != null) {
            bar.hide();
        }
        setContentView(R.layout.happy_end_activity);
        showCongratulations(new View(this));
    }

    public void showCongratulations(View view) {
        Toast.makeText(this, R.string.happy_end_message, Toast.LENGTH_LONG).show();
    }
}

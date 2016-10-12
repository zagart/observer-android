package by.grodno.zagart.observer.observerandroid;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Pair;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.Toast;

import by.grodno.zagart.observer.observerandroid.apiengine.EndpointsAsyncTask;

/**
 * Application main activity.
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ActionBar bar = getSupportActionBar();
        if (bar != null) {
            bar.hide();
        }
        setContentView(R.layout.main_activity);
    }

    public void onExitClick(View view) {
        this.finish();
    }

    public void onInfoClick(View view) {
        SparseBooleanArray sparseArray = new SparseBooleanArray();
        boolean logic = false;
        for (int i = 0; i < 10; ) {
            logic = !logic;
            sparseArray.append(i++, logic);
        }
        Toast.makeText(this, sparseArray.toString(), Toast.LENGTH_LONG).show();
    }

    public void onLoginClick(View view) {
        new EndpointsAsyncTask().execute(
                new Pair<Context, String>(this, getString(R.string.main_message_to_backend))
        );
    }
}

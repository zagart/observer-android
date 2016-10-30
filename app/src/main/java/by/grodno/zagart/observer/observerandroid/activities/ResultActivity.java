package by.grodno.zagart.observer.observerandroid.activities;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import java.util.List;

import by.grodno.zagart.observer.observerandroid.R;
import by.grodno.zagart.observer.observerandroid.adapter.StandAdapter;
import by.grodno.zagart.observer.observerandroid.adapter.callbacks.ManualCallback;
import by.grodno.zagart.observer.observerandroid.cache.model.Stand;

/**
 * Activity that shows result from retrieving data.
 */
public class ResultActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result_activity);
        RecyclerView rvStands = (RecyclerView) findViewById(R.id.stand_recycler_view);
        List<Stand> stands = Stand.createStandList(20);
        final StandAdapter adapter = new StandAdapter(stands);
        ItemTouchHelper.Callback callback = new ManualCallback(adapter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(rvStands);
        rvStands.setAdapter(adapter);
        rvStands.setLayoutManager(new LinearLayoutManager(this));
    }
}

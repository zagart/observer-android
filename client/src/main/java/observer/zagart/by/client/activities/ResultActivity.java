package observer.zagart.by.client.activities;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import observer.zagart.by.client.R;
import observer.zagart.by.client.adapter.StandAdapter;
import observer.zagart.by.client.adapter.callbacks.ManualCallback;
import observer.zagart.by.client.cache.services.StandService;
import observer.zagart.by.client.singletons.ContextHolder;

/**
 * Activity that shows result from retrieving data.
 */
public class ResultActivity extends AppCompatActivity {
    private RecyclerView mRvStands;

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result_activity);
        mRvStands = (RecyclerView) findViewById(R.id.stand_recycler_view);
        processRecycler();
    }

    @Override
    protected void onStart() {
        super.onStart();
        ContextHolder.set(this);
    }

    private void processRecycler() {
        final StandAdapter adapter = new StandAdapter(StandService.selectAllStands());
        ItemTouchHelper.Callback callback = new ManualCallback(adapter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(mRvStands);
        mRvStands.setAdapter(adapter);
        mRvStands.setLayoutManager(new LinearLayoutManager(this));
    }
}

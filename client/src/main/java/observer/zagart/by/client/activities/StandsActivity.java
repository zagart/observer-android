package observer.zagart.by.client.activities;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import observer.zagart.by.client.R;
import observer.zagart.by.client.adapters.StandAdapter;
import observer.zagart.by.client.adapters.callbacks.ManualCallback;
import observer.zagart.by.client.repository.Service;

/**
 * Activity that shows result from retrieving data.
 */
public class StandsActivity extends BaseActivity {
    private RecyclerView mRvStands;

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stands_activity);
        mRvStands = (RecyclerView) findViewById(R.id.stands_recycler_view);
        processRecycler();
    }

    private void processRecycler() {
        final StandAdapter adapter = new StandAdapter(Service.selectAllStands());
        ItemTouchHelper.Callback callback = new ManualCallback(adapter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(mRvStands);
        mRvStands.setAdapter(adapter);
        mRvStands.setLayoutManager(new LinearLayoutManager(this));
    }
}

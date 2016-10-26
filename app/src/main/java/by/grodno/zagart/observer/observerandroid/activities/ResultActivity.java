package by.grodno.zagart.observer.observerandroid.activities;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
    /**
     * Simple implementation works the same way as ManualCallback
     * but require less override methods so can be written shorter.
     * Method returns that SimpleCallback implementation.
     *
     * @param pAdapter
     * @return ItemTouchHelper.SimpleCallback custom implementation.
     */
    @NonNull
    private ItemTouchHelper.SimpleCallback getSimpleCallback(final StandAdapter pAdapter) {
        return new ItemTouchHelper.SimpleCallback(
                ItemTouchHelper.UP | ItemTouchHelper.DOWN,
                ItemTouchHelper.START | ItemTouchHelper.END
        ) {
            @Override
            public boolean onMove(final RecyclerView recyclerView, final RecyclerView.ViewHolder viewHolder, final RecyclerView.ViewHolder target) {
                pAdapter.onItemLock(viewHolder.getAdapterPosition(), target.getAdapterPosition());
                return true;
            }

            @Override
            public void onSwiped(final RecyclerView.ViewHolder viewHolder, final int direction) {
                pAdapter.onItemRelease(viewHolder.getAdapterPosition());
            }
        };
    }

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result_activity);
        RecyclerView rvStands = (RecyclerView) findViewById(R.id.stand_recycler_view);
        List<Stand> stands = Stand.createStandList(20);
        final StandAdapter adapter = new StandAdapter(stands);
        ItemTouchHelper.Callback callback = new ManualCallback(adapter);
         /*
             It works the same way as ManualCallback:
             ItemTouchHelper.IActionCallback callback = getSimpleCallback(adapter);
          */
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(rvStands);
        rvStands.setAdapter(adapter);
        rvStands.setLayoutManager(new LinearLayoutManager(this));
    }
}

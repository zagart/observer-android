package observer.zagart.by.client.activities;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import java.util.List;

import observer.zagart.by.client.R;
import observer.zagart.by.client.adapter.StandAdapter;
import observer.zagart.by.client.adapter.callbacks.ManualCallback;
import observer.zagart.by.client.cache.model.Stand;
import observer.zagart.by.client.cache.services.StandService;
import observer.zagart.by.client.interfaces.IAction;
import observer.zagart.by.client.interfaces.ICallback;
import observer.zagart.by.client.threadings.ThreadWorker;

/**
 * Activity that shows result from retrieving data.
 */
public class ResultActivity extends AppCompatActivity {
    private RecyclerView mRvStands;

    private void fillRecyclerView(List<Stand> pStands) {
        final StandAdapter adapter = new StandAdapter(pStands);
        ItemTouchHelper.Callback callback = new ManualCallback(adapter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(mRvStands);
        mRvStands.setAdapter(adapter);
        mRvStands.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result_activity);
        processRecyclerFilling();
    }

    private void processRecyclerFilling() {
        mRvStands = (RecyclerView) findViewById(R.id.stand_recycler_view);
        final ThreadWorker worker = new ThreadWorker<List<Stand>>() {
            @Override
            public void onResult(final List<Stand> pStands) {
                fillRecyclerView(pStands);
            }
        };
        worker.performAction(
                new IAction<Void, Void, List<Stand>>() {
                    @Override
                    public List<Stand> process(
                            final ICallback<Void, List<Stand>> pCallback,
                            final Void... pParam) throws InterruptedException {
                        return StandService.selectAllStands();
                    }
                },
                null,
                null
        );
    }
}

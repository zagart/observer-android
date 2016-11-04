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
import by.grodno.zagart.observer.observerandroid.cache.services.StandService;
import by.grodno.zagart.observer.observerandroid.interfaces.IAction;
import by.grodno.zagart.observer.observerandroid.interfaces.ICallback;
import by.grodno.zagart.observer.observerandroid.threadings.ThreadWorker;

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
                        /*
                        Method will be executed in background thread.
                         */
                        return StandService.selectAllStands();
                    }
                },
                null,
                null
        );
    }
}

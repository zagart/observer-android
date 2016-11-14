package observer.zagart.by.client.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

import observer.zagart.by.client.App;
import observer.zagart.by.client.BuildConfig;
import observer.zagart.by.client.R;
import observer.zagart.by.client.adapters.StandTableAdapter;
import observer.zagart.by.client.adapters.callbacks.ManualCallback;
import observer.zagart.by.client.backend.api.ObserverCallback;
import observer.zagart.by.client.backend.requests.GetStandsRequest;
import observer.zagart.by.client.http.HttpClientFactory;
import observer.zagart.by.client.repository.Service;
import observer.zagart.by.client.repository.model.Stand;
import observer.zagart.by.client.threadings.ThreadWorker;

/**
 * Activity for showing cached stand objects.
 *
 * @author zagart
 */
public class StandsActivity extends BaseActivity {

    private RecyclerView mRvStands;
    private ThreadWorker mWorker;

    {
        mWorker = App.getState().getThreadWorker();
    }

    public void onClearClick(View pView) {
        Service.clearCachedStands();
        loadRecycler();
    }

    public void onReloadClick(View pView) {
        List<Stand> stands = new ArrayList<>();
        try {
            stands = ObserverCallback.onStandsReceived(
                    (String) mWorker.submit(
                            new Callable() {

                                @Override
                                public Object call() throws Exception {
                                    try {
                                        return HttpClientFactory.getDefaultClient().executeRequest(
                                                new GetStandsRequest()
                                        );
                                    } catch (IOException pEx) {
                                        if (BuildConfig.DEBUG) {
                                            Log.e(
                                                    StandsActivity.class.getSimpleName(),
                                                    pEx.getMessage(),
                                                    pEx
                                            );
                                        }
                                        return null;
                                    }
                                }
                            }
                    )
            );
        } catch (ExecutionException | InterruptedException |
                JSONException | NullPointerException pEx) {
            if (BuildConfig.DEBUG) {
                Log.e(StandsActivity.class.getSimpleName(), pEx.getMessage(), pEx);
            }
        }
        Service.synchronizeStands(stands);
        loadRecycler();
    }

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stands_activity);
        mRvStands = (RecyclerView) findViewById(R.id.stands_recycler_view);
        loadRecycler();
    }

    private void loadRecycler() {
        final StandTableAdapter adapter = new StandTableAdapter(Service.selectAllStands());
        ItemTouchHelper.Callback callback = new ManualCallback(adapter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(mRvStands);
        mRvStands.setAdapter(adapter);
        mRvStands.setLayoutManager(new LinearLayoutManager(this));
    }
}

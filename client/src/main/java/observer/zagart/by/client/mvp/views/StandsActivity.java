package observer.zagart.by.client.mvp.views;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import org.json.JSONException;

import java.util.List;
import java.util.concurrent.ExecutionException;

import observer.zagart.by.client.R;
import observer.zagart.by.client.adapters.StandTableAdapter;
import observer.zagart.by.client.repository.entities.Stand;

/**
 * Activity for showing cached stand objects.
 *
 * @author zagart
 */
public class StandsActivity extends BaseActivity {

    private RecyclerView mRecyclerViewStands;

    public void onClearClick(View pView) {
        getPresenter().clearStandModel();
    }

    public void onReloadClick(View pView)
            throws InterruptedException, ExecutionException, JSONException {
        getPresenter().downloadAllStands();
    }

    @Override
    public void onDataChanged() {
        super.onDataChanged();
        setAdapter();
    }

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stands_activity);
        mRecyclerViewStands = (RecyclerView) findViewById(R.id.stands_recycler_view);
        setAdapter();
    }

    private void setAdapter() {
        final List<Stand> stands = getPresenter().getStandsFromModel();
        final StandTableAdapter adapter = new StandTableAdapter(stands);
        mRecyclerViewStands.setAdapter(adapter);
        mRecyclerViewStands.setLayoutManager(new LinearLayoutManager(this));
    }
}

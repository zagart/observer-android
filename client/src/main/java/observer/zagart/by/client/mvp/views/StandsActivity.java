package observer.zagart.by.client.mvp.views;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import org.json.JSONException;

import java.util.concurrent.ExecutionException;

import observer.zagart.by.client.R;
import observer.zagart.by.client.adapters.StandTableAdapter;
import observer.zagart.by.client.mvp.MVP;
import observer.zagart.by.client.mvp.presenters.Presenter;

/**
 * Activity for showing cached stand objects.
 *
 * @author zagart
 */
public class StandsActivity extends BaseActivity {

    private MVP.IPresenterOperations mPresenter;
    private RecyclerView mRecyclerViewStands;

    {
        mPresenter = new Presenter(this);
    }

    public void onClearClick(View pView) {
        getPresenter().clearStandModel();
        loadRecycler();
    }

    public void onReloadClick(View pView)
            throws InterruptedException, ExecutionException, JSONException {
        mPresenter.downloadAllStands();
        loadRecycler();
    }

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stands_activity);
        mRecyclerViewStands = (RecyclerView) findViewById(R.id.stands_recycler_view);
        loadRecycler();
    }

    private void loadRecycler() {
        final StandTableAdapter adapter = new StandTableAdapter(
                getPresenter().getStandsFromModel()
        );
        mRecyclerViewStands.setAdapter(adapter);
        mRecyclerViewStands.setLayoutManager(new LinearLayoutManager(this));
    }
}

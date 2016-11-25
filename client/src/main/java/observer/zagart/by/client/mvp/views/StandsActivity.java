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
import observer.zagart.by.client.mvp.IMvp;
import observer.zagart.by.client.mvp.models.repository.entities.Stand;
import observer.zagart.by.client.mvp.presenters.StandPresenter;
import observer.zagart.by.client.mvp.presenters.base.BasePresenter;
import observer.zagart.by.client.mvp.views.adapters.StandTableAdapter;
import observer.zagart.by.client.mvp.views.base.BaseView;

/**
 * Activity for showing cached stand objects.
 *
 * @author zagart
 */
public class StandsActivity extends BaseView implements IMvp.IViewOperations<List<Stand>> {

    private StandPresenter mPresenter = new StandPresenter(this);
    private RecyclerView mRecyclerViewStands;

    public void onClearClick(View pView) {
        mPresenter.clearModel();
    }

    public void onReloadClick(View pView)
            throws InterruptedException, ExecutionException, JSONException {
        mPresenter.synchronizeModel(null);
    }

    @Override
    public void onDataChanged(final List<Stand> pStands) {
        setAdapter(pStands);
    }

    @Override
    protected void onStart() {
        super.onStart();
        super.onAccountCheck();
    }

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stands);
        mRecyclerViewStands = (RecyclerView) findViewById(R.id.stands_recycler_view);
        mPresenter.startDataReload();
    }

    @Override
    protected BasePresenter getPresenter() {
        return mPresenter;
    }

    private void setAdapter(final List<Stand> pStands) {
        if (pStands != null) {
            final StandTableAdapter adapter = new StandTableAdapter(pStands);
            mRecyclerViewStands.setAdapter(adapter);
            mRecyclerViewStands.setLayoutManager(new LinearLayoutManager(this));
        }
    }
}

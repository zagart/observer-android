package observer.zagart.by.client.mvp.views;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import observer.zagart.by.client.R;
import observer.zagart.by.client.mvp.IMvp;
import observer.zagart.by.client.mvp.models.repository.entities.Module;
import observer.zagart.by.client.mvp.presenters.ModulePresenter;
import observer.zagart.by.client.mvp.presenters.base.BasePresenter;
import observer.zagart.by.client.mvp.views.adapters.DataTableAdapter;
import observer.zagart.by.client.mvp.views.base.BaseView;

/**
 * Activity for viewing main data (measurements from modules/sensors).
 *
 * @author zagart
 */

public class DataActivity extends BaseView implements IMvp.IViewOperations<Module> {

    private ModulePresenter mPresenter = new ModulePresenter(this);
    private RecyclerView mRecyclerView;

    @Override
    public void onDataChanged(final List<Module> pModules) {
        setAdapter(pModules);
    }

    public void onClearClick(View pView) {
        mPresenter.clearModel();
        onDataChanged(new ArrayList<>());
    }

    public void onReloadClick(View pView)
            throws InterruptedException, ExecutionException, JSONException {
        mPresenter.synchronizeModel(null);
    }

    @Override
    protected void onStart() {
        super.onStart();
        super.onAccountCheck();
    }

    @Override
    protected void onCreate(@Nullable final Bundle pSavedInstanceState) {
        super.onCreate(pSavedInstanceState);
        setContentView(R.layout.activity_data);
        mRecyclerView = (RecyclerView) findViewById(R.id.data_recycler_view);
        setAdapter(new ArrayList<>());
        mPresenter.startDataReload();
    }

    @Override
    protected BasePresenter getPresenter() {
        return mPresenter;
    }

    private void setAdapter(final List<Module> pModels) {
        if (pModels != null) {
            final DataTableAdapter adapter = new DataTableAdapter(pModels);
            mRecyclerView.setAdapter(adapter);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        }
    }
}

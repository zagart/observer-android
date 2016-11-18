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
import observer.zagart.by.client.adapters.ModuleTableAdapter;
import observer.zagart.by.client.mvp.presenters.ModulePresenter;
import observer.zagart.by.client.repository.entities.Module;
import observer.zagart.by.client.utils.URIUtil;

/**
 * Activity for showing cached module objects.
 *
 * @author zagart
 */
public class ModulesActivity extends BaseActivity {

    private ModulePresenter mPresenter;
    private RecyclerView mRecyclerView;

    {
        mPresenter = new ModulePresenter(this);
    }

    public void onClearClick(View pView) {
        mPresenter.clearModel(URIUtil.getModuleUri());
    }

    public void onReloadClick(View pView)
            throws InterruptedException, ExecutionException, JSONException {
        mPresenter.synchronizeModel(URIUtil.getModuleUri(), null);
    }



    @Override
    public void onDataChanged() {
        super.onDataChanged();
        setAdapter();
    }

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.modules_activity);
        mRecyclerView = (RecyclerView) findViewById(R.id.modules_recycler_view);
        setAdapter();
    }

    private void setAdapter() {
        final List<Module> modules = mPresenter.getElementsFromModel(URIUtil.getModuleUri());
        final ModuleTableAdapter adapter = new ModuleTableAdapter(modules);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}

package observer.zagart.by.client.mvp.views;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import observer.zagart.by.client.R;
import observer.zagart.by.client.adapters.ModuleTableAdapter;
import observer.zagart.by.client.mvp.presenters.ModulePresenter;
import observer.zagart.by.client.utils.URIUtil;

/**
 * Activity for showing cached module objects.
 *
 * @author zagart
 */
public class ModulesActivity extends BaseActivity {

    private ModulePresenter mPresenter;

    {
        mPresenter = new ModulePresenter(this);
    }

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.modules_activity);
        RecyclerView mRecyclerViewModules = (RecyclerView) findViewById(R.id.modules_recycler_view);
        final ModuleTableAdapter adapter = new ModuleTableAdapter(
                mPresenter.getElementsFromModel(URIUtil.getModuleUri())
        );
        mRecyclerViewModules.setAdapter(adapter);
        mRecyclerViewModules.setLayoutManager(new LinearLayoutManager(this));
    }
}

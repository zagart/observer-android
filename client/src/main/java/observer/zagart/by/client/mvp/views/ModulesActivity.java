package observer.zagart.by.client.mvp.views;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import observer.zagart.by.client.R;
import observer.zagart.by.client.adapters.ModuleTableAdapter;

/**
 * Activity for showing cached module objects.
 *
 * @author zagart
 */
public class ModulesActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.modules_activity);
        RecyclerView mRecyclerViewModules = (RecyclerView) findViewById(R.id.modules_recycler_view);
        final ModuleTableAdapter adapter = new ModuleTableAdapter(
                getPresenter().getModulesFromModel()
        );
        mRecyclerViewModules.setAdapter(adapter);
        mRecyclerViewModules.setLayoutManager(new LinearLayoutManager(this));
    }
}

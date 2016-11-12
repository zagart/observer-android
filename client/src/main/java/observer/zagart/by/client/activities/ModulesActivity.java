package observer.zagart.by.client.activities;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import observer.zagart.by.client.R;
import observer.zagart.by.client.adapters.ModuleAdapter;
import observer.zagart.by.client.repository.Service;

/**
 * Activity that shows result from retrieving data.
 */
public class ModulesActivity extends BaseActivity {
    private RecyclerView mRecyclerViewModules;

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.modules_activity);
        mRecyclerViewModules = (RecyclerView) findViewById(R.id.modules_recycler_view);
        processRecycler();
    }

    private void processRecycler() {
        final ModuleAdapter adapter = new ModuleAdapter(Service.selectAllModules());
        mRecyclerViewModules.setAdapter(adapter);
        mRecyclerViewModules.setLayoutManager(new LinearLayoutManager(this));
    }
}

package observer.zagart.by.client.activities;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import observer.zagart.by.client.R;
import observer.zagart.by.client.adapters.ModuleTableAdapter;
import observer.zagart.by.client.repository.Service;

/**
 * @author zagart
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
        final ModuleTableAdapter adapter = new ModuleTableAdapter(Service.selectAllModules());
        mRecyclerViewModules.setAdapter(adapter);
        mRecyclerViewModules.setLayoutManager(new LinearLayoutManager(this));
    }
}

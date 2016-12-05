package observer.zagart.by.client.mvp.views.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import observer.zagart.by.client.App;
import observer.zagart.by.client.R;
import observer.zagart.by.client.application.utils.IOUtil;
import observer.zagart.by.client.mvp.models.repository.entities.Module;

/**
 * Simple adapter for modules table.
 *
 * @author zagart
 */
public class ModuleTableAdapter extends RecyclerView.Adapter<ModuleTableAdapter.RowHolder> {

    final private List<Module> mModules;

    public ModuleTableAdapter(final List<Module> pModules) {
        Set<Module> modules = new TreeSet<>(pModules);
        mModules = new ArrayList<>();
        mModules.addAll(modules);
    }

    @Override
    public RowHolder onCreateViewHolder(final ViewGroup pParent, final int pViewType) {
        final LayoutInflater inflater = LayoutInflater.from(pParent.getContext());
        final View row = inflater.inflate(R.layout.view_module_adapter_row, pParent, false);
        return new RowHolder(row);
    }

    @Override
    public void onBindViewHolder(final RowHolder pHolder, final int pPosition) {
        final Module module = mModules.get(pPosition);
        pHolder.mName.setText(module.getName());
        pHolder.mContainer.setTag(pPosition);
        pHolder.mContainer.setOnClickListener((pView) -> {
            final Module moduleUnderClick = mModules.get((Integer) pView.getTag());
            IOUtil.showToast(
                    App.getContext(),
                    String.valueOf(moduleUnderClick.getStatusChangeDate()));
        });
    }

    @Override
    public int getItemCount() {
        return mModules.size();
    }

    static class RowHolder extends RecyclerView.ViewHolder {

        final private View mContainer;
        final private TextView mName;

        private RowHolder(final View pItemView) {
            super(pItemView);
            mContainer = pItemView.findViewById(R.id.module_model_item);
            mName = (TextView) pItemView.findViewById(R.id.module_name);
        }
    }
}

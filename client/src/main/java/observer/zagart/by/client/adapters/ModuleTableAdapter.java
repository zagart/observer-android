package observer.zagart.by.client.adapters;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import observer.zagart.by.client.R;
import observer.zagart.by.client.repository.model.Module;

/**
 * Simple adapter for modules table.
 *
 * @author zagart
 */
public class ModuleTableAdapter extends RecyclerView.Adapter<ModuleTableAdapter.RowHolder> {
    private List<Module> mModules;

    public ModuleTableAdapter(final List<Module> pModules) {
        mModules = pModules;
    }

    @Override
    public RowHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        final Context context = parent.getContext();
        final LayoutInflater inflater = LayoutInflater.from(context);
        final View moduleRowView = inflater.inflate(R.layout.module_table_row, parent, false);
        return new RowHolder(moduleRowView);
    }

    @Override
    public void onBindViewHolder(final RowHolder holder, final int position) {
        final Module module = mModules.get(position);
        final TextView id = holder.mId;
        id.setText(String.valueOf(module.getId()));
        final TextView name = holder.mName;
        name.setText(module.getName());
        final TextView status = holder.mStatus;
        status.setText(module.getStatus());
        final TextView value = holder.mValue;
        value.setText(module.getValue());
        final TextView statusChangeDate = holder.mStatusChangeDate;
        statusChangeDate.setText(module.getStatusChangeDate().toString());
        final TextView standId = holder.mStandId;
        standId.setText(String.valueOf(module.getStandId()));
    }

    @Override
    public int getItemCount() {
        return mModules.size();
    }

    public static class RowHolder extends RecyclerView.ViewHolder {
        private TextView mId;
        private TextView mName;
        private TextView mStatus;
        private TextView mValue;
        private TextView mStatusChangeDate;
        private TextView mStandId;

        private RowHolder(final View pItemView) {
            super(pItemView);
            mId = (TextView) pItemView.findViewById(R.id.module_id);
            mName = (TextView) pItemView.findViewById(R.id.module_name);
            mStatus = (TextView) pItemView.findViewById(R.id.module_status);
            mValue = (TextView) pItemView.findViewById(R.id.module_value);
            mStatusChangeDate = (TextView) pItemView.findViewById(R.id.module_status_change_date);
            mStandId = (TextView) pItemView.findViewById(R.id.module_stand_id);
        }
    }
}

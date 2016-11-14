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
    public RowHolder onCreateViewHolder(final ViewGroup pParent, final int pViewType) {
        final Context context = pParent.getContext();
        final LayoutInflater inflater = LayoutInflater.from(context);
        final View moduleRowView = inflater.inflate(R.layout.module_table_row, pParent, false);
        return new RowHolder(moduleRowView);
    }

    @Override
    public void onBindViewHolder(final RowHolder pHolder, final int pPosition) {
        final Module module = mModules.get(pPosition);
        final TextView id = pHolder.mId;
        id.setText(String.valueOf(module.getId()));
        final TextView name = pHolder.mName;
        name.setText(module.getName());
        final TextView status = pHolder.mStatus;
        status.setText(module.getStatus());
        final TextView value = pHolder.mValue;
        value.setText(module.getValue());
        final TextView statusChangeDate = pHolder.mStatusChangeDate;
        statusChangeDate.setText(module.getStatusChangeDate().toString());
        final TextView standId = pHolder.mStandId;
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

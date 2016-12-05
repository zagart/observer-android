package observer.zagart.by.client.mvp.views.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import observer.zagart.by.client.App;
import observer.zagart.by.client.R;
import observer.zagart.by.client.application.utils.IOUtil;
import observer.zagart.by.client.mvp.models.repository.entities.Module;

/**
 * Adapter for data table.
 *
 * @author zagart
 */

public class DataTableAdapter extends RecyclerView.Adapter<DataTableAdapter.RowHolder> {

    final private List<Module> mRows;

    public DataTableAdapter(final List<Module> pRows) {
        mRows = pRows;
    }

    @Override
    public RowHolder onCreateViewHolder(final ViewGroup pParent, final int pViewType) {
        final LayoutInflater inflater = LayoutInflater.from(pParent.getContext());
        final View row = inflater.inflate(R.layout.view_data_adapter_row, pParent, false);
        return new RowHolder(row);
    }

    @Override
    public void onBindViewHolder(final RowHolder pHolder, final int pPosition) {
        final Module row = mRows.get(pPosition);
        pHolder.mStatus.setText(row.getStatus());
        pHolder.mValue.setText(row.getValue());
        pHolder.mContainer.setTag(pPosition);
        pHolder.mContainer.setOnClickListener(pView -> {
            //TODO universal info window
            StringBuilder info = new StringBuilder();
            final Module module = mRows.get((Integer) pView.getTag());
            info
                    .append("Module name: ").append(module.getName()).append("\n")
                    .append("Status: ").append(module.getStatus()).append("\n")
                    .append("Value: ").append(module.getValue()).append("\n")
                    .append("Date: ").append(module.getStatusChangeDate());
            IOUtil.showToast(App.getContext(), info.toString());
        });
    }

    @Override
    public int getItemCount() {
        return mRows.size();
    }

    static class RowHolder extends RecyclerView.ViewHolder {

        final private View mContainer;

        final private TextView mStatus;
        final private TextView mValue;

        RowHolder(final View pItemView) {
            super(pItemView);
            mContainer = pItemView.findViewById(R.id.data_model_item);
            mStatus = (TextView) pItemView.findViewById(R.id.data_status);
            mValue = (TextView) pItemView.findViewById(R.id.data_value);
        }
    }
}

package observer.zagart.by.client.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import observer.zagart.by.client.R;
import observer.zagart.by.client.repository.entities.Stand;

/**
 * My adapter for stand model.
 */
public class StandTableAdapter extends RecyclerView.Adapter<StandTableAdapter.RowHolder> {

    final private List<Stand> mStands;

    public StandTableAdapter(final List<Stand> pStands) {
        mStands = pStands;
    }

    @Override
    public StandTableAdapter.RowHolder onCreateViewHolder(
            final ViewGroup pParent,
            final int pViewType) {
        final Context context = pParent.getContext();
        final LayoutInflater inflater = LayoutInflater.from(context);
        final View standRowView = inflater.inflate(R.layout.stand_table_row, pParent, false);
        return new RowHolder(standRowView);
    }

    @Override
    public void onBindViewHolder(final StandTableAdapter.RowHolder pHolder, final int pPosition) {
        final Stand stand = mStands.get(pPosition);
        pHolder.mIdView.setText(String.valueOf(stand.getId()));
        pHolder.mNumberView.setText(stand.getNumber());
        pHolder.mDescriptionView.setText(stand.getDescription());
    }

    @Override
    public int getItemCount() {
        return mStands.size();
    }

    static class RowHolder extends RecyclerView.ViewHolder {

        final private TextView mIdView;
        final private TextView mNumberView;
        final private TextView mDescriptionView;

        private RowHolder(final View pItemView) {
            super(pItemView);
            mIdView = (TextView) pItemView.findViewById(R.id.stand_id);
            mNumberView = (TextView) pItemView.findViewById(R.id.stand_number);
            mDescriptionView = (TextView) pItemView.findViewById(R.id.stand_description);
        }
    }
}

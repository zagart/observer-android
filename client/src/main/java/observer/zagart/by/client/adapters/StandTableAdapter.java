package observer.zagart.by.client.adapters;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

import observer.zagart.by.client.R;
import observer.zagart.by.client.adapters.callbacks.IMovableContent;
import observer.zagart.by.client.repository.model.Stand;

/**
 * My adapter for stand model.
 */
public class StandTableAdapter extends RecyclerView.Adapter<StandTableAdapter.RowHolder>
        implements IMovableContent {
    private List<Stand> mStands;

    public StandTableAdapter(final List<Stand> pStands) {
        mStands = pStands;
    }

    @Override
    public StandTableAdapter.RowHolder onCreateViewHolder(
            final ViewGroup pParent,
            final int pViewType
    ) {
        Context context = pParent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View standRowView = inflater.inflate(R.layout.stand_table_row, pParent, false);
        return new RowHolder(standRowView);
    }

    @Override
    public void onBindViewHolder(final StandTableAdapter.RowHolder pHolder, final int pPosition) {
        Stand stand = mStands.get(pPosition);
        TextView idView = pHolder.mIdView;
        idView.setText(String.valueOf(stand.getId()));
        TextView numberView = pHolder.mNumberView;
        numberView.setText(stand.getNumber());
        TextView descriptionView = pHolder.mDescriptionView;
        descriptionView.setText(stand.getDescription());
    }

    @Override
    public int getItemCount() {
        return mStands.size();
    }

    @Override
    public void onItemLock(final int pStartPosition, final int pEndPosition) {
        if (pStartPosition < pEndPosition) {
            for (int i = pStartPosition; i < pEndPosition; i++) {
                Collections.swap(mStands, i, i + 1);
            }
        } else {
            for (int i = pStartPosition; i > pEndPosition; i--) {
                Collections.swap(mStands, i, i - 1);
            }
        }
        notifyItemMoved(pStartPosition, pEndPosition);
    }

    @Override
    public void onItemRelease(final int pPosition) {
        mStands.remove(pPosition);
        notifyItemRemoved(pPosition);
    }

    public static class RowHolder extends RecyclerView.ViewHolder {
        private TextView mIdView;
        private TextView mNumberView;
        private TextView mDescriptionView;

        private RowHolder(final View pItemView) {
            super(pItemView);
            mIdView = (TextView) pItemView.findViewById(R.id.stand_id);
            mNumberView = (TextView) pItemView.findViewById(R.id.stand_number);
            mDescriptionView = (TextView) pItemView.findViewById(R.id.stand_description);
        }
    }
}

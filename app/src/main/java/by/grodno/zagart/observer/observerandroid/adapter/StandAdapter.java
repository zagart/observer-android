package by.grodno.zagart.observer.observerandroid.adapter;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

import by.grodno.zagart.observer.observerandroid.R;
import by.grodno.zagart.observer.observerandroid.adapter.callbacks.IMovableContent;
import by.grodno.zagart.observer.observerandroid.cache.model.Stand;

/**
 * My adapter for stand model.
 */
public class StandAdapter extends RecyclerView.Adapter<StandAdapter.RowHolder> implements IMovableContent {
    private List<Stand> mStands;

    public StandAdapter(final List<Stand> pStands) {
        mStands = pStands;
    }

    @Override
    public StandAdapter.RowHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View standView = inflater.inflate(R.layout.stand_table_row, parent, false);
        return new RowHolder(standView);
    }

    @Override
    public void onBindViewHolder(final StandAdapter.RowHolder holder, final int position) {
        Stand stand = mStands.get(position);
        TextView idView = holder.mIdView;
        idView.setText(String.valueOf(stand.getId()));
        TextView numberView = holder.mNumberView;
        numberView.setText(stand.getNumber());
        TextView descriptionView = holder.mDescriptionView;
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

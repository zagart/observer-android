package by.grodno.zagart.observer.observerandroid.adapter;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import by.grodno.zagart.observer.observerandroid.R;
import by.grodno.zagart.observer.observerandroid.cache.model.Stand;

/**
 * My adapter for stand.
 */
public class StandAdapter extends RecyclerView.Adapter<StandAdapter.RowHolder> {
    private List<Stand> mStands;
    private Context mContext;

    public StandAdapter(final List<Stand> pStands, final Context pContext) {
        mStands = pStands;
        mContext = pContext;
    }

    @Override
    public StandAdapter.RowHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View standView = inflater.inflate(R.layout.stand_table_row, parent, false);
        RowHolder holder = new RowHolder(standView);
        return holder;
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

    public static class RowHolder extends RecyclerView.ViewHolder {
        public TextView mIdView;
        public TextView mNumberView;
        public TextView mDescriptionView;

        public RowHolder(final View pItemView) {
            super(pItemView);
            mIdView = (TextView) pItemView.findViewById(R.id.stand_id);
            mNumberView = (TextView) pItemView.findViewById(R.id.stand_number);
            mDescriptionView = (TextView) pItemView.findViewById(R.id.stand_description);
        }
    }
}

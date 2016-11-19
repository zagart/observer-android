package observer.zagart.by.client.mvp.views.adapters.callbacks;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

/**
 * Manual implementation of ItemTouchHelper.ICallback.
 */
//TODO remove if will be not required to release date
@SuppressWarnings("unused")
public class ManualCallback extends ItemTouchHelper.Callback {

    private final IMovableContent mContent;

    public ManualCallback(final IMovableContent pContent) {
        mContent = pContent;
    }

    @Override
    public int getMovementFlags(
            final RecyclerView pRecyclerView,
            final RecyclerView.ViewHolder pViewHolder) {
        int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
        int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
        return makeMovementFlags(dragFlags, swipeFlags);
    }

    @Override
    public boolean onMove(
            final RecyclerView pRecyclerView,
            final RecyclerView.ViewHolder pViewHolder,
            final RecyclerView.ViewHolder pTarget) {
        mContent.onItemLock(pViewHolder.getAdapterPosition(), pTarget.getAdapterPosition());
        return true;
    }

    @Override
    public void onSwiped(final RecyclerView.ViewHolder pViewHolder, final int pDirection) {
        mContent.onItemRelease(pViewHolder.getAdapterPosition());
    }
}

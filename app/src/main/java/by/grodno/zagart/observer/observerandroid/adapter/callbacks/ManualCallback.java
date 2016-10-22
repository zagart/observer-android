package by.grodno.zagart.observer.observerandroid.adapter.callbacks;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

/**
 * Manual implementation of ItemTouchHelper.Callback.
 */
public class ManualCallback extends ItemTouchHelper.Callback {
    private final IMovableContent mContent;

    public ManualCallback(final IMovableContent pContent) {
        mContent = pContent;
    }

    @Override
    public int getMovementFlags(final RecyclerView recyclerView, final RecyclerView.ViewHolder viewHolder) {
        int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
        int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
        return makeMovementFlags(dragFlags, swipeFlags);
    }

    @Override
    public boolean onMove(
            final RecyclerView recyclerView,
            final RecyclerView.ViewHolder viewHolder,
            final RecyclerView.ViewHolder target
    ) {
        mContent.onItemLock(viewHolder.getAdapterPosition(), target.getAdapterPosition());
        return true;
    }

    @Override
    public void onSwiped(final RecyclerView.ViewHolder viewHolder, final int direction) {
        mContent.onItemRelease(viewHolder.getAdapterPosition());
    }
}

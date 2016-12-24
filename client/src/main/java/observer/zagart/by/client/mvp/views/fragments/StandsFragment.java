package observer.zagart.by.client.mvp.views.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import observer.zagart.by.client.R;
import observer.zagart.by.client.mvp.IMvp;
import observer.zagart.by.client.mvp.models.repository.entities.Stand;
import observer.zagart.by.client.mvp.presenters.StandPresenter;
import observer.zagart.by.client.mvp.presenters.base.BasePresenter;
import observer.zagart.by.client.mvp.views.adapters.StandTableAdapter;
import observer.zagart.by.client.mvp.views.base.BaseFragmentView;

/**
 * Fragment for showing cached stand objects.
 *
 * @author zagart
 */
public class StandsFragment extends BaseFragmentView implements IMvp.IViewOperations<Stand> {

    private StandPresenter mPresenter = new StandPresenter(this);
    private RecyclerView mRecyclerViewStands;

    @Override
    public void onDataChanged(final List<Stand> pStands) {
        setAdapter(pStands);
    }

    @Override
    public void onActivityCreated(final Bundle pSavedInstanceState) {
        super.onActivityCreated(pSavedInstanceState);
        mRecyclerViewStands = (RecyclerView) getActivity().findViewById(R.id.stands_recycler_view);
        setAdapter(new ArrayList<>());
        mPresenter.startDataReload();
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater pInflater,
                             final ViewGroup pContainer,
                             final Bundle pSavedInstanceState) {
        super.onCreateView(pInflater, pContainer, pSavedInstanceState);
        return getLayoutWithPanel(
                pInflater,
                pContainer,
                R.layout.fragment_stands,
                pView -> {
                    mPresenter.clearModel();
                    onDataChanged(new ArrayList<>());
                },
                pView -> mPresenter.synchronizeModel(null)
        );
    }

    @Override
    protected BasePresenter getPresenter() {
        return mPresenter;
    }

    @Override
    protected int getProgressBarResId() {
        return R.id.progress_bar_stands;
    }

    private void setAdapter(final List<Stand> pStands) {
        final View view = getView();
        if (pStands != null && view != null) {
            final StandTableAdapter adapter = new StandTableAdapter(pStands);
            mRecyclerViewStands.setAdapter(adapter);
            mRecyclerViewStands.setLayoutManager(new LinearLayoutManager(view.getContext()));
        }
    }
}

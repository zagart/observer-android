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
import observer.zagart.by.client.mvp.models.repository.entities.Module;
import observer.zagart.by.client.mvp.presenters.ModulePresenter;
import observer.zagart.by.client.mvp.presenters.base.BasePresenter;
import observer.zagart.by.client.mvp.views.adapters.DataTableAdapter;
import observer.zagart.by.client.mvp.views.base.BaseFragmentView;

/**
 * Fragment for viewing main data (measurements from modules/sensors).
 *
 * @author zagart
 */

public class DataFragment extends BaseFragmentView implements IMvp.IViewOperations<Module> {

    private ModulePresenter mPresenter = new ModulePresenter(this);
    private RecyclerView mRecyclerView;

    @Override
    public void onDataChanged(final List<Module> pModules) {
        setAdapter(mRecyclerView, pModules);
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
                R.layout.fragment_data,
                (pView -> {
                    mPresenter.clearModel();
                    onDataChanged(new ArrayList<>());
                }),
                (pView) -> mPresenter.synchronizeModel(null));
    }

    @Override
    public void onActivityCreated(final Bundle pSavedInstanceState) {
        super.onActivityCreated(pSavedInstanceState);
        mRecyclerView = (RecyclerView) getActivity().findViewById(R.id.data_recycler_view);
        setAdapter(mRecyclerView, new ArrayList<>());
        mPresenter.startDataReload();
    }

    @Override
    protected int getProgressBarResId() {
        return R.id.progress_bar_data;
    }

    @Override
    protected BasePresenter getPresenter() {
        return mPresenter;
    }

    private void setAdapter(final RecyclerView pRecyclerView, final List<Module> pModels) {
        final View view = getView();
        if (pModels != null && view != null && pRecyclerView != null) {
            final DataTableAdapter adapter = new DataTableAdapter(pModels);
            pRecyclerView.setAdapter(adapter);
            pRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        }
    }
}

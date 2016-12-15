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
import observer.zagart.by.client.mvp.views.adapters.ModuleTableAdapter;
import observer.zagart.by.client.mvp.views.base.BaseView;

/**
 * Fragment for showing cached module objects.
 *
 * @author zagart
 */

public class ModulesFragment extends BaseView implements IMvp.IViewOperations<Module> {

    private ModulePresenter mPresenter = new ModulePresenter(this);
    private RecyclerView mRecyclerView;

    @Override
    public void onDataChanged(final List<Module> pModules) {
        setAdapter(pModules);
    }

    @Override
    public void onStart() {
        super.onStart();
        super.onAccountCheck();
        getActivity().setTitle(R.string.modules);
    }

    @Override
    public void onActivityCreated(final Bundle pSavedInstanceState) {
        super.onActivityCreated(pSavedInstanceState);
        mRecyclerView = (RecyclerView) getActivity().findViewById(R.id.modules_recycler_view);
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
                R.layout.activity_modules,
                (pView -> {
                    mPresenter.clearModel();
                    onDataChanged(new ArrayList<>());
                }),
                (pView) -> mPresenter.synchronizeModel(null)
        );
    }

    @Override
    protected BasePresenter<Module> getPresenter() {
        return mPresenter;
    }

    private void setAdapter(final List<Module> pModules) {
        final View view = getView();
        if (pModules != null && view != null) {
            final ModuleTableAdapter adapter = new ModuleTableAdapter(pModules);
            mRecyclerView.setAdapter(adapter);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        }
    }
}

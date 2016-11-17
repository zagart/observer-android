package observer.zagart.by.client.mvp.views;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import observer.zagart.by.client.mvp.MVP;
import observer.zagart.by.client.mvp.presenters.Presenter;

/**
 * Base activity of application.
 *
 * @author zagart
 */
abstract public class BaseActivity extends AppCompatActivity implements MVP.IViewOperations {

    private MVP.IPresenterOperations mPresenter;

    {
        mPresenter = new Presenter(this);
    }

    @Override
    public Context getViewContext() {
        return this;
    }

    @Override
    public void onViewsVisibilityCheck() {
        //activity specific buttons visibility check
    }

    @Override
    public void onDataChanged() {
        //activity specific content changed actions
    }

    @Override
    protected void onStart() {
        super.onStart();
        onViewsVisibilityCheck();
    }

    protected MVP.IPresenterOperations getPresenter() {
        return mPresenter;
    }
}

package observer.zagart.by.client.mvp.views;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import observer.zagart.by.client.mvp.IMvp;

/**
 * Base activity of application.
 *
 * @author zagart
 */
public class BaseActivity extends AppCompatActivity implements IMvp.IViewOperations {

    @Override
    public Context getViewContext() {
        return this;
    }

    //TODO
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
}

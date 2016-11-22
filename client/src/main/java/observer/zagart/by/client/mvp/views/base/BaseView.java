package observer.zagart.by.client.mvp.views.base;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import observer.zagart.by.client.App;
import observer.zagart.by.client.mvp.IMvp;
import observer.zagart.by.client.mvp.presenters.base.BasePresenter;
import observer.zagart.by.client.mvp.views.MainActivity;

/**
 * Base activity of application.
 *
 * @author zagart
 */
abstract public class BaseView extends AppCompatActivity implements IMvp.IViewOperations {

    public void onAccountCheck() {
        if (App.getAccount() == null) {
            final Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }

    @Override
    public Context getViewContext() {
        return this;
    }

    abstract protected BasePresenter getPresenter();

    @Override
    protected void onResume() {
        super.onResume();
        final BasePresenter presenter = getPresenter();
        if (presenter != null) {
            presenter.registerObserver();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        final BasePresenter presenter = getPresenter();
        if (presenter != null) {
            getPresenter().unregisterObserver();
        }
    }
}

package observer.zagart.by.client.mvp.views.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import observer.zagart.by.client.R;

/**
 * Base account activity.
 *
 * @author zagart
 */

public class BaseAccountActivity extends AppCompatActivity {

    private View mCoordinatorLayout;

    private AlertDialog mDialog;

    public void showProgressDialog() {
        mDialog.show();
    }

    public void dismissProgressDialog() {
        mDialog.dismiss();
    }

    public void showSnackBar(final int pResID) {
        if (mCoordinatorLayout instanceof CoordinatorLayout) {
            final Snackbar snackBar = Snackbar.make(
                    mCoordinatorLayout,
                    pResID,
                    Snackbar.LENGTH_INDEFINITE);
            snackBar.show();
        }
    }

    @Override
    protected void onCreate(@Nullable final Bundle pSavedInstanceState) {
        super.onCreate(pSavedInstanceState);
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(R.layout.view_alert_dialog);
        builder.setCancelable(false);
        mDialog = builder.create();
    }

    protected void findCoordinatorLayout() {
        mCoordinatorLayout = findViewById(R.id.activity_login);
    }
}

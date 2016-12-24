package observer.zagart.by.client.mvp.views.base;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;

/**
 * Base account activity.
 *
 * @author zagart
 */

public class BaseAccountActivity extends AppCompatActivity {

    private ProgressDialog mProgressDialog;

    public void showProgressDialog(final int pResID) {
        final ColorDrawable drawable = new ColorDrawable(Color.BLACK);
        mProgressDialog = new ProgressDialog(this);
        final Window window = mProgressDialog.getWindow();
        if (window != null) {
            window.setBackgroundDrawable(drawable);
            mProgressDialog.setTitle(getString(pResID));
            mProgressDialog.show();
        }
    }

    public void dismissProgressDialog() {
        mProgressDialog.dismiss();
    }
}

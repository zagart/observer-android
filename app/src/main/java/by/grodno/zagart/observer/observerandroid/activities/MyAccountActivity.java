package by.grodno.zagart.observer.observerandroid.activities;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import by.grodno.zagart.observer.observerandroid.R;
import by.grodno.zagart.observer.observerandroid.imageloader.BitmapDrawer;

/**
 * Activity for settings of user account.
 *
 * @author zagart
 */
public class MyAccountActivity extends AppCompatActivity {
    public static final String TEMP_IMG =
            "http://makeitlast.se/wp-content/uploads/2015/10/loppis_12.jpg";
    private ImageView mAvatar;

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ActionBar bar = getSupportActionBar();
        if (bar != null) {
            bar.hide();
        }
        setContentView(R.layout.my_account_activity);
        mAvatar = (ImageView) findViewById(R.id.my_account_avatar);
    }

    public void onLoadImageClick(View pView) {
        BitmapDrawer drawer = new BitmapDrawer();
        drawer.draw(mAvatar, TEMP_IMG);
    }
}

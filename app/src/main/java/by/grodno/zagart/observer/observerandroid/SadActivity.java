package by.grodno.zagart.observer.observerandroid;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;

import static by.grodno.zagart.observer.observerandroid.utils.MoodUtil.addHappinessToSadLayout;
import static by.grodno.zagart.observer.observerandroid.utils.MoodUtil.getSadLinearLayout;

/**
 * Sad activity. Born to be sad.
 */
public class SadActivity extends AppCompatActivity {

    public static final int HAPPINESS_DEGREE = 10;
    public static final String TITLE = "So sad...";
    private LinearLayout mSadRootLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setTitle(TITLE);
        mSadRootLayout = getSadLinearLayout(this);
        setContentView(mSadRootLayout);
    }

    @Override
    protected void onStart() {
        super.onStart();
        addHappinessToSadLayout(mSadRootLayout, HAPPINESS_DEGREE, this);
        setContentView(mSadRootLayout);
    }
}

package by.grodno.zagart.observer.observerandroid;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.RelativeLayout;

import by.grodno.zagart.observer.observerandroid.classes.HappinessFactory;

import static by.grodno.zagart.observer.observerandroid.utils.MoodUtil.addHappinessToSadLayout;
import static by.grodno.zagart.observer.observerandroid.utils.MoodUtil.getSadRelativeLayout;

/**
 * Sad activity. Born to be sad.
 */
public class SadActivity extends AppCompatActivity {

    public static final int HAPPINESS_DEGREE = 20;
    public static final String TITLE = "So sad...";
    private RelativeLayout mSadRootLayout;

    public boolean isSadnessDefeated() {
        return !(HappinessFactory.happinessRemaining(this) > 0);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setTitle(TITLE);
        mSadRootLayout = getSadRelativeLayout(this);
        addHappinessToSadLayout(mSadRootLayout, HAPPINESS_DEGREE, this);
        setContentView(mSadRootLayout);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
    }
}

package by.grodno.zagart.observer.observerandroid;
import android.content.Intent;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.assertEquals;
import static org.robolectric.Shadows.shadowOf;

/**
 * Robolectric wasn't tested, because it is do not support API 24.
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 24)
public class RobotiumMainActivityTest {

    @Test
    public void clickingHelp_shouldRunSadActivity() {
        MainActivity activity = Robolectric.setupActivity(MainActivity.class);
        activity.findViewById(R.id.btn_help).performClick();
        Intent intent = new Intent(activity, SadActivity.class);
        assertEquals(shadowOf(activity).getNextStartedActivity(), intent);
    }

    @Test
    public void clickingRunAway_shouldFinishActivity() {
        MainActivity activity = Robolectric.setupActivity(MainActivity.class);
        activity.findViewById(R.id.btn_exit).performClick();
        Intent intent = new Intent(activity, null);
        assertEquals(shadowOf(activity).getNextStartedActivity(), intent);
    }
}

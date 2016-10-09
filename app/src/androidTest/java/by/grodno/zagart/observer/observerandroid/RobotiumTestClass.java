package by.grodno.zagart.observer.observerandroid;
import android.test.ActivityInstrumentationTestCase2;

import com.robotium.solo.Solo;
import com.robotium.solo.Timeout;

public class RobotiumTestClass extends ActivityInstrumentationTestCase2<ZeroActivity> {

    private Solo solo;

    public RobotiumTestClass() {
        super(ZeroActivity.class);
    }

    public void setUp() throws Exception {
        super.setUp();
        solo = new Solo(getInstrumentation());
        getActivity();
    }

    @Override
    public void tearDown() throws Exception {
        solo.finishOpenedActivities();
        super.tearDown();
    }

    public void testRun() {
        solo.waitForActivity(by.grodno.zagart.observer.observerandroid.ZeroActivity.class, 2000);
        assertTrue(
                "by.grodno.zagart.observer.observerandroid.activities.A1 is not found!",
                solo.waitForActivity(by.grodno.zagart.observer.observerandroid.activities.A1.class)
        );
        Timeout.setSmallTimeout(58858);
        solo.clickOnView(solo.getView(by.grodno.zagart.observer.observerandroid.R.id.btn_no));
        solo.clickOnView(solo.getView(by.grodno.zagart.observer.observerandroid.R.id.next));
        assertTrue(
                "by.grodno.zagart.observer.observerandroid.activities.A2 is not found!",
                solo.waitForActivity(by.grodno.zagart.observer.observerandroid.activities.A2.class)
        );
        solo.clickOnView(solo.getView(by.grodno.zagart.observer.observerandroid.R.id.btn_yes));
        solo.clickOnView(solo.getView(by.grodno.zagart.observer.observerandroid.R.id.next));
        assertTrue(
                "by.grodno.zagart.observer.observerandroid.activities.A3 is not found!",
                solo.waitForActivity(by.grodno.zagart.observer.observerandroid.activities.A3.class)
        );
        assertTrue(
                "by.grodno.zagart.observer.observerandroid.activities.A2 is not found!",
                solo.waitForActivity(by.grodno.zagart.observer.observerandroid.activities.A2.class)
        );
        solo.clickOnView(solo.getView(by.grodno.zagart.observer.observerandroid.R.id.btn_yes));
        solo.clickOnView(solo.getView(by.grodno.zagart.observer.observerandroid.R.id.next));
        assertTrue(
                "by.grodno.zagart.observer.observerandroid.MainActivity is not found!",
                solo.waitForActivity(by.grodno.zagart.observer.observerandroid.MainActivity.class)
        );
        solo.clickOnView(solo.getView(by.grodno.zagart.observer.observerandroid.R.id.btn_exit));
    }
}

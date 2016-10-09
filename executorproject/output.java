package by.grodno.zagart.observer.observerandroid.test;

import by.grodno.zagart.observer.observerandroid.ZeroActivity;
import com.robotium.solo.*;
import android.test.ActivityInstrumentationTestCase2;


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
        //Wait for activity: 'by.grodno.zagart.observer.observerandroid.ZeroActivity'
		solo.waitForActivity(by.grodno.zagart.observer.observerandroid.ZeroActivity.class, 2000);
        //Wait for activity: 'by.grodno.zagart.observer.observerandroid.activities.A1'
		assertTrue("by.grodno.zagart.observer.observerandroid.activities.A1 is not found!", solo.waitForActivity(by.grodno.zagart.observer.observerandroid.activities.A1.class));
        //Set default small timeout to 58858 milliseconds
		Timeout.setSmallTimeout(58858);
        //Click on No
		solo.clickOnView(solo.getView(by.grodno.zagart.observer.observerandroid.R.id.btn_no));
        //Click on Next
		solo.clickOnView(solo.getView(by.grodno.zagart.observer.observerandroid.R.id.next));
        //Wait for activity: 'by.grodno.zagart.observer.observerandroid.activities.A2'
		assertTrue("by.grodno.zagart.observer.observerandroid.activities.A2 is not found!", solo.waitForActivity(by.grodno.zagart.observer.observerandroid.activities.A2.class));
        //Click on Yes
		solo.clickOnView(solo.getView(by.grodno.zagart.observer.observerandroid.R.id.btn_yes));
        //Click on Next
		solo.clickOnView(solo.getView(by.grodno.zagart.observer.observerandroid.R.id.next));
        //Wait for activity: 'by.grodno.zagart.observer.observerandroid.activities.A3'
		assertTrue("by.grodno.zagart.observer.observerandroid.activities.A3 is not found!", solo.waitForActivity(by.grodno.zagart.observer.observerandroid.activities.A3.class));
        //Wait for activity: 'by.grodno.zagart.observer.observerandroid.activities.A2'
		assertTrue("by.grodno.zagart.observer.observerandroid.activities.A2 is not found!", solo.waitForActivity(by.grodno.zagart.observer.observerandroid.activities.A2.class));
        //Click on Yes
		solo.clickOnView(solo.getView(by.grodno.zagart.observer.observerandroid.R.id.btn_yes));
        //Click on Next
		solo.clickOnView(solo.getView(by.grodno.zagart.observer.observerandroid.R.id.next));
        //Wait for activity: 'by.grodno.zagart.observer.observerandroid.MainActivity'
		assertTrue("by.grodno.zagart.observer.observerandroid.MainActivity is not found!", solo.waitForActivity(by.grodno.zagart.observer.observerandroid.MainActivity.class));
        //Click on Run away
		solo.clickOnView(solo.getView(by.grodno.zagart.observer.observerandroid.R.id.btn_exit));
	}
}

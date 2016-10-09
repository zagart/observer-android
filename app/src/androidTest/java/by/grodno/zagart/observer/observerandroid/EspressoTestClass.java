package by.grodno.zagart.observer.observerandroid;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import by.grodno.zagart.observer.observerandroid.utils.SharedPreferencesUtil;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@RunWith(AndroidJUnit4.class)
public class EspressoTestClass {

    @Rule
    public ActivityTestRule<ZeroActivity> mActivityTestRule = new ActivityTestRule<ZeroActivity>(ZeroActivity.class) {
        /**
         * Clean up Shared Preferences before activity launched. If do not do this,
         * activities that are required for test may be ignored.
         */
        @Override
        protected void beforeActivityLaunched() {
            super.beforeActivityLaunched();
            final SharedPreferences sharedPreferences = InstrumentationRegistry.getTargetContext().
                    getSharedPreferences(SharedPreferencesUtil.PREF_FILE, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear();
            editor.apply();
        }
    };

    @Test
    public void zeroActivity_A1_A2_A3_mainActivity_exit() {
        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.btn_no), withText("No"), isDisplayed()));
        appCompatButton.perform(click());
        ViewInteraction appCompatButton2 = onView(
                allOf(withId(R.id.next), withText("Next"), isDisplayed()));
        appCompatButton2.perform(click());
        ViewInteraction appCompatButton3 = onView(
                allOf(withId(R.id.btn_yes), withText("Yes"), isDisplayed()));
        appCompatButton3.perform(click());
        ViewInteraction appCompatButton4 = onView(
                allOf(withId(R.id.next), withText("Next"), isDisplayed()));
        appCompatButton4.perform(click());
        ViewInteraction appCompatButton5 = onView(
                allOf(withId(R.id.btn_yes), withText("Yes"), isDisplayed()));
        appCompatButton5.perform(click());
        ViewInteraction appCompatButton6 = onView(
                allOf(withId(R.id.next), withText("Next"), isDisplayed()));
        appCompatButton6.perform(click());
        ViewInteraction appCompatButton7 = onView(
                allOf(withId(R.id.btn_exit), withText("Run away"), isDisplayed()));
        appCompatButton7.perform(click());
    }
}

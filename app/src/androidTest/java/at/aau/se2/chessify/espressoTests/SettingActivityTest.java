package at.aau.se2.chessify.espressoTests;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import at.aau.se2.chessify.R;
import at.aau.se2.chessify.SettingActivity;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class SettingActivityTest {

    @Rule
    public ActivityScenarioRule<SettingActivity> activityRule =
            new ActivityScenarioRule<>(SettingActivity.class);

    @Test
    public void textFieldSettings() {
        onView(withText("Settings")).check(matches(isDisplayed()));
    }

    @Test
    public void CheckIntent() {
        onView(ViewMatchers.withId(R.id.btn_sound)).perform(click());
    }

    @Test
    public void CheckIntentVibration() {
        onView(ViewMatchers.withId(R.id.btn_vibrations)).perform(click());
    }

    @Test
    public void CheckDarkmode() {
        onView(ViewMatchers.withId(R.id.btn_darkmode)).perform(click());
    }

}

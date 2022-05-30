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

import at.aau.se2.chessify.MainActivity;
import at.aau.se2.chessify.R;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class MainActivityTest {

    @Rule
    public ActivityScenarioRule<MainActivity> activityRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void textFieldSettings() {
        onView(withText("CHESSIFY")).check(matches(isDisplayed()));
    }

    @Test
    public void textFieldSettings2() {
        onView(withText("Name")).check(matches(isDisplayed()));
    }

    @Test
    public void textFieldSettings3() {
        onView(withText("PLAY")).check(matches(isDisplayed()));
    }

    @Test
    public void textFieldSettings4() {
        onView(withText("EXIT")).check(matches(isDisplayed()));
    }

    @Test
    public void textFieldSettings5() {
        onView(withText("SETTINGS")).check(matches(isDisplayed()));
    }

    @Test
    public void CheckIntent() {
        onView(ViewMatchers.withId(R.id.btn_sound_main)).perform(click());
    }

}

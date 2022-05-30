package at.aau.se2.chessify.espressoTests;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import at.aau.se2.chessify.Dice.DiceActivity;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class DiceActivityTest {

    @Rule
    public ActivityScenarioRule<DiceActivity> activityRule =
            new ActivityScenarioRule<>(DiceActivity.class);

    @Test
    public void textFieldSettings() {
        onView(withText("CREATE BOARD")).check(matches(isDisplayed()));
    }

    @Test
    public void textFieldSettings2() {
        onView(withText("ABORT")).check(matches(isDisplayed()));
    }

}


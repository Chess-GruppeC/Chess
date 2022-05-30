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

import at.aau.se2.chessify.LobbyActivity;
import at.aau.se2.chessify.R;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class LobbyActivityTest {

    @Rule
    public ActivityScenarioRule<LobbyActivity> activityRule =
            new ActivityScenarioRule<>(LobbyActivity.class);

    @Test
    public void textFieldSettings() {
        onView(withText("Lobby")).check(matches(isDisplayed()));
    }

    @Test
    public void textFieldSettings2() {
        onView(withText("JOIN GAME")).check(matches(isDisplayed()));
    }

    @Test
    public void CheckIntent() {
        onView(ViewMatchers.withId(R.id.btn_sound_lobby)).perform(click());
    }

}


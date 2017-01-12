package ch.semafor.esta;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.widget.ListView;

import junit.framework.Assert;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import ch.semafor.esta.android.MainActivity;

import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;

@RunWith(AndroidJUnit4.class)
public class InstrumentedStudentsTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(
            MainActivity.class);

    @Test
    public void testAdd() throws InterruptedException {

        // Get the initial amount of items in the list
        final int count = getListItemCount(Espresso.onView(ViewMatchers.withId(R.id.list)));

        // Enters data for a new student
        Espresso.onView(ViewMatchers.withId(R.id.firstnameText))
                .perform(typeText("Test"), closeSoftKeyboard());
        Espresso.onView(ViewMatchers.withId(R.id.lastnameText))
                .perform(typeText("Test"), closeSoftKeyboard());
        Espresso.onView(ViewMatchers.withId(R.id.birthdateText))
                .perform(typeText("1111-11-11"), closeSoftKeyboard());

        // Clicks the save button
        Espresso.onView(ViewMatchers.withId(R.id.saveButton)).perform(click());

        // Get the new amount of items in the list
        final int newCount = getListItemCount(Espresso.onView(ViewMatchers.withId(R.id.list)));

        // Checks if the new count is higher by one
        Assert.assertEquals(count + 1, newCount);
    }

    private int getListItemCount(ViewInteraction interaction) {
        final int[] newCount = {0};
        interaction.check(matches(new TypeSafeMatcher<View>() {
            @Override
            protected boolean matchesSafely(View item) {
                ListView listView = (ListView) item;

                newCount[0] = listView.getCount();

                return true;
            }

            @Override
            public void describeTo(Description description) {

            }
        }));

        return newCount[0];
    }

}

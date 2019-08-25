package com.twenk11k.todolists;

import android.view.Gravity;

import androidx.test.espresso.contrib.DrawerActions;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.twenk11k.todolists.ui.activity.EnterActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.DrawerMatchers.isClosed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.IsNot.not;


@RunWith(AndroidJUnit4.class)
public class EnterActivityUserInfoTest {

    @Rule
    public ActivityTestRule<EnterActivity> activityTestRule = new ActivityTestRule<>(EnterActivity.class);


    @Before
    public void init(){

        activityTestRule.getActivity()
                .getSupportFragmentManager().beginTransaction();

    }

    @Test
    public void userInfoTest() throws InterruptedException {

        onView(withId(R.id.drawerLayout))
                .check(matches(isClosed(Gravity.LEFT)))
                .perform(DrawerActions.open());

        onView(withId(R.id.userNameAndSurname)).check(matches(not(withText("null null")))).check(matches(not(withText(""))));

        onView(withId(R.id.userEmail)).check(matches(not(withText(""))));

        Thread.sleep(2000);

    }


}

package com.twenk11k.todolists;


import android.content.Intent;

import androidx.fragment.app.FragmentTransaction;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.twenk11k.todolists.ui.activity.EnterActivity;
import com.twenk11k.todolists.ui.activity.WelcomeActivity;
import com.twenk11k.todolists.ui.fragment.welcome.FragmentRegister;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;


@RunWith(AndroidJUnit4.class)
public class UserRegisterTest {

    @Rule
    public ActivityTestRule<WelcomeActivity> activityTestRule = new ActivityTestRule<>(WelcomeActivity.class,true,false);


    @Before
    public void init(){

     activityTestRule.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                FragmentRegister fragmentRegister = startFragmentRegister();
            }
        });

    }

    private FragmentRegister startFragmentRegister() {

        WelcomeActivity welcomeActivity = (WelcomeActivity) activityTestRule.getActivity();
        FragmentTransaction transaction = welcomeActivity.getSupportFragmentManager().beginTransaction();
        FragmentRegister fragmentRegister = new FragmentRegister();
        transaction.replace(R.id.fragment,fragmentRegister);
        transaction.commitAllowingStateLoss();

        return fragmentRegister;

    }

    @Test
    public void TestUserRegister() throws InterruptedException {
        onView(withId(R.id.editTextName)).perform(click(),typeText("Selim"),closeSoftKeyboard());
        onView(withId(R.id.editTextSurname)).perform(click(),typeText("Demiral"),closeSoftKeyboard());
        onView(withId(R.id.editTextEmail)).perform(click(),typeText("selim@selim.com"),closeSoftKeyboard());
        onView(withId(R.id.editTextPassword)).perform(click(),typeText("selim"),closeSoftKeyboard());
        // onView(withId(R.id.btnRegister)).perform(click());
        Thread.sleep(15000);
    }

}

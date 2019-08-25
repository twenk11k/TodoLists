package com.twenk11k.todolists;


import android.view.KeyEvent;

import androidx.fragment.app.FragmentTransaction;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;
import com.twenk11k.todolists.ui.activity.WelcomeActivity;
import com.twenk11k.todolists.ui.fragment.welcome.FragmentLogin;
import com.twenk11k.todolists.utils.Utils;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.pressKey;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;


@RunWith(AndroidJUnit4.class)
public class UserLoginTest {

    private boolean wasAutoEnabled = false;

    @Rule
    public ActivityTestRule<WelcomeActivity> activityTestRule = new ActivityTestRule<WelcomeActivity>(WelcomeActivity.class){
        @Override
        protected void beforeActivityLaunched() {
            super.beforeActivityLaunched();
            wasAutoEnabled = Utils.getIsAutoEnabled();
            Utils.setIsAutoEnabled(false);
        }
    };


    @Before
    public void init(){

        activityTestRule.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                FragmentLogin fragmentLogin = startFragmentLogin();
            }
        });

    }

    private FragmentLogin startFragmentLogin() {

        WelcomeActivity welcomeActivity = (WelcomeActivity) activityTestRule.getActivity();
        FragmentTransaction transaction = welcomeActivity.getSupportFragmentManager().beginTransaction();
        FragmentLogin fragmentLogin = new FragmentLogin();
        transaction.replace(R.id.fragment,fragmentLogin);
        transaction.commitAllowingStateLoss();

        return fragmentLogin;

    }


    @Test
    public void userLoginTest() throws InterruptedException {

        // We press KEYCODE_DEL to remove whitespace that occurs after Espresso type the '.' character.
        onView(withId(R.id.emailEditText))
                .perform(click(),typeText("sample@sample."),pressKey(KeyEvent.KEYCODE_DEL),typeText("com"),closeSoftKeyboard());

        onView(withId(R.id.passwordEditText))
                .perform(click(),typeText("samplepassword"),closeSoftKeyboard());

        // Here we set auto_enabled value from shared preferences. If the given value was true then we have to set it as true.
        if(wasAutoEnabled){
            Utils.setIsAutoEnabled(wasAutoEnabled);
        }

        // Sleep the thread so there will be enough time for the user interaction.
        Thread.sleep(15000);

    }


}

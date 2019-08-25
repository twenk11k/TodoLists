package com.twenk11k.todolists;


import androidx.fragment.app.FragmentTransaction;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;
import com.twenk11k.todolists.ui.activity.WelcomeActivity;
import com.twenk11k.todolists.ui.fragment.welcome.FragmentLogin;
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
public class UserLoginTest {

    @Rule
    public ActivityTestRule<WelcomeActivity> activityTestRule = new ActivityTestRule<>(WelcomeActivity.class);


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
    public void TestUserLogin() throws InterruptedException {
        onView(withId(R.id.emailEditText)).perform(click(),typeText("Selim"),closeSoftKeyboard());
        onView(withId(R.id.passwordEditText)).perform(click(),typeText("Demiral"),closeSoftKeyboard());
        Thread.sleep(15000);
    }


}

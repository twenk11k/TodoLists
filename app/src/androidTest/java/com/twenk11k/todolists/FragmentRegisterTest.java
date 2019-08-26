package com.twenk11k.todolists;


import android.view.KeyEvent;
import androidx.fragment.app.FragmentTransaction;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;
import com.twenk11k.todolists.ui.activity.WelcomeActivity;
import com.twenk11k.todolists.ui.fragment.welcome.FragmentRegister;
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
public class FragmentRegisterTest {

    private boolean wasAutoEnabled = false;

    @Rule
    public ActivityTestRule<WelcomeActivity> activityTestRule = new ActivityTestRule<WelcomeActivity>(WelcomeActivity.class) {
        @Override
        protected void beforeActivityLaunched() {
            wasAutoEnabled = Utils.getIsAutoEnabled();
            Utils.setIsAutoEnabled(false);
            super.beforeActivityLaunched();
        }
    };

    @Before
    public void init() {

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
        transaction.replace(R.id.fragment, fragmentRegister);
        transaction.commitAllowingStateLoss();
        return fragmentRegister;

    }

    @Test
    public void userRegisterTest() throws InterruptedException {

        onView(withId(R.id.editTextName))
                .perform(click(), typeText("S_Name"), closeSoftKeyboard());

        onView(withId(R.id.editTextSurname))
                .perform(click(), typeText("S_Surname"), closeSoftKeyboard());

        onView(withId(R.id.editTextEmail))
                .perform(click(), typeText("sample@sample."), pressKey(KeyEvent.KEYCODE_DEL), typeText("com"), closeSoftKeyboard());

        onView(withId(R.id.editTextPassword))
                .perform(click(), typeText("s_password"), closeSoftKeyboard());

        // Here we set auto_enabled value from shared preferences. If the given value was true then we have to set it as true.
        if(wasAutoEnabled){
            Utils.setIsAutoEnabled(wasAutoEnabled);
        }

        // Sleep the thread so there will be enough time for the user interaction.
        Thread.sleep(15000);

    }

}

package com.twenk11k.todolists;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.twenk11k.todolists.common.ClickOnCheckbox;
import com.twenk11k.todolists.common.ClickOnImageDeleteItem;
import com.twenk11k.todolists.common.ClickOnImageDeleteList;
import com.twenk11k.todolists.common.ClickOnRelativeList;
import com.twenk11k.todolists.ui.activity.EnterActivity;
import com.twenk11k.todolists.utils.TestUtils;
import com.twenk11k.todolists.utils.Utils;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class TodoInnerOrderTest {

    @Rule
    public ActivityTestRule<EnterActivity> activityTestRule = new ActivityTestRule<>(EnterActivity.class);

    @Test
    public void todoItemsOrderByNameTest() throws InterruptedException {

        // Create a To-Do List
        onView(withId(R.id.fabList)).perform(click());
        onView(withId(R.id.editTextName)).perform(click(), replaceText("To-Do List 1"), closeSoftKeyboard());
        onView(withId(R.id.btnCreateList)).perform(click());
        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(TestUtils.getCountFromRecyclerView(R.id.recyclerView) - 1, new ClickOnRelativeList()));
        // Create 3 To-Do items with descending order
        List<Integer> nameList = TestUtils.getNameList();
        for (int i = 0; i < nameList.size(); i++) {
            onView(withId(R.id.fabInner)).perform(click());
            onView(withId(R.id.editTextName)).perform(replaceText(i + "_Name"));
            onView(withId(R.id.editTextDescription)).perform(replaceText(i + "_Description"));
            onView(withId(R.id.editTextDeadline)).perform(replaceText(Utils.getCurrentDate()));
            onView(withId(R.id.btnCreateItem)).perform(click());
        }
        // Click name item from menu on Toolbar
        onView(withId(R.id.menu_order_by)).perform(click());
        onView(withText(R.string.name)).perform(click());
        // Make thread sleep for 3 seconds so we could check whether it's true or not
        Thread.sleep(3000);
        // Go back to FragmentTodoList
        Espresso.pressBack();
        // Delete To-Do list
        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(TestUtils.getCountFromRecyclerView(R.id.recyclerView) - 1, new ClickOnImageDeleteList()));
        onView(withId(R.id.btnYes)).perform(click());

        Thread.sleep(2000);

    }


    @Test
    public void todoItemsOrderByStatusTest() throws InterruptedException {

        // Create a To-Do List
        onView(withId(R.id.fabList)).perform(click());
        onView(withId(R.id.editTextName)).perform(click(), replaceText("To-Do List 1"), closeSoftKeyboard());
        onView(withId(R.id.btnCreateList)).perform(click());
        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(TestUtils.getCountFromRecyclerView(R.id.recyclerView) - 1, new ClickOnRelativeList()));

        for (List<Integer> lInt : TestUtils.getStatusList()) {
            int lIntCount = 0;
            for (int i = 3; i >= 1; i--) {
                onView(withId(R.id.fabInner)).perform(click());
                onView(withId(R.id.editTextName)).perform(replaceText(i + "_Name"));
                onView(withId(R.id.editTextDescription)).perform(replaceText(i + "_Description"));
                onView(withId(R.id.editTextDeadline)).perform(replaceText(Utils.getCurrentDate()));
                onView(withId(R.id.btnCreateItem)).perform(click());
                if (lInt.get(lIntCount) == 1) {
                    onView(withId(R.id.recyclerViewInner)).perform(RecyclerViewActions.actionOnItemAtPosition(lIntCount, new ClickOnCheckbox()));
                }
                lIntCount++;
            }
            // Click status item from menu on Toolbar
            onView(withId(R.id.menu_order_by)).perform(click());
            onView(withText(R.string.status)).perform(click());
            // Make thread sleep for 2 seconds so we could check whether it's true or not
            Thread.sleep(2000);
            for (int j = 0; j < 3; j++) {
                onView(withId(R.id.recyclerViewInner)).perform(RecyclerViewActions.actionOnItemAtPosition(0, new ClickOnImageDeleteItem()));
                onView(withId(R.id.btnYes)).perform(click());
            }
        }

        // Go back to FragmentTodoList
        Espresso.pressBack();
        // Delete To-Do list
        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(TestUtils.getCountFromRecyclerView(R.id.recyclerView) - 1, new ClickOnImageDeleteList()));
        onView(withId(R.id.btnYes)).perform(click());

        Thread.sleep(2000);

    }


    @Test
    public void todoItemsOrderByDeadlineTest() throws InterruptedException {

        // Create a To-Do List
        onView(withId(R.id.fabList)).perform(click());
        onView(withId(R.id.editTextName)).perform(click(), replaceText("To-Do List 1"), closeSoftKeyboard());
        onView(withId(R.id.btnCreateList)).perform(click());
        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(TestUtils.getCountFromRecyclerView(R.id.recyclerView) - 1, new ClickOnRelativeList()));

        List<String> deadlineList = TestUtils.getDateList();
        for (int i = 0; i < deadlineList.size(); i++) {
            onView(withId(R.id.fabInner)).perform(click());
            onView(withId(R.id.editTextName)).perform(replaceText(i + "_Name"));
            onView(withId(R.id.editTextDescription)).perform(replaceText(i + "_Description"));
            onView(withId(R.id.editTextDeadline)).perform(replaceText(deadlineList.get(i)));
            onView(withId(R.id.btnCreateItem)).perform(click());
        }
        // Click deadline item from menu on Toolbar
        onView(withId(R.id.menu_order_by)).perform(click());
        onView(withText(R.string.deadline)).perform(click());
        // Make thread sleep for 2 seconds so we could check whether it's true or not
        Thread.sleep(2000);

        // Go back to FragmentTodoList
        Espresso.pressBack();

        // Delete To-Do list
        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(TestUtils.getCountFromRecyclerView(R.id.recyclerView) - 1, new ClickOnImageDeleteList()));
        onView(withId(R.id.btnYes)).perform(click());

        Thread.sleep(2000);

    }



}

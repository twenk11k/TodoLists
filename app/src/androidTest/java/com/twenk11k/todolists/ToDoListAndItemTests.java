package com.twenk11k.todolists;

import androidx.fragment.app.FragmentTransaction;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.twenk11k.todolists.roomdb.todolist.TodoItem;
import com.twenk11k.todolists.ui.activity.EnterActivity;
import com.twenk11k.todolists.ui.fragment.enter.FragmentTodoInner;
import com.twenk11k.todolists.ui.fragment.enter.FragmentTodoList;
import com.twenk11k.todolists.utils.TestUtils;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.CoreMatchers.not;


@RunWith(AndroidJUnit4.class)
public class ToDoListAndItemTests {

    @Rule
    public ActivityTestRule<EnterActivity> activityTestRule = new ActivityTestRule<>(EnterActivity.class);


    @Before
    public void init(){

        activityTestRule.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                FragmentTodoList fragmentTodoList = startFragmentTodoList();
            }
        });

    }

    private FragmentTodoList startFragmentTodoList() {

        EnterActivity enterActivity =  activityTestRule.getActivity();
        FragmentTransaction transaction = enterActivity.getSupportFragmentManager().beginTransaction();
        FragmentTodoList fragmentTodoList = new FragmentTodoList();
        transaction.replace(R.id.fragment,fragmentTodoList);
        transaction.commitAllowingStateLoss();
        return fragmentTodoList;

    }

    @Test
    public void createToDoListThenDeleteItTest() throws InterruptedException {

        onView(withId(R.id.fabList)).perform(click());
        onView(withId(R.id.editTextName)).perform(click(),replaceText("Liste 1"),closeSoftKeyboard());
        onView(withId(R.id.btnCreate)).perform(click());
        onView(withId(R.id.relativeList)).check(matches(isDisplayed()));
        onView(withId(R.id.imageDeleteList)).perform(click());
        onView(withId(R.id.btnYes)).perform(click());
        onView(withId(R.id.relativeList)).check(matches(not(isDisplayed())));
        // Thread.sleep(15000);

    }

    @Test
    public void createToDoListThenOpenItTest() throws InterruptedException {
        onView(withId(R.id.fabList)).perform(click());
        onView(withId(R.id.editTextName)).perform(click(),replaceText("Liste 1"),closeSoftKeyboard());
        onView(withId(R.id.btnCreate)).perform(click());
        onView(withId(R.id.relativeList)).perform(click());
        Thread.sleep(50000);

    }


    @Test
    public void createToDoListAndItemTest() throws InterruptedException {
        onView(withId(R.id.fabList)).perform(click());
        onView(withId(R.id.editTextName)).perform(click(),replaceText("Liste 1"),closeSoftKeyboard());
        onView(withId(R.id.btnCreate)).perform(click());
        onView(withId(R.id.relativeList)).check(matches(isDisplayed())).perform(click());
        if(activityTestRule.getActivity().getSupportFragmentManager().findFragmentById(R.id.fragment) instanceof FragmentTodoInner){
            onView(withId(R.id.fabInner)).perform(click());
            onView(withId(R.id.editTextName)).perform(click(),typeText("name"),closeSoftKeyboard());
            onView(withId(R.id.editTextDescription)).perform(click(),typeText("description"),closeSoftKeyboard());
            onView(withId(R.id.editTextDeadline)).perform(click(),typeText("12345"),closeSoftKeyboard());
            onView(withId(R.id.btnCreate)).perform(click());
            onView(withId(R.id.relativeItem)).check(matches(isDisplayed()));
            Thread.sleep(30000);
        }
    }

    @Test
    public void createToDoListAndItemsTest() throws InterruptedException {
        onView(withId(R.id.fabList)).perform(click());
        onView(withId(R.id.editTextName)).perform(click(),replaceText("Liste 1"),closeSoftKeyboard());
        onView(withId(R.id.btnCreate)).perform(click());
        onView(withId(R.id.relativeList)).check(matches(isDisplayed())).perform(click());
        List<TodoItem> todoItemList = TestUtils.tempTodoItems();
        for(TodoItem item : todoItemList){
            if(activityTestRule.getActivity().getSupportFragmentManager().findFragmentById(R.id.fragment) instanceof FragmentTodoInner){
                onView(withId(R.id.fabInner)).perform(click());
                onView(withId(R.id.editTextName)).perform(click(),typeText(item.getName()),closeSoftKeyboard());
                onView(withId(R.id.editTextDescription)).perform(click(),typeText("description"),closeSoftKeyboard());
                onView(withId(R.id.editTextDeadline)).perform(click(),typeText("12345"),closeSoftKeyboard());
                onView(withId(R.id.btnCreate)).perform(click());
            }
        }

        Thread.sleep(40000);
    }


    @Test
    public void createToDoListAndItemThenDeleteItemTest() throws InterruptedException {

        onView(withId(R.id.fabList)).perform(click());
        onView(withId(R.id.editTextName)).perform(click(),replaceText("Liste 1"),closeSoftKeyboard());
        onView(withId(R.id.btnCreate)).perform(click());
        onView(withId(R.id.relativeList)).check(matches(isDisplayed())).perform(click());

        if(activityTestRule.getActivity().getSupportFragmentManager().findFragmentById(R.id.fragment) instanceof FragmentTodoInner){
            onView(withId(R.id.fabInner)).perform(click());
            onView(withId(R.id.editTextName)).perform(click(),typeText("name"),closeSoftKeyboard());
            onView(withId(R.id.editTextDescription)).perform(click(),typeText("description"),closeSoftKeyboard());
            onView(withId(R.id.editTextDeadline)).perform(click(),typeText("12345"),closeSoftKeyboard());
            onView(withId(R.id.btnCreate)).perform(click());
            onView(withId(R.id.imageDeleteItem)).perform(click());
            onView(withId(R.id.btnYes)).perform(click());
            onView(withId(R.id.relativeItem)).check(matches(not(isDisplayed())));
            Thread.sleep(2000);
        }

    }


}

package com.twenk11k.todolists.utils;


import android.view.View;
import android.widget.TextView;

import androidx.annotation.IdRes;
import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import java.util.ArrayList;
import java.util.List;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.core.AllOf.allOf;

public class TestUtils {

    public static int getCountFromRecyclerView(@IdRes int RecyclerViewId) {

        final int[] COUNT = {0};
        Matcher matcher = new TypeSafeMatcher<View>() {
            @Override
            protected boolean matchesSafely(View item) {
                COUNT[0] = ((RecyclerView) item).getAdapter().getItemCount();
                return true;
            }
            @Override
            public void describeTo(Description description) {}
        };
        onView(allOf(withId(RecyclerViewId),isDisplayed())).check(matches(matcher));
        return COUNT[0];

    }

    public static ViewAction setTextInTextView(final String value){

        return new ViewAction() {
            @SuppressWarnings("unchecked")
            @Override
            public Matcher<View> getConstraints() {
                return allOf(isDisplayed(), isAssignableFrom(TextView.class));
            }

            @Override
            public void perform(UiController uiController, View view) {
                ((TextView) view).setText(value);
            }

            @Override
            public String getDescription() {
                return "replace text";
            }
        };

    }


    public static List<Integer> getNameList(){
        List<Integer> lStr = new ArrayList<>();
        lStr.add(3);
        lStr.add(2);
        lStr.add(1);
        return lStr;
    }


    public static List<List<Integer>> getStatusList(){

        List<List<Integer>> lInt = new ArrayList<>();

        List<Integer> l1 =new ArrayList<>();
        l1.add(1);
        l1.add(0);
        l1.add(1);

        lInt.add(l1);

        List<Integer> l2 =new ArrayList<>();
        l2.add(0);
        l2.add(0);
        l2.add(1);

        lInt.add(l2);

        List<Integer> l3 =new ArrayList<>();
        l3.add(0);
        l3.add(1);
        l3.add(1);

        lInt.add(l3);

        return lInt;
    }

    public static List<String> getDateList(){
        List<String> lStr = new ArrayList<>();
        lStr.add("29/08/19");
        lStr.add("28/08/19");
        lStr.add("27/08/19");
        return lStr;
    }


}

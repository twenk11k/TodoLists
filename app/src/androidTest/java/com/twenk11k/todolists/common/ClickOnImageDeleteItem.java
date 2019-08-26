package com.twenk11k.todolists.common;

import android.view.View;

import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;

import com.twenk11k.todolists.R;

import org.hamcrest.Matcher;

import static androidx.test.espresso.action.ViewActions.click;

public class ClickOnImageDeleteItem implements ViewAction {

    ViewAction click = click();

    @Override
    public Matcher<View> getConstraints() {
        return click.getConstraints();
    }

    @Override
    public String getDescription() {
        return " click on imageDeleteItem";
    }

    @Override
    public void perform(UiController uiController, View view) {
        click.perform(uiController, view.findViewById(R.id.imageDeleteItem));
    }

}
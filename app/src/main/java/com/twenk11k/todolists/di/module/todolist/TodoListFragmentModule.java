package com.twenk11k.todolists.di.module.todolist;


import com.twenk11k.todolists.ui.fragment.enter.FragmentTodoList;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;


@Module
public abstract class TodoListFragmentModule {

    @ContributesAndroidInjector
    abstract FragmentTodoList bindTodoListFragment();


}

package com.twenk11k.todolists.di.module.todolist;


import com.twenk11k.todolists.ui.fragment.enter.FragmentTodoInner;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;


@Module
public abstract class TodoInnerFragmentModule {

    @ContributesAndroidInjector
    abstract FragmentTodoInner bindTodoInnerFragment();

}

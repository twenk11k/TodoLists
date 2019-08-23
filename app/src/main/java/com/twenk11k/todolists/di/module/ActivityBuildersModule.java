package com.twenk11k.todolists.di.module;


import com.twenk11k.todolists.di.module.todolist.TodoInnerFragmentModule;
import com.twenk11k.todolists.di.module.todolist.TodoListFragmentModule;
import com.twenk11k.todolists.di.module.user.LoginFragmentModule;
import com.twenk11k.todolists.di.module.user.RegisterFragmentModule;
import com.twenk11k.todolists.ui.activity.EnterActivity;
import com.twenk11k.todolists.ui.activity.WelcomeActivity;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;


@Module
public abstract class ActivityBuildersModule {

    @ContributesAndroidInjector(modules = {RegisterFragmentModule.class, LoginFragmentModule.class})
    abstract WelcomeActivity bindWelcomeActivity();

    @ContributesAndroidInjector(modules = {TodoListFragmentModule.class, TodoInnerFragmentModule.class})
    abstract EnterActivity bindEnterActivity();

}

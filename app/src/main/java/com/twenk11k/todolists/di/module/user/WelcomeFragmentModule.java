package com.twenk11k.todolists.di.module.user;


import com.twenk11k.todolists.ui.fragment.welcome.FragmentWelcome;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;


@Module
public abstract class WelcomeFragmentModule {

    @ContributesAndroidInjector
    abstract FragmentWelcome bindWelcomeFragment();


}

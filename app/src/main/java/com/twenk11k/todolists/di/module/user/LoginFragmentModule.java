package com.twenk11k.todolists.di.module.user;


import com.twenk11k.todolists.ui.fragment.welcome.FragmentLogin;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;


@Module
public abstract class LoginFragmentModule {

    @ContributesAndroidInjector
    abstract FragmentLogin bindLoginFragment();


}

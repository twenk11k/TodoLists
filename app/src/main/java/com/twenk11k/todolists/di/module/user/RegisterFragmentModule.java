package com.twenk11k.todolists.di.module.user;


import com.twenk11k.todolists.ui.fragment.welcome.FragmentRegister;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;


@Module
public abstract class RegisterFragmentModule {

    @ContributesAndroidInjector
    abstract FragmentRegister bindRegisterFragment();


}

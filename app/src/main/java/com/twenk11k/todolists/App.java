package com.twenk11k.todolists;

import android.app.Application;

import com.twenk11k.todolists.di.component.AppComponent;
import com.twenk11k.todolists.di.component.DaggerAppComponent;
import com.twenk11k.todolists.di.injector.AppInjector;
import com.twenk11k.todolists.utils.Utils;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasAndroidInjector;

public class App extends Application implements HasAndroidInjector {

    @Inject
    DispatchingAndroidInjector<Object> activityDispatchingAndroidInjector;

    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        Utils.initUtils(getApplicationContext());

        appComponent = DaggerAppComponent.builder()
                .application(this)
                .build();

        appComponent.inject(this);

        AppInjector.init(this);
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }

    @Override
    public AndroidInjector<Object> androidInjector() {
        return activityDispatchingAndroidInjector;
    }
}

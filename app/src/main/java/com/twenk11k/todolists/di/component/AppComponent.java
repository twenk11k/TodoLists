package com.twenk11k.todolists.di.component;

import android.app.Application;
import com.twenk11k.todolists.App;
import com.twenk11k.todolists.di.module.ActivityBuildersModule;
import com.twenk11k.todolists.di.module.AppModule;
import com.twenk11k.todolists.di.module.todolist.TodoListDbModule;
import com.twenk11k.todolists.di.module.user.UserDbModule;
import com.twenk11k.todolists.di.module.user.WelcomeFragmentModule;
import javax.inject.Singleton;
import dagger.BindsInstance;
import dagger.Component;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component(modules = {AndroidSupportInjectionModule.class, AppModule.class, ActivityBuildersModule.class, UserDbModule.class, TodoListDbModule.class, WelcomeFragmentModule.class})
public interface AppComponent {


    void inject(App app);

    @Component.Builder
    interface Builder {

        @BindsInstance
        Builder application(Application application);

        AppComponent build();
    }


}

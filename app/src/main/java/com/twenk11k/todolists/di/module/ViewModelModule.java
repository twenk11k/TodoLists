package com.twenk11k.todolists.di.module;


import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import com.twenk11k.todolists.di.ViewModelKey;
import com.twenk11k.todolists.di.module.user.UserViewModelFactory;
import com.twenk11k.todolists.ui.viewmodel.TodoListViewModel;
import com.twenk11k.todolists.ui.viewmodel.UserViewModel;
import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;


@Module
public abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(UserViewModel.class)
    abstract ViewModel bindUserViewModel(UserViewModel userViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(TodoListViewModel.class)
    abstract ViewModel bindTodoListViewModel(TodoListViewModel todoListViewModel);

    @Binds
    abstract ViewModelProvider.Factory bindUserViewModelFactory(UserViewModelFactory factory);

}

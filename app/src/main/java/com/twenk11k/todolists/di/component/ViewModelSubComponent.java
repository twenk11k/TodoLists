package com.twenk11k.todolists.di.component;


import com.twenk11k.todolists.ui.viewmodel.TodoListViewModel;
import com.twenk11k.todolists.ui.viewmodel.UserViewModel;

import dagger.Subcomponent;

@Subcomponent
public interface ViewModelSubComponent {

    @Subcomponent.Builder
    interface Builder {
        ViewModelSubComponent build();
    }

    UserViewModel userViewModel();
    TodoListViewModel todoListViewModel();

}

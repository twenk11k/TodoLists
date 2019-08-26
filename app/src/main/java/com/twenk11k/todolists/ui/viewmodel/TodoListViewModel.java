package com.twenk11k.todolists.ui.viewmodel;


import android.app.Application;
import android.util.Log;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.MutableLiveData;
import com.twenk11k.todolists.repository.TodoListRepository;
import com.twenk11k.todolists.repository.UserRepository;
import com.twenk11k.todolists.roomdb.todolist.TodoItem;
import com.twenk11k.todolists.roomdb.todolist.TodoList;
import com.twenk11k.todolists.roomdb.user.User;
import java.util.List;
import javax.inject.Inject;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class TodoListViewModel extends AndroidViewModel {

    @Inject
    public TodoListRepository todoListRepository;

    @Inject
    public UserRepository userRepository;

    private CompositeDisposable compositeDisposable;
    private MutableLiveData<List<User>> autoLoginInfoLiveData;
    private MutableLiveData<List<TodoItem>> todoItemLiveData;

    @Inject
    public TodoListViewModel(Application application) {

        super(application);
        compositeDisposable = new CompositeDisposable();
        autoLoginInfoLiveData = new MutableLiveData<>();
        todoItemLiveData = new MutableLiveData<>();

    }

    public MutableLiveData<List<User>> getAutoLoginInfoLiveData() {
        return autoLoginInfoLiveData;
    }

    public MutableLiveData<List<TodoItem>> getTodoItemLiveData() {
        return todoItemLiveData;
    }

    public void insert(TodoList data) {

        Disposable insertTodoList = todoListRepository.insertData(data)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();

        compositeDisposable.add(insertTodoList);

    }


    public void insert(TodoItem todoItem) {

        Disposable insertTodoItem = todoListRepository.insertData(todoItem)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();

        compositeDisposable.add(insertTodoItem);

    }

    public void delete(TodoItem data) {

        Disposable deleteTodoItem = todoListRepository.deleteData(data)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();

        compositeDisposable.add(deleteTodoItem);

    }

    public void delete(TodoList data) {

        Disposable deleteTodoList = todoListRepository.deleteTodoList(data)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();

        compositeDisposable.add(deleteTodoList);

        Disposable deleteItemsByTodoListId = todoListRepository.deleteItemsByTodoListIdAndEmail(data.getId(), data.getEmail())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();

        compositeDisposable.add(deleteItemsByTodoListId);

    }

    public void update(TodoItem data) {

        Disposable updateTodoItem = todoListRepository.updateData(data)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();

        compositeDisposable.add(updateTodoItem);

    }


    public Single<List<TodoItem>> loadTodoItemsSingle(int todoListId, String todoListEmail) {

        return todoListRepository.loadTodoItems(todoListId, todoListEmail);

    }

    public void loadTodoItems(int todoListId, String todoListEmail) {

        Disposable loadTodoItemsDisposable = todoListRepository.loadTodoItems(todoListId, todoListEmail)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onLoadTodoItemFetch, this::onLoadTodoItemError);

        compositeDisposable.add(loadTodoItemsDisposable);


    }


    private void onLoadTodoItemFetch(List<TodoItem> todoItems) {
        todoItemLiveData.setValue(todoItems);
    }

    private void onLoadTodoItemError(Throwable t) {

        Log.d(getClass().getName(), "onLoadTodoItemError: " + t.getMessage());
        todoItemLiveData.setValue(null);

    }

    public LiveData<List<TodoList>> loadUserWithTodoList(String email) {

        return LiveDataReactiveStreams.fromPublisher(todoListRepository.loadUserWithTodoList(email));

    }


    public void loadAutoLoginInfo() {

        Disposable autoLoginDisposable = userRepository.getAutoLoginInfo()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onLoadAutoLoginInfoFetched, this::onLoadAutoLoginInfoError);

        compositeDisposable.add(autoLoginDisposable);

    }

    private void onLoadAutoLoginInfoError(Throwable t) {

        Log.d(getClass().getName(), "onLoadAutoLoginInfoError: " + t.getMessage());
        autoLoginInfoLiveData.setValue(null);

    }

    private void onLoadAutoLoginInfoFetched(List<User> userList) {
        autoLoginInfoLiveData.setValue(userList);
    }


    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.clear();
    }

}

package com.twenk11k.todolists.ui.fragment.enter;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.twenk11k.todolists.R;
import com.twenk11k.todolists.common.StatusBarView;
import com.twenk11k.todolists.databinding.FragmentTodoListInnerBinding;
import com.twenk11k.todolists.di.injector.Injectable;
import com.twenk11k.todolists.listener.OnToDoAdapterClick;
import com.twenk11k.todolists.roomdb.todolist.TodoItem;
import com.twenk11k.todolists.ui.adapter.TodoAdapter;
import com.twenk11k.todolists.ui.dialog.CreateToDoItemDialog;
import com.twenk11k.todolists.ui.viewmodel.TodoListViewModel;
import com.twenk11k.todolists.utils.Utils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javax.inject.Inject;
import dagger.android.support.AndroidSupportInjection;
import static com.twenk11k.todolists.common.Constants.EXTRA_TODO_LIST_EMAIL;
import static com.twenk11k.todolists.common.Constants.EXTRA_TODO_LIST_ID;
import static com.twenk11k.todolists.common.Constants.EXTRA_TODO_LIST_NAME;


public class FragmentTodoInner extends Fragment implements OnToDoAdapterClick, Injectable {

    private Toolbar toolbar;
    private FloatingActionButton fab;
    private FragmentTodoListInnerBinding binding;
    private RecyclerView recyclerView;
    private TodoAdapter todoAdapter;
    private TextView emptyText;
    private ProgressBar pBar;
    private String toDoListName, todoListEmail;
    private int todoListId;
    private List<TodoItem> todoItems = new ArrayList<>();
    private TodoListViewModel todoListViewModel;
    private Context context;

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    public static FragmentTodoInner newInstance(String todoListName, int todoId, String todoListEmail) {

        FragmentTodoInner fragment = new FragmentTodoInner();
        Bundle args = new Bundle();
        args.putString(EXTRA_TODO_LIST_NAME, todoListName);
        args.putString(EXTRA_TODO_LIST_EMAIL, todoListEmail);
        args.putInt(EXTRA_TODO_LIST_ID, todoId);
        fragment.setArguments(args);

        return fragment;

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_todo_list_inner, container, false);

        todoListViewModel = new ViewModelProvider(this, viewModelFactory).get(TodoListViewModel.class);

        setViews();

        if (getArguments() != null) {
            toDoListName = getArguments().getString(EXTRA_TODO_LIST_NAME);
            todoListEmail = getArguments().getString(EXTRA_TODO_LIST_EMAIL);
            todoListId = getArguments().getInt(EXTRA_TODO_LIST_ID);
            toolbar.setTitle(toDoListName);
            if (todoListId >= 0) {
                bringTodoItems();
            }

        } else {
            toolbar.setTitle(R.string.todo_list_inner_title);
        }

        return binding.getRoot();
    }

    private void bringTodoItems() {
        emptyText.setVisibility(View.GONE);
        recyclerView.setVisibility(View.GONE);
        pBar.setVisibility(View.VISIBLE);
        todoListViewModel.loadTodoItems(todoListId, todoListEmail);
        todoListViewModel.getTodoItemLiveData().observe(this, this::handleTodoItems);

    }

    private void handleTodoItems(List<TodoItem> todoItemList) {

        if (!todoItemList.isEmpty()) {

            pBar.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            todoItems.clear();
            todoItems.addAll(todoItemList);
            todoAdapter.notifyDataSetChanged();

        } else {

            pBar.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            emptyText.setVisibility(View.VISIBLE);

        }
    }


    private void setViews() {

        fab = binding.fabInner;
        toolbar = binding.toolbar;
        recyclerView = binding.recyclerView;
        emptyText = binding.emptyText;
        pBar = binding.pBar;
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.addItemDecoration(new DividerItemDecoration(context,
                DividerItemDecoration.VERTICAL));
        todoAdapter = new TodoAdapter(context, todoItems, this);
        recyclerView.setAdapter(todoAdapter);
        todoAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                checkIfListEmpty();
            }

            @Override
            public void onItemRangeRemoved(int positionStart, int itemCount) {
                super.onItemRangeRemoved(positionStart, itemCount);
                checkIfListEmpty();
            }
        });

        fab.setOnClickListener(v -> {
            CreateToDoItemDialog createTodoListDialog = new CreateToDoItemDialog(context, (name, description, deadline, createDate) -> {
                TodoItem data = new TodoItem();
                data.setName(name);
                data.setDescription(description);
                data.setDeadline(deadline);
                data.setCreateDate(createDate);
                data.setTodoListEmail(todoListEmail);
                data.setTodoListId(todoListId);
                todoListViewModel.insert(data);
                todoItems.add(data);
                todoAdapter.notifyDataSetChanged();
            });
            createTodoListDialog.show();
        });
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidSupportInjection.inject(this);
        context = getContext();
        setHasOptionsMenu(true);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case android.R.id.home:
                getActivity().onBackPressed();
                return true;
            case R.id.menu_order_by_name:
                orderItemsByName();
                break;
            case R.id.menu_order_by_status:
                orderItemsByStatus();
                break;
            case R.id.menu_order_by_deadline:
                orderItemsByDeadline();
                break;
            case R.id.menu_order_by_createdate:
                orderItemsByCreateDate();
                break;
            case R.id.menu_order_by_expired:
                orderItemsByExpired();
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    private void orderItemsByExpired() {
        Collections.sort(todoItems, new Comparator<TodoItem>() {
            @Override
            public int compare(TodoItem o1, TodoItem o2) {
                return Utils.getCurrentDate().compareTo(o1.getDeadline()) + Utils.getCurrentDate().compareTo(o2.getDeadline());
            }
        });
        todoAdapter.notifyDataSetChanged();
    }

    private void orderItemsByDeadline() {
        Collections.sort(todoItems, new Comparator<TodoItem>() {
            @Override
            public int compare(TodoItem lhs, TodoItem rhs) {
                return lhs.getDeadline().compareTo(rhs.getDeadline());
            }
        });
        todoAdapter.notifyDataSetChanged();
    }

    private void orderItemsByName() {
        Collections.sort(todoItems, new Comparator<TodoItem>() {
            @Override
            public int compare(TodoItem lhs, TodoItem rhs) {
                return lhs.getName().compareTo(rhs.getName());
            }
        });
        todoAdapter.notifyDataSetChanged();
    }

    private void orderItemsByCreateDate() {
        Collections.sort(todoItems, new Comparator<TodoItem>() {
            @Override
            public int compare(TodoItem lhs, TodoItem rhs) {
                return lhs.getCreateDate().compareTo(rhs.getCreateDate());
            }
        });
        todoAdapter.notifyDataSetChanged();
    }

    private void orderItemsByStatus() {

        Collections.sort(todoItems, new Comparator<TodoItem>() {
            @Override
            public int compare(TodoItem lhs, TodoItem rhs) {
                return rhs.getStatus() - lhs.getStatus();
            }
        });

        todoAdapter.notifyDataSetChanged();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (Build.VERSION.SDK_INT < 21) {
            if (Build.VERSION.SDK_INT >= 19) {
                int statusBarHeight = Utils.getStatHeight(context);
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) view.findViewById(R.id.toolbar).getLayoutParams();
                layoutParams.setMargins(0, statusBarHeight, 0, 0);
                view.findViewById(R.id.toolbar).setLayoutParams(layoutParams);
            }
        }

        ((AppCompatActivity) getActivity()).setSupportActionBar(binding.toolbar);
        binding.toolbar.setBackgroundColor(ContextCompat.getColor(context, R.color.colorToolbar));
        setStatusbarColor(binding.statusBarCustom, ContextCompat.getColor(context, R.color.colorToolbar));


        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        binding.toolbar.setNavigationOnClickListener(v -> getActivity().getSupportFragmentManager().popBackStack());
    }

    private void setStatusbarColor(StatusBarView statusBarView, int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (statusBarView != null) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    statusBarView.setBackgroundColor(Utils.darkenColor(color));
                } else {
                    statusBarView.setBackgroundColor(color);
                }
            }
        }
    }

    private void checkIfListEmpty() {
        if (emptyText != null) {
            emptyText.setVisibility(todoAdapter == null || todoAdapter.getItemCount() == 0 ? View.VISIBLE : View.GONE);
        }
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.menu_todo, menu);
    }

    @Override
    public void onAdapterClickDelete(TodoItem todoItem) {
        todoListViewModel.delete(todoItem);
    }

    @Override
    public void onAdapterClickUpdate(TodoItem todoItem) {
        todoListViewModel.update(todoItem);
    }


}

package com.twenk11k.todolists.ui.fragment.enter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.twenk11k.todolists.R;
import com.twenk11k.todolists.common.StatusBarView;
import com.twenk11k.todolists.databinding.FragmentTodoListBinding;
import com.twenk11k.todolists.di.injector.Injectable;
import com.twenk11k.todolists.listener.OnCreateToDoListDialogClick;
import com.twenk11k.todolists.listener.OnToDoListAdapterClick;
import com.twenk11k.todolists.roomdb.ExportTodoList;
import com.twenk11k.todolists.roomdb.todolist.table.TodoItem;
import com.twenk11k.todolists.roomdb.todolist.table.TodoList;
import com.twenk11k.todolists.roomdb.user.User;
import com.twenk11k.todolists.ui.activity.EnterActivity;
import com.twenk11k.todolists.ui.adapter.TodoListAdapter;
import com.twenk11k.todolists.ui.dialog.CreateToDoListDialog;
import com.twenk11k.todolists.ui.viewmodel.TodoListViewModel;
import com.twenk11k.todolists.utils.Utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;

import static com.twenk11k.todolists.common.Constants.EXTRA_USER_EMAIL_TODO_LIST;
import static com.twenk11k.todolists.common.Constants.EXTRA_USER_ID_TODO_LIST;


public class FragmentTodoList extends Fragment implements OnToDoListAdapterClick, Injectable {

    private Toolbar toolbar;
    private FloatingActionButton fab;
    private FragmentTodoListBinding binding;
    private RecyclerView recyclerView;
    private TodoListAdapter todoListAdapter;
    private TextView emptyText;
    private ProgressBar pBar;
    private TodoListViewModel todoListViewModel;
    private List<TodoList> toDoLists = new ArrayList<>();
    private String userEmail = "";
    private int userId;
    private Context context;

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    public static FragmentTodoList newInstance(String email, int id) {

        FragmentTodoList fragment = new FragmentTodoList();
        Bundle args = new Bundle();
        args.putString(EXTRA_USER_EMAIL_TODO_LIST, email);
        args.putInt(EXTRA_USER_ID_TODO_LIST, id);
        fragment.setArguments(args);

        return fragment;

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_todo_list, container, false);
        binding.toolbar.setTitle(R.string.todo_lists_title);
        todoListViewModel = ViewModelProviders.of(this,viewModelFactory).get(TodoListViewModel.class);
        setViews();
        if (getArguments() != null) {
            userEmail = getArguments().getString(EXTRA_USER_EMAIL_TODO_LIST);
            if (userEmail != null) {
                bringTodoLists();
            } else {
                todoListViewModel.loadAutoLoginInfo();
                todoListViewModel.getAutoLoginInfoLiveData().observe(this,this::handleAutoLoginInfo);
            }
        }


        return binding.getRoot();
    }

    private void handleAutoLoginInfo(User user){
        if (user != null) {
            userEmail = user.getEmail();
            if (!userEmail.isEmpty()) {
                bringTodoLists();
            }
        } else {
            Toast.makeText(context.getApplicationContext(),getString(R.string.error_occurred),Toast.LENGTH_SHORT).show();
        }
    }

    private void bringTodoLists() {
        emptyText.setVisibility(View.GONE);
        recyclerView.setVisibility(View.GONE);
        pBar.setVisibility(View.VISIBLE);
        todoListViewModel.loadUserWithTodoList(userEmail).observe(this, new Observer<List<TodoList>>() {
            @Override
            public void onChanged(List<TodoList> todoListList) {
                if(todoListList != null){
                    if(!todoListList.isEmpty()){
                        pBar.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                        if (todoListList != null) {
                            toDoLists.clear();
                            toDoLists.addAll(todoListList);
                            todoListAdapter.notifyDataSetChanged();
                        }
                    } else {
                        pBar.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                        emptyText.setVisibility(View.VISIBLE);
                    }
                } else {
                    pBar.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                    emptyText.setVisibility(View.VISIBLE);
                    Toast.makeText(context.getApplicationContext(),getString(R.string.error_occurred),Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void setViews() {

        fab = binding.fabList;
        toolbar = binding.toolbar;
        recyclerView = binding.recyclerView;
        emptyText = binding.emptyText;
        pBar = binding.pBar;

        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.addItemDecoration(new DividerItemDecoration(context,
                DividerItemDecoration.VERTICAL));
        todoListAdapter = new TodoListAdapter(context, toDoLists, this);
        recyclerView.setAdapter(todoListAdapter);
        todoListAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
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

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateToDoListDialog createTodoListDialog = new CreateToDoListDialog(context, new OnCreateToDoListDialogClick() {
                    @Override
                    public void onCreateBtnClick(String name, String createDate) {
                        TodoList data = new TodoList();
                        data.setName(name);
                        data.setCreateDate(createDate);
                        data.setEmail(userEmail);
                        todoListViewModel.insert(data);
                    }
                });
                createTodoListDialog.show();
            }
        });
        ((EnterActivity) getActivity()).setDrawerOptions(toolbar);
    }

    private void checkIfListEmpty() {
        if (emptyText != null) {
            emptyText.setVisibility(todoListAdapter == null || todoListAdapter.getItemCount() == 0 ? View.VISIBLE : View.GONE);
        }
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

    private void openTodoListInnerFragment(String name, int id,String email) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment fragment = FragmentTodoInner.newInstance(name, id, email);
        fragmentTransaction.add(R.id.fragment, fragment);
        fragmentTransaction.hide(getActivity().getSupportFragmentManager().findFragmentById(R.id.fragment));
        fragmentTransaction.addToBackStack(FragmentTodoList.class.getName());
        fragmentTransaction.commit();
    }

    @Override
    public void onAdapterClick(String name, int id,String email) {

        Log.d("tololistid","onAdapterClick: "+String.valueOf(id));
        openTodoListInnerFragment(name, id,email);
    }

    @Override
    public void onAdapterClickDelete(TodoList todoList) {
        todoListViewModel.delete(todoList);
    }

    private TodoList tempTodoList;
    private int tempExportClick = 0;

    @Override
    public void onExportListLocalClick(TodoList todoList) {
        tempExportClick = 1;
        tempTodoList = todoList;
        if (Utils.getPermissionExternalEnabled()) {
            exportListLocalStorage();
        } else {
            ((EnterActivity) getActivity()).requestExternalStoragePermission();
        }
    }
    public void exportList(){
        if(tempExportClick == 1){
            exportListLocalStorage();
        } else if(tempExportClick == 2){
            exportListSendViaEmail();
        }
    }

    public void exportListLocalStorage() {

        if(tempTodoList != null ){
            todoListViewModel.loadTodoItems(tempTodoList.getId(),tempTodoList.getEmail());
            todoListViewModel.getTodoItemLiveData().observe(this,this::handleTodoItemsExportList);
        } else {
            Toast.makeText(context.getApplicationContext(),getString(R.string.error_occurred),Toast.LENGTH_SHORT).show();
        }

    }

    private void handleTodoItemsExportList(List<TodoItem> todoItems) {
    if(todoItems!= null){
        if(!todoItems.isEmpty()){
            ExportTodoList exportTodoList = new ExportTodoList();
            exportTodoList.setTodos(todoItems);
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String sJson = gson.toJson(exportTodoList);
            FileWriter writer = null;
            try {
                String fileName = Environment.getExternalStorageDirectory() +"/"+tempTodoList.getName() + ".json";
                System.out.println(fileName);
                writer = new FileWriter(fileName);
                writer.write(sJson);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (writer != null) {
                    try {
                        writer.close();
                        Toast.makeText(context,getString(R.string.success_export_todo_list),Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    } else {
        Toast.makeText(context.getApplicationContext(),getString(R.string.error_occurred),Toast.LENGTH_SHORT).show();
    }

    }


    @Override
    public void onExportListEmailClick(TodoList todoList) {
        tempExportClick = 2;
        tempTodoList = todoList;
        if (Utils.getPermissionExternalEnabled()) {
            exportListSendViaEmail();
        } else {
            ((EnterActivity) getActivity()).requestExternalStoragePermission();
        }
    }

    @SuppressLint("CheckResult")
    private void exportListSendViaEmail() {
        if(tempTodoList != null){
            todoListViewModel.loadTodoItems(tempTodoList.getId(),tempTodoList.getEmail());
            todoListViewModel.getTodoItemLiveData().observe(this,this::handleTodoItemsExportSendEmail);

        }
    }

    private void handleTodoItemsExportSendEmail(List<TodoItem> todoItems) {
        if(!todoItems.isEmpty()){
            ExportTodoList exportTodoList = new ExportTodoList();
            exportTodoList.setTodos(todoItems);
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String sJson = gson.toJson(exportTodoList);
            FileWriter writer = null;
            String fileName = "";
            try {
                fileName = Environment.getExternalStorageDirectory() +"/"+tempTodoList.getName() + ".json";
                System.out.println(fileName);
                writer = new FileWriter(fileName);
                writer.write(sJson);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (writer != null) {
                    try {
                        writer.close();
                        String filename= tempTodoList.getName() + ".json";
                        File file=new File(fileName);
                        Uri uri = FileProvider.getUriForFile(context,context.getApplicationContext().getPackageName()+".fileprovider",file);
                        Intent intent = new Intent(Intent.ACTION_SEND);
                        intent.setType("text/plain");
                        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.email_subject)+" "+tempTodoList.getName());
                        intent.putExtra(Intent.EXTRA_TEXT, getString(R.string.email_text));
                        intent.putExtra(Intent.EXTRA_STREAM, uri);
                        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        startActivity(Intent.createChooser(intent, getString(R.string.email_title)));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidSupportInjection.inject(this);
        context = getContext();
    }

}
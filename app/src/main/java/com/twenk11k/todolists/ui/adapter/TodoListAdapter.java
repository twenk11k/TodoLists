package com.twenk11k.todolists.ui.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import com.twenk11k.todolists.R;
import com.twenk11k.todolists.databinding.ItemTodoListBinding;
import com.twenk11k.todolists.listener.OnDeleteToDoListDialogClick;
import com.twenk11k.todolists.listener.OnExportToDoListDialogClick;
import com.twenk11k.todolists.listener.OnToDoListAdapterClick;
import com.twenk11k.todolists.roomdb.todolist.TodoList;
import com.twenk11k.todolists.ui.dialog.DeleteToDoDialog;
import com.twenk11k.todolists.ui.dialog.ExportToDoListDialog;
import java.util.List;


public class TodoListAdapter extends RecyclerView.Adapter<TodoListAdapter.ViewHolder> {

    private Context context;
    private List<TodoList> toDoLists;
    private OnToDoListAdapterClick onToDoListAdapterClick;

    public TodoListAdapter(Context context, List<TodoList> toDoLists, OnToDoListAdapterClick onToDoListAdapterClick){
        this.context = context;
        this.toDoLists = toDoLists;
        this.onToDoListAdapterClick = onToDoListAdapterClick;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemTodoListBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),R.layout.item_todo_list,parent,false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        String name = toDoLists.get(position).getName();
        String createDate = toDoLists.get(position).getCreateDate();
        holder.binding.textName.setText(name);
        holder.binding.textCreateDate.setText(context.getString(R.string.created_at)+" "+createDate);

    }

    @Override
    public int getItemCount() {
        return toDoLists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ItemTodoListBinding binding;

        private ViewHolder(ItemTodoListBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            binding.imageDeleteList.setOnClickListener(this);
            binding.imageExport.setOnClickListener(this);
            binding.relativeList.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            TodoList toDoList = toDoLists.get(getAdapterPosition());
            switch (v.getId()){
                case R.id.imageDeleteList:
                    DeleteToDoDialog deleteToDoDialog = new DeleteToDoDialog(context,toDoList.getName(), new OnDeleteToDoListDialogClick() {
                        @Override
                        public void onDeleteBtnClick() {
                            onToDoListAdapterClick.onAdapterClickDelete(toDoList);
                            toDoLists.remove(getAdapterPosition());
                        }
                    },true);
                    deleteToDoDialog.show();
                    break;
                case R.id.imageExport:
                    ExportToDoListDialog exportToDoListDialog = new ExportToDoListDialog(context,toDoList.getName(), new OnExportToDoListDialogClick() {
                        @Override
                        public void onSaveToStorageBtnClick() {
                            onToDoListAdapterClick.onExportListLocalClick(toDoList);
                        }

                        @Override
                        public void onSendViaEmailBtnClick() {
                            onToDoListAdapterClick.onExportListEmailClick(toDoList);
                        }
                    });
                    exportToDoListDialog.show();
                    break;
                case R.id.relativeList:
                    onToDoListAdapterClick.onAdapterClick(toDoList.getName(),toDoList.getId(),toDoList.getEmail());
                    break;
            }
        }
    }

}

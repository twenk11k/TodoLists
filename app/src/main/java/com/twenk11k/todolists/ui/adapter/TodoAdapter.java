package com.twenk11k.todolists.ui.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import com.twenk11k.todolists.R;
import com.twenk11k.todolists.databinding.ItemTodoBinding;
import com.twenk11k.todolists.listener.OnDeleteToDoListDialogClick;
import com.twenk11k.todolists.listener.OnToDoAdapterClick;
import com.twenk11k.todolists.roomdb.todolist.TodoItem;
import com.twenk11k.todolists.ui.dialog.DeleteToDoDialog;
import java.util.List;


public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.ViewHolder> {

    private Context context;
    private List<TodoItem> toDoItemList;
    private OnToDoAdapterClick onToDoAdapterClick;

    public TodoAdapter(Context context, List<TodoItem> toDoItemList, OnToDoAdapterClick onToDoAdapterClick) {
        this.context = context;
        this.toDoItemList = toDoItemList;
        this.onToDoAdapterClick = onToDoAdapterClick;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemTodoBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_todo, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        TodoItem todoItem = toDoItemList.get(position);
        String name = todoItem.getName();
        String description = todoItem.getDescription();
        String deadline = todoItem.getDeadline();
        String createDate = todoItem.getCreateDate();
        int checkBoxStatus = todoItem.getStatus();
        holder.binding.textName.setText(name);
        holder.binding.textDescription.setText(description);
        holder.binding.textDeadline.setText(context.getString(R.string.deadlineText).concat(deadline));
        holder.binding.textCreateDate.setText(context.getString(R.string.created_at).concat(createDate));
        if (checkBoxStatus == 1) {
            holder.binding.checkBoxStatus.setChecked(true);
        } else {
            holder.binding.checkBoxStatus.setChecked(false);
        }

    }

    @Override
    public int getItemCount() {
        return toDoItemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ItemTodoBinding binding;

        private ViewHolder(ItemTodoBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            this.binding.checkBoxStatus.setOnClickListener(this);
            this.binding.relativeItem.setOnClickListener(this);
            this.binding.imageDeleteItem.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            TodoItem todoItem = toDoItemList.get(getAdapterPosition());
            switch (v.getId()) {
                case R.id.imageDeleteItem:
                    DeleteToDoDialog deleteToDoDialog = new DeleteToDoDialog(context, todoItem.getName(), new OnDeleteToDoListDialogClick() {
                        @Override
                        public void onDeleteBtnClick() {
                            onToDoAdapterClick.onAdapterClickDelete(toDoItemList.get(getAdapterPosition()));
                            toDoItemList.remove(getAdapterPosition());
                            notifyItemRemoved(getAdapterPosition());
                            notifyItemRangeChanged(getAdapterPosition(), getItemCount());
                        }
                    },false);
                    deleteToDoDialog.show();
                    break;
                case R.id.relativeItem:
                    binding.checkBoxStatus.performClick();
                    break;
                case R.id.checkBoxStatus:
                    if (binding.checkBoxStatus.isChecked()) {
                        todoItem.setStatus(1);
                    } else {
                        todoItem.setStatus(0);
                    }
                    onToDoAdapterClick.onAdapterClickUpdate(todoItem);
                    break;

            }
        }
    }

}

package com.twenk11k.todolists.ui.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.twenk11k.todolists.R;
import com.twenk11k.todolists.listener.OnDeleteToDoListDialogClick;
import com.twenk11k.todolists.listener.OnToDoAdapterClick;
import com.twenk11k.todolists.roomdb.todolist.table.TodoItem;
import com.twenk11k.todolists.ui.dialog.DeleteToDoListDialog;
import java.util.List;


public class TodoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<TodoItem> toDoItemList;
    private OnToDoAdapterClick onToDoAdapterClick;

    public TodoAdapter(Context context, List<TodoItem> toDoItemList,OnToDoAdapterClick onToDoAdapterClick){
        this.context = context;
        this.toDoItemList = toDoItemList;
        this.onToDoAdapterClick = onToDoAdapterClick;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;

        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_todo, parent, false);
        viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        ViewHolder viewHolder = (ViewHolder) holder;

        TodoItem todoItem = toDoItemList.get(position);
        String name = todoItem.getName();
        String description = todoItem.getDescription();
        String deadline = todoItem.getDeadline();
        String createDate = todoItem.getCreateDate();
        int checkBoxStatus = todoItem.getStatus();
        viewHolder.textName.setText(name);
        viewHolder.textDescription.setText(description);
        viewHolder.textDeadline.setText(context.getString(R.string.deadlineText)+" "+deadline);
        viewHolder.textCreateDate.setText(context.getString(R.string.created_at)+" "+createDate);
        if(checkBoxStatus == 1){
            viewHolder.checkBoxStatus.setChecked(true);
        } else {
            viewHolder.checkBoxStatus.setChecked(false);
        }
    }

    @Override
    public int getItemCount() {
        return toDoItemList.size();
    }

    private class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView textName,textDescription,textDeadline,textCreateDate;
        private ImageView imageDelete;
        private CheckBox checkBoxStatus;
        private RelativeLayout relativeItem;

        private ViewHolder(@NonNull View itemView) {
            super(itemView);

            textName = itemView.findViewById(R.id.textName);
            textDescription = itemView.findViewById(R.id.textDescription);
            textDeadline = itemView.findViewById(R.id.textDeadline);
            checkBoxStatus = itemView.findViewById(R.id.checkBoxStatus);
            relativeItem = itemView.findViewById(R.id.relativeItem);
            textCreateDate = itemView.findViewById(R.id.textCreateDate);
            imageDelete = itemView.findViewById(R.id.imageDeleteItem);
            imageDelete.setOnClickListener(this);
            relativeItem.setOnClickListener(this);
            checkBoxStatus.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            TodoItem todoItem = toDoItemList.get(getAdapterPosition());
            switch (v.getId()){
                case R.id.imageDeleteItem:
                    DeleteToDoListDialog deleteToDoListDialog = new DeleteToDoListDialog(context, todoItem.getName(), new OnDeleteToDoListDialogClick() {
                        @Override
                        public void onDeleteBtnClick() {
                            onToDoAdapterClick.onAdapterClickDelete(toDoItemList.get(getAdapterPosition()));
                            toDoItemList.remove(getAdapterPosition());
                            notifyItemRemoved(getAdapterPosition());
                            notifyItemRangeChanged(getAdapterPosition(), getItemCount());
                        }
                    });
                    deleteToDoListDialog.show();
                    break;
                case R.id.relativeItem:
                    checkBoxStatus.performClick();
                    break;
                case R.id.checkBoxStatus:
                    if(checkBoxStatus.isChecked()){
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

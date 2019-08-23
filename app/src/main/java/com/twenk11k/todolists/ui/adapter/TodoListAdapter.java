package com.twenk11k.todolists.ui.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.twenk11k.todolists.R;
import com.twenk11k.todolists.listener.OnDeleteToDoListDialogClick;
import com.twenk11k.todolists.listener.OnExportToDoListDialogClick;
import com.twenk11k.todolists.listener.OnToDoListAdapterClick;
import com.twenk11k.todolists.roomdb.todolist.TodoList;
import com.twenk11k.todolists.ui.dialog.DeleteToDoListDialog;
import com.twenk11k.todolists.ui.dialog.ExportToDoListDialog;
import java.util.List;


public class TodoListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

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
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_todo_list, parent, false);
        viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        ViewHolder viewHolder = (ViewHolder) holder;
        String name = toDoLists.get(position).getName();
        String createDate = toDoLists.get(position).getCreateDate();
        viewHolder.textName.setText(name);
        viewHolder.textCreateDate.setText(context.getString(R.string.created_at)+" "+createDate);

    }

    @Override
    public int getItemCount() {
        return toDoLists.size();
    }

    private class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView textName,textCreateDate;
        private ImageView imageDelete,imageExport;
        private RelativeLayout relativeList;

        private ViewHolder(@NonNull View itemView) {
            super(itemView);
            textName = itemView.findViewById(R.id.textName);
            textCreateDate = itemView.findViewById(R.id.textCreateDate);
            imageDelete = itemView.findViewById(R.id.imageDeleteList);
            imageExport = itemView.findViewById(R.id.imageExport);
            relativeList = itemView.findViewById(R.id.relativeList);
            imageDelete.setOnClickListener(this);
            imageExport.setOnClickListener(this);
            relativeList.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            TodoList toDoList = toDoLists.get(getAdapterPosition());
            switch (v.getId()){
                case R.id.imageDeleteList:
                    DeleteToDoListDialog deleteToDoListDialog = new DeleteToDoListDialog(context,toDoList.getName(), new OnDeleteToDoListDialogClick() {
                        @Override
                        public void onDeleteBtnClick() {
                            toDoLists.remove(getAdapterPosition());
                            notifyItemRemoved(getAdapterPosition());
                            notifyItemRangeChanged(getAdapterPosition(), getItemCount());
                            onToDoListAdapterClick.onAdapterClickDelete(toDoList);
                        }
                    });
                    deleteToDoListDialog.show();
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

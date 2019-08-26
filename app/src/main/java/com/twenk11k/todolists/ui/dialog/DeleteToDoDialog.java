package com.twenk11k.todolists.ui.dialog;


import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.twenk11k.todolists.R;
import com.twenk11k.todolists.listener.OnDeleteToDoListDialogClick;


public class DeleteToDoDialog extends Dialog {

    private OnDeleteToDoListDialogClick onDeleteToDoListDialogClick;
    private String listName;
    private TextView textListName;
    private Button btnNo, btnYes;
    private boolean isTodoList;

    public DeleteToDoDialog(@NonNull Context context, String listName, OnDeleteToDoListDialogClick onDeleteToDoListDialogClick,boolean isTodoList) {
        super(context);
        this.onDeleteToDoListDialogClick = onDeleteToDoListDialogClick;
        this.listName = listName;
        this.isTodoList = isTodoList;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        if(isTodoList)
            setContentView(R.layout.dialog_delete_todo_list);
        else
            setContentView(R.layout.dialog_delete_todo_item);

        setViews();
    }

    private void setViews() {
        textListName = findViewById(R.id.textListName);
        btnNo = findViewById(R.id.btnNo);
        btnYes = findViewById(R.id.btnYes);
        textListName.setText(listName);
        btnNo.setOnClickListener(v -> dismiss());
        btnYes.setOnClickListener(v -> {
            onDeleteToDoListDialogClick.onDeleteBtnClick();
            dismiss();
        });
    }

}

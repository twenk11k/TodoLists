package com.twenk11k.todolists.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.twenk11k.todolists.R;
import com.twenk11k.todolists.listener.OnDeleteToDoListDialogClick;

public class DeleteToDoListDialog extends Dialog {

    private OnDeleteToDoListDialogClick onDeleteToDoListDialogClick;
    private String listName;
    private TextView textListName;
    private Button btnNo, btnYes;

    public DeleteToDoListDialog(@NonNull Context context, String listName, OnDeleteToDoListDialogClick onDeleteToDoListDialogClick) {
        super(context);
        this.onDeleteToDoListDialogClick = onDeleteToDoListDialogClick;
        this.listName = listName;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_delete_todo_list);
        setViews();
    }

    private void setViews() {
        textListName = findViewById(R.id.textListName);
        btnNo = findViewById(R.id.btnNo);
        btnYes = findViewById(R.id.btnYes);
        textListName.setText(listName);
        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDeleteToDoListDialogClick.onDeleteBtnClick();
                dismiss();
            }
        });
    }

}

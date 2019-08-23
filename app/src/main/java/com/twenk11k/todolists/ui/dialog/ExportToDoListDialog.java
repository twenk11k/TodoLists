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
import com.twenk11k.todolists.listener.OnExportToDoListDialogClick;

public class ExportToDoListDialog extends Dialog {

    private OnExportToDoListDialogClick onExportToDoListDialogClick;
    private String listName;
    private TextView textViewName;
    private Button btnSendViaEmail,btnSaveToStorage;

    public ExportToDoListDialog(@NonNull Context context, String listName, OnExportToDoListDialogClick onExportToDoListDialogClick) {
        super(context);
        this.onExportToDoListDialogClick = onExportToDoListDialogClick;
        this.listName = listName;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_export_todo_list);
        setViews();
    }

    private void setViews() {

        textViewName = findViewById(R.id.textListName);
        btnSendViaEmail = findViewById(R.id.btnSendViaEmail);
        btnSaveToStorage = findViewById(R.id.btnSaveToStorage);
        textViewName.setText(listName);

        btnSaveToStorage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onExportToDoListDialogClick.onSaveToStorageBtnClick();
                dismiss();
            }
        });

        btnSendViaEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onExportToDoListDialogClick.onSendViaEmailBtnClick();
                dismiss();
            }
        });
    }


}

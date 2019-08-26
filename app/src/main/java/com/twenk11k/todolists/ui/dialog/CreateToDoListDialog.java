package com.twenk11k.todolists.ui.dialog;


import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import com.twenk11k.todolists.R;
import com.twenk11k.todolists.listener.OnCreateToDoListDialogClick;
import com.twenk11k.todolists.utils.Utils;


public class CreateToDoListDialog extends Dialog {

    private EditText editTextName;
    private Button btnCreateList;
    private boolean isApproved = false;
    private OnCreateToDoListDialogClick onCreateToDoListDialogClick;
    private Context context;

    public CreateToDoListDialog(Context context, OnCreateToDoListDialogClick onCreateToDoListDialogClick) {
        super(context);
        this.context = context;
        this.onCreateToDoListDialogClick = onCreateToDoListDialogClick;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_create_todo_list);
        setViews();
    }

    private void setViews() {
        editTextName = findViewById(R.id.editTextName);
        btnCreateList = findViewById(R.id.btnCreateList);
        btnCreateList.setOnClickListener(v -> onCreateButtonClick());

    }

    private void onCreateButtonClick() {
        isApproved = true;
        String strName = editTextName.getText().toString().trim();

        if (strName.isEmpty()) {
            isApproved = false;
            editTextName.setError(context.getString(R.string.error_name));
        }
        if (isApproved) {
            onCreateToDoListDialogClick.onCreateBtnClick(strName, Utils.getCurrentDate());
            dismiss();
        }

    }

}
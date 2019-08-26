package com.twenk11k.todolists.ui.dialog;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import com.google.android.material.textfield.TextInputLayout;
import com.twenk11k.todolists.R;
import com.twenk11k.todolists.listener.OnCreateToDoItemDialogClick;
import com.twenk11k.todolists.utils.Utils;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


public class CreateToDoItemDialog extends Dialog {

    private EditText editTextName,editTextDescription,editTextDeadline;
    private TextInputLayout textInputDeadline;
    private Button btnCreate;
    private boolean isApproved = false;
    private OnCreateToDoItemDialogClick onCreateToDoItemDialogClick;
    private Context context;
    private final Calendar myCalendar = Calendar.getInstance();


    public CreateToDoItemDialog(Context context,OnCreateToDoItemDialogClick onCreateToDoItemDialogClick){
        super(context);
        this.context = context;
        this.onCreateToDoItemDialogClick = onCreateToDoItemDialogClick;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_create_todo_item);
        setViews();
    }

    private void setViews() {
        editTextName = findViewById(R.id.editTextName);
        editTextDescription = findViewById(R.id.editTextDescription);
        editTextDeadline = findViewById(R.id.editTextDeadline);
        textInputDeadline = findViewById(R.id.textInputDeadline);
        btnCreate = findViewById(R.id.btnCreateItem);
        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCreateButtonClick();
            }
        });
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };
        textInputDeadline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createDatePickerDialog(date);
            }
        });
        editTextDeadline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createDatePickerDialog(date);
            }
        });

    }
    private void createDatePickerDialog(DatePickerDialog.OnDateSetListener date){
        DatePickerDialog datePickerDialog = new DatePickerDialog(context, date, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis()-1000);
        datePickerDialog.show();
    }

    private void updateLabel() {
        String myFormat = "dd/MM/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        editTextDeadline.setText(sdf.format(myCalendar.getTime()));
    }

    private void onCreateButtonClick() {
        isApproved = true;
        String strName = editTextName.getText().toString().trim();
        String strDescription = editTextDescription.getText().toString().trim();
        String strDeadline = editTextDeadline.getText().toString().trim();

        if(strName.isEmpty()){
            isApproved = false;
            editTextName.setError(context.getString(R.string.error_name));
        }
        if(strDescription.isEmpty()){
            isApproved = false;
            editTextDescription.setError(context.getString(R.string.error_description));
        }
        if(strDeadline.isEmpty()){
            isApproved = false;
            editTextDeadline.setError(context.getString(R.string.error_deadline));
        }

        if(isApproved){
            onCreateToDoItemDialogClick.onCreateBtnClick(strName, strDescription,strDeadline, Utils.getCurrentDate());
            dismiss();
        }

    }

}
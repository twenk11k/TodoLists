package com.twenk11k.todolists.ui.fragment.welcome;


import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.twenk11k.todolists.R;
import com.twenk11k.todolists.common.EmailValidator;
import com.twenk11k.todolists.databinding.FragmentRegisterBinding;
import com.twenk11k.todolists.di.injector.Injectable;
import com.twenk11k.todolists.roomdb.user.User;
import com.twenk11k.todolists.ui.activity.EnterActivity;
import com.twenk11k.todolists.ui.viewmodel.UserViewModel;
import com.twenk11k.todolists.utils.Utils;
import javax.inject.Inject;
import dagger.android.support.AndroidSupportInjection;


public class FragmentRegister extends Fragment implements Injectable {

    private FragmentRegisterBinding binding;
    private Button btnRegister;
    private EditText editTextName, editTextSurname, editTextEmail, editTextPassword;
    private UserViewModel userViewModel;
    private boolean isApproved = true;
    private Context context;

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_register, container, false);
        binding.toolbar.setTitle(R.string.register_title);

        userViewModel = new ViewModelProvider(this, viewModelFactory).get(UserViewModel.class);

        setViews();

        return binding.getRoot();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidSupportInjection.inject(this);
        context = getContext();
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
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
    }

    private void setViews() {

        btnRegister = binding.btnRegister;
        editTextName = binding.editTextName;
        editTextSurname = binding.editTextSurname;
        editTextEmail = binding.editTextEmail;
        editTextPassword = binding.editTextPassword;
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRegisterButtonClick();
            }
        });

    }

    private User currentUser;

    private void onRegisterButtonClick() {
        isApproved = true;
        String strName = binding.editTextName.getText().toString().trim();
        String strSurname = binding.editTextSurname.getText().toString().trim();
        String strEmail = binding.editTextEmail.getText().toString().trim();
        String strPassword = binding.editTextPassword.getText().toString().trim();

        User data = new User();

        if (strName.isEmpty()) {
            isApproved = false;
            editTextName.setError(context.getString(R.string.error_name));
        }
        if (strSurname.isEmpty()) {
            isApproved = false;
            editTextSurname.setError(context.getString(R.string.error_surname));
        }
        if (strEmail.isEmpty()) {
            isApproved = false;
            editTextEmail.setError(context.getString(R.string.error_email));
        } else {
            if(!EmailValidator.isEmailValid(strEmail)) {
                isApproved = false;
                editTextEmail.setError(context.getString(R.string.error_correct_email));
            }
        }
        if (strPassword.isEmpty()) {
            isApproved = false;
            editTextPassword.setError(context.getString(R.string.error_password));
        }
        if (isApproved) {

            data.setName(strName);
            data.setSurname(strSurname);
            data.setEmail(strEmail);
            data.setPassword(strPassword);
            data.setAutoLogin(1);
            this.currentUser = data;
            userViewModel.checkIfUserExist(data);
            userViewModel.getUserLiveData().observe(this, this::handleUser);

        }
    }

    private void handleUser(User user) {
        if (user == null && currentUser != null) {
            userViewModel.insert(currentUser);
            openEnterActivity(currentUser);
        } else {
            editTextEmail.setError(context.getString(R.string.email_not_available));
        }
    }


    private void openEnterActivity(User data) {
        Intent intent = new Intent(getActivity(), EnterActivity.class);
        intent.putExtra("userName", data.getName());
        intent.putExtra("userSurname", data.getSurname());
        intent.putExtra("userEmail", data.getEmail());
        intent.putExtra("userId", data.getId());
        Utils.setIsAutoEnabled(true);
        startActivity(intent);
        getActivity().finish();
    }


}

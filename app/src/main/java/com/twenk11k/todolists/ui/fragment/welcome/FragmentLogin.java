package com.twenk11k.todolists.ui.fragment.welcome;


import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.twenk11k.todolists.R;
import com.twenk11k.todolists.common.EmailValidator;
import com.twenk11k.todolists.databinding.FragmentLoginBinding;
import com.twenk11k.todolists.di.injector.Injectable;
import com.twenk11k.todolists.roomdb.user.User;
import com.twenk11k.todolists.ui.activity.EnterActivity;
import com.twenk11k.todolists.ui.viewmodel.UserViewModel;
import com.twenk11k.todolists.utils.Utils;
import javax.inject.Inject;
import dagger.android.support.AndroidSupportInjection;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class FragmentLogin extends Fragment implements Injectable {


    private FragmentLoginBinding binding;
    private Button loginButton;
    private UserViewModel userViewModel;
    private EditText editTextEmail,editTextPassword;
    private boolean isApproved = true;
    private Context context;
    private Disposable disposable;

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false);
        userViewModel = new ViewModelProvider(this,viewModelFactory).get(UserViewModel.class);
        binding.toolbar.setTitle(R.string.login_title);
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

        loginButton = binding.btnLogin;
        editTextEmail = binding.emailEditText;
        editTextPassword = binding.passwordEditText;
        loginButton.setOnClickListener(v -> onLoginButtonClick());

    }


    private void onLoginButtonClick() {

        isApproved = true;
        String strEmail = binding.emailEditText.getText().toString().trim();
        String strPassword = binding.passwordEditText.getText().toString().trim();

        User data = new User();
        if(strEmail.isEmpty()){
            isApproved = false;
            editTextEmail.setError(context.getString(R.string.error_email));
        } else {
            if(!EmailValidator.isEmailValid(strEmail)) {
                isApproved = false;
                editTextEmail.setError(context.getString(R.string.error_correct_email));
            }
        }
        if(strPassword.isEmpty()){
            isApproved = false;
            editTextPassword.setError(context.getString(R.string.error_password));
        }
        if (isApproved) {
            data.setEmail(strEmail);
            data.setPassword(strPassword);
            data.setAutoLogin(1);
            userViewModel.checkIfUserExist(data);

            userViewModel.findUserByEmailAndPassword(data)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new SingleObserver<User>() {
                        @Override
                        public void onSubscribe(Disposable d) {
                            disposable = d;
                        }

                        @Override
                        public void onSuccess(User user) {
                            handleUser(user);
                        }

                        @Override
                        public void onError(Throwable e) {
                            Toast.makeText(context,getString(R.string.error_occurred),Toast.LENGTH_SHORT).show();
                            Log.d(getClass().getName(),e.getMessage());
                        }
                    });

        }
    }
    private void handleUser(User user){

        if(user != null){
            userViewModel.updateAutoLogin(user.getId(),1);
            openEnterActivity(user);
        } else {
            Toast.makeText(context.getApplicationContext(),getString(R.string.error_occurred),Toast.LENGTH_SHORT).show();
        }

    }

    private void openEnterActivity(User data) {
        Intent intent = new Intent(getActivity(), EnterActivity.class);
        intent.putExtra("userName",data.getName());
        intent.putExtra("userSurname",data.getSurname());
        intent.putExtra("userEmail",data.getEmail());
        intent.putExtra("userId",data.getId());
        Utils.setIsAutoEnabled(true);
        startActivity(intent);
        getActivity().finish();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(disposable != null && !disposable.isDisposed()){
            disposable.dispose();
        }
    }
}

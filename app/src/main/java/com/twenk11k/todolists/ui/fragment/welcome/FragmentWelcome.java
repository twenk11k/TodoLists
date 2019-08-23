package com.twenk11k.todolists.ui.fragment.welcome;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.twenk11k.todolists.R;
import com.twenk11k.todolists.databinding.FragmentWelcomeBinding;

public class FragmentWelcome extends Fragment {

    private FragmentWelcomeBinding binding;
    private Button btnLogin,btnRegister;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_welcome,container,false);
        setViews();
        return binding.getRoot();
    }

    private void setViews() {
        btnLogin = binding.btnLogin;
        btnRegister = binding.btnRegister;
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLoginOrRegisterFragment(true);
            }
        });
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLoginOrRegisterFragment(false);
            }
        });
    }
    private void openLoginOrRegisterFragment(boolean isLogin){
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment fragment;
        if(isLogin){
            fragment = new FragmentLogin();
        } else {
            fragment = new FragmentRegister();
        }
        // fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
        fragmentTransaction.add(R.id.fragment, fragment);
        fragmentTransaction.hide(getActivity().getSupportFragmentManager().findFragmentById(R.id.fragment));
        fragmentTransaction.addToBackStack(FragmentWelcome.class.getName());
        fragmentTransaction.commit();
    }
}

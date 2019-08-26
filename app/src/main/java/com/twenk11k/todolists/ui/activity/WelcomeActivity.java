package com.twenk11k.todolists.ui.activity;


import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.twenk11k.todolists.R;
import com.twenk11k.todolists.ui.activity.base.BaseActivity;
import com.twenk11k.todolists.ui.fragment.welcome.FragmentWelcome;
import com.twenk11k.todolists.utils.Utils;


public class WelcomeActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_welcome);
        openLoginOrDirect();

    }

    private void openLoginOrDirect() {

        if (Utils.getIsAutoEnabled()) {
            setEnterActivity();
        } else {
            setWelcomeFragment();
        }

    }

    private void setEnterActivity() {
        Intent intent = new Intent(this,EnterActivity.class);
        startActivity(intent);
        finish();

    }

    private void setWelcomeFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment fragment;
        fragment = new FragmentWelcome();
        fragmentTransaction.replace(R.id.fragment, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}

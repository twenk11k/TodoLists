package com.twenk11k.todolists.ui.activity;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import com.google.android.material.navigation.NavigationView;
import com.twenk11k.todolists.R;
import com.twenk11k.todolists.databinding.ActivityEnterBinding;
import com.twenk11k.todolists.databinding.NavigationViewHeaderBinding;
import com.twenk11k.todolists.roomdb.user.User;
import com.twenk11k.todolists.ui.activity.base.BaseActivity;
import com.twenk11k.todolists.ui.fragment.enter.FragmentTodoList;
import com.twenk11k.todolists.ui.viewmodel.UserViewModel;
import com.twenk11k.todolists.utils.Utils;
import javax.inject.Inject;


public class EnterActivity extends BaseActivity {

    private ActivityEnterBinding binding;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private String userName, userSurname, userEmail;
    private int userId;
    private TextView userNameAndSurname, userEmailText;
    private NavigationViewHeaderBinding headerBinding;
    private Button btnLogout;
    private boolean isUserInfoNull = false;
    private UserViewModel userViewModel;

    @Inject
    ViewModelProvider.Factory viewModelFactory;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_enter);
        headerBinding = NavigationViewHeaderBinding.bind(binding.navigationView.getHeaderView(0));
        userViewModel = new ViewModelProvider(this, viewModelFactory).get(UserViewModel.class);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            userName = extras.getString("userName");
            userSurname = extras.getString("userSurname");
            userEmail = extras.getString("userEmail");
            userId = extras.getInt("userId");
        } else {
            isUserInfoNull = true;
        }
        setViews();
        openTodoFragment();

    }

    private void setViews() {
        drawerLayout = binding.drawerLayout;
        navigationView = binding.navigationView;
        userNameAndSurname = headerBinding.userNameAndSurname;
        userEmailText = headerBinding.userEmail;
        btnLogout = headerBinding.btnLogout;


        setUserInfoTextViews();

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openWelcomeActivity(getString(R.string.logged_out));
            }
        });

    }

    private void setUserInfoTextViews() {
        if (isUserInfoNull) {
            userViewModel.loadAutoLoginInfo();
            userViewModel.getAutoLoginInfoLiveData().observe(this, this::handleAutoLoginInfo);
        } else {
            setLoginInfo();
        }

    }

    private void handleAutoLoginInfo(User user) {
        if (user != null) {
            userName = user.getName();
            userSurname = user.getSurname();
            userEmail = user.getEmail();
            userId = user.getId();
            setLoginInfo();
            openTodoFragment();
        } else {
            openWelcomeActivity(getString(R.string.error_occurred_try_again));
        }
    }

    private void setLoginInfo() {
        if (userEmail != null) {
            String nameAndSurname = userName + " " + userSurname;
            userNameAndSurname.setText(nameAndSurname);
            userEmailText.setText(userEmail);
        } else {
            openWelcomeActivity(getString(R.string.error_occurred_try_again));
        }

    }

    private void openWelcomeActivity(String errorMessage) {
        Intent intent = new Intent(EnterActivity.this, WelcomeActivity.class);
        Utils.setIsAutoEnabled(false);
        userViewModel.updateAllAutoLoginToZero();
        startActivity(intent);
        Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_SHORT).show();
        finish();
    }

    public void setDrawerOptions(Toolbar toolbar) {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.setDrawerIndicatorEnabled(true);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            drawerLayout.setOnApplyWindowInsetsListener((view, windowInsets) -> {
                navigationView.dispatchApplyWindowInsets(windowInsets);
                return windowInsets.replaceSystemWindowInsets(0, 0, 0, 0);
            });
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            drawerLayout.openDrawer(GravityCompat.START);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void openTodoFragment() {

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment fragment = FragmentTodoList.newInstance(userEmail);

        fragmentTransaction.replace(R.id.fragment, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (grantResults[0]  == PackageManager.PERMISSION_GRANTED) {

            Utils.setPermissionExternalEnabled(true);

            if (getSupportFragmentManager().findFragmentById(R.id.fragment) instanceof FragmentTodoList) {
                    ((FragmentTodoList) getSupportFragmentManager().findFragmentById(R.id.fragment)).requestExportList();
            }

        } else {

            Utils.setPermissionExternalEnabled(false);
            if (ActivityCompat.shouldShowRequestPermissionRationale(EnterActivity.this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                Toast.makeText(getApplicationContext(), getString(R.string.error_permission), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(), getString(R.string.error_permission), Toast.LENGTH_SHORT).show();
            }

        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }


}

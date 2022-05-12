package com.elmoselhy.solution.ui.user;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import com.elmoselhy.solution.R;
import com.elmoselhy.solution.base.BaseActivity;
import com.elmoselhy.solution.databinding.ActivityUserHomeBinding;
import com.elmoselhy.solution.ui.AboutUsActivity;
import com.elmoselhy.solution.ui.SplashActivity;
import com.elmoselhy.solution.viewmodels.UserViewModel;

public class UserHomeActivity extends BaseActivity {


    ActivityUserHomeBinding binding;
    UserViewModel userViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_user_home);
        initPage();
    }

    private void initPage() {
        setUpViewModel();

        binding.profileBtn.setOnClickListener(view -> {
            startActivity(new Intent(this, UserProfileActivity.class));
        });
        binding.makeAReportBtn.setOnClickListener(view -> {
            startActivity(new Intent(this, ChooseCorporationActivity.class));
        });
        binding.myReportsBtn.setOnClickListener(view -> {
            startActivity(new Intent(this, UserReportsActivity.class));
        });
        binding.aboutUsBtn.setOnClickListener(view -> {
            startActivity(new Intent(this, AboutUsActivity.class));
        });
        binding.logoutBtn.setOnClickListener(view -> {
            userViewModel.logout();
            startActivity(new Intent(this, SplashActivity.class));
            finishAffinity();
        });
    }

    private void setUpViewModel() {
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        userViewModel.setContext(this);
    }

}
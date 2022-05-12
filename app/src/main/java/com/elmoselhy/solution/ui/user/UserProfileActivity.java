package com.elmoselhy.solution.ui.user;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import com.elmoselhy.solution.R;
import com.elmoselhy.solution.databinding.ActivityUserProfileBinding;
import com.elmoselhy.solution.viewmodels.UserViewModel;
import com.google.firebase.auth.FirebaseAuth;

public class UserProfileActivity extends AppCompatActivity {
    ActivityUserProfileBinding binding;
    UserViewModel userViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_user_profile);
        initPage();
    }

    private void initPage() {
        setUpViewModel();
    }


    private void setUpViewModel() {
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        userViewModel.setContext(this);
        setUpObserver();
        binding.setLifecycleOwner(this);
    }
    private void setUpObserver() {
        userViewModel.getUserFromDatabase(FirebaseAuth.getInstance().getCurrentUser().getUid());
        userViewModel.observeGetUser().observe(this,account -> {
            binding.setAccount(account);
        });
    }

}
package com.elmoselhy.solution.ui.user;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import com.elmoselhy.solution.R;
import com.elmoselhy.solution.databinding.ActivityUserProfileBinding;

public class UserProfileActivity extends AppCompatActivity {
    ActivityUserProfileBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_user_profile);
        initPage();
    }

    private void initPage() {

    }
}
package com.elmoselhy.solution.ui;

import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;

import com.elmoselhy.solution.R;
import com.elmoselhy.solution.base.BaseActivity;
import com.elmoselhy.solution.databinding.ActivityChooseClientTypeBinding;
import com.elmoselhy.solution.ui.corporation.CorporationLoginActivity;
import com.elmoselhy.solution.ui.user.UserLoginActivity;

public class ChooseClientTypeActivity extends BaseActivity {

    ActivityChooseClientTypeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_choose_client_type);
        initPage();
    }

    private void initPage() {
        setUpPageActions();
    }

    private void setUpPageActions() {
        binding.userBtn.setOnClickListener(view -> {
            startActivity(new Intent(this, UserLoginActivity.class));
        });
        binding.corporationBtn.setOnClickListener(view -> {
            startActivity(new Intent(this, CorporationLoginActivity.class));
        });
    }


}
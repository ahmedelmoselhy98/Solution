package com.elmoselhy.solution.ui.corporation;

import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;

import com.elmoselhy.solution.MainActivity;
import com.elmoselhy.solution.R;
import com.elmoselhy.solution.base.BaseActivity;
import com.elmoselhy.solution.databinding.ActivityCorporationLoginBinding;

public class CorporationLoginActivity extends BaseActivity {
    ActivityCorporationLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_corporation_login);
        initPage();
    }

    private void initPage() {
        setUpPageActions();
    }

    private void setUpPageActions() {
        binding.loginBtn.setOnClickListener(view -> {
            startActivity(new Intent(this, MainActivity.class));
        });
        binding.registerTv.setOnClickListener(view -> {
            startActivity(new Intent(this,CorporationRegisterActivity.class));
        });
    }
}
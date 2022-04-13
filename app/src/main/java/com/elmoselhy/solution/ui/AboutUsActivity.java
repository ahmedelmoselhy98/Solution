package com.elmoselhy.solution.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.elmoselhy.solution.R;
import com.elmoselhy.solution.databinding.ActivityAboutUsBinding;

public class AboutUsActivity extends AppCompatActivity {
ActivityAboutUsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_about_us);
        initPage();
    }

    private void initPage() {
        setUpPageActions();
    }

    private void setUpPageActions() {
        binding.backIv.setOnClickListener(view -> {
            onBackPressed();
        });
    }
}
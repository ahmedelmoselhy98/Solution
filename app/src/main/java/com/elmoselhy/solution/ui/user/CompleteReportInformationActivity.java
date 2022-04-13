package com.elmoselhy.solution.ui.user;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;

import com.elmoselhy.solution.R;
import com.elmoselhy.solution.databinding.ActivityCompleteReportInformationBinding;

public class CompleteReportInformationActivity extends AppCompatActivity {
    ActivityCompleteReportInformationBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_complete_report_information);
        initPage();
    }

    private void initPage() {
        binding.makeAReportBtn.setOnClickListener(view -> {
            startActivity(new Intent(this, UserHomeActivity.class));
        });
        setUpPageActions();
    }

    private void setUpPageActions() {
        binding.backIv.setOnClickListener(view -> {
            onBackPressed();
        });
    }
}
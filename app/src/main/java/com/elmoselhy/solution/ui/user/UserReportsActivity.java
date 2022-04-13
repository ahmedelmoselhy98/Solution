package com.elmoselhy.solution.ui.user;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;

import com.elmoselhy.solution.R;
import com.elmoselhy.solution.adapter.ReportAdapter;
import com.elmoselhy.solution.databinding.ActivityUserReportsBinding;
import com.elmoselhy.solution.model.response.Report;

import java.util.ArrayList;

public class UserReportsActivity extends AppCompatActivity {

    ActivityUserReportsBinding binding;
    ReportAdapter reportAdapter;
    ArrayList<Report> reportList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_user_reports);
        initPage();
    }

    private void initPage() {
        setUpCorporationsList();
        setUpPageActions();
    }

    private void setUpPageActions() {
        binding.backIv.setOnClickListener(view -> {
            onBackPressed();
        });
    }

    private void setUpCorporationsList() {
        reportAdapter = new ReportAdapter(this,reportList);
        binding.recycler.setAdapter(reportAdapter);
        binding.recycler.setLayoutManager(new LinearLayoutManager(this));
    }
}
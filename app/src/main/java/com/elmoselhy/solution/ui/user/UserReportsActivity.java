package com.elmoselhy.solution.ui.user;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;

import com.elmoselhy.solution.R;
import com.elmoselhy.solution.adapter.ReportAdapter;
import com.elmoselhy.solution.databinding.ActivityUserReportsBinding;
import com.elmoselhy.solution.model.response.Report;
import com.elmoselhy.solution.viewmodels.UserViewModel;

import java.util.ArrayList;

public class UserReportsActivity extends AppCompatActivity {

    ActivityUserReportsBinding binding;
    ReportAdapter reportAdapter;
    ArrayList<Report> reportList = new ArrayList<>();
    UserViewModel userViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_user_reports);
        initPage();
    }

    private void initPage() {
        setUpCorporationsList();
        setUpPageActions();
        setUpViewModel();
    }

    private void setUpPageActions() {
        binding.backIv.setOnClickListener(view -> {
            onBackPressed();
        });
    }

    private void setUpViewModel() {
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        userViewModel.setContext(this);
        setUpObserver();
        binding.setLifecycleOwner(this);
    }
    private void setUpObserver() {
        userViewModel.getReportListDatabase();
        userViewModel.observeReportList().observe(this, reports -> {
            if (reports != null && !reports.isEmpty()) {
                binding.emptyText.setVisibility(View.GONE);
                reportList.clear();
                reportList.addAll(reports);
                reportAdapter.notifyDataSetChanged();
            }else
                binding.emptyText.setVisibility(View.VISIBLE);

        });
    }

    private void setUpCorporationsList() {
        reportAdapter = new ReportAdapter(this,reportList);
        binding.recycler.setAdapter(reportAdapter);
        binding.recycler.setLayoutManager(new LinearLayoutManager(this));
    }
}
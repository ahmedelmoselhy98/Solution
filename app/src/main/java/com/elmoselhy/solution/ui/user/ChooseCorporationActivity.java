package com.elmoselhy.solution.ui.user;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;

import com.elmoselhy.solution.R;
import com.elmoselhy.solution.adapter.CorporationAdapter;
import com.elmoselhy.solution.base.BaseActivity;
import com.elmoselhy.solution.databinding.ActivityChooseCorporationBinding;
import com.elmoselhy.solution.model.response.Corporation;

import java.util.ArrayList;

public class ChooseCorporationActivity extends BaseActivity {

    ActivityChooseCorporationBinding binding;
    CorporationAdapter corporationAdapter;
    ArrayList<Corporation> corporationList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_choose_corporation);
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
        corporationAdapter = new CorporationAdapter(this,corporationList);
        binding.recycler.setAdapter(corporationAdapter);
        binding.recycler.setLayoutManager(new LinearLayoutManager(this));
    }
}
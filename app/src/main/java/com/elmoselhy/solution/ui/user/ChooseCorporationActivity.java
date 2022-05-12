package com.elmoselhy.solution.ui.user;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;

import com.elmoselhy.solution.R;
import com.elmoselhy.solution.adapter.CorporationAdapter;
import com.elmoselhy.solution.base.BaseActivity;
import com.elmoselhy.solution.databinding.ActivityChooseCorporationBinding;
import com.elmoselhy.solution.model.response.Account;
import com.elmoselhy.solution.model.response.Corporation;
import com.elmoselhy.solution.viewmodels.UserViewModel;

import java.util.ArrayList;

public class ChooseCorporationActivity extends BaseActivity {

    ActivityChooseCorporationBinding binding;
    CorporationAdapter corporationAdapter;
    ArrayList<Account> corporationList = new ArrayList<>();
    UserViewModel userViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_choose_corporation);
        initPage();
    }

    private void initPage() {
        setUpCorporationsList();
        setUpPageActions();
        setUpViewModel();
    }

    private void setUpViewModel() {
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        userViewModel.setContext(this);
        setUpObserver();
    }

    private void setUpObserver() {
        userViewModel.getCorporationListDatabase();
        userViewModel.observeCorporationList().observe(this, corporations -> {
            if (corporations != null && !corporations.isEmpty()) {
                corporationList.clear();
                corporationList.addAll(corporations);
                corporationAdapter.notifyDataSetChanged();
            }
        });
    }

    private void setUpPageActions() {
        binding.backIv.setOnClickListener(view -> {
            onBackPressed();
        });
    }

    private void setUpCorporationsList() {
        corporationAdapter = new CorporationAdapter(this, corporationList);
        binding.recycler.setAdapter(corporationAdapter);
        binding.recycler.setLayoutManager(new LinearLayoutManager(this));
    }
}
package com.elmoselhy.solution.ui.corporation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import com.elmoselhy.solution.MainActivity;
import com.elmoselhy.solution.R;
import com.elmoselhy.solution.base.BaseActivity;
import com.elmoselhy.solution.databinding.ActivityCorporationRegisterBinding;

public class CorporationRegisterActivity extends BaseActivity {
    ActivityCorporationRegisterBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_corporation_register);
        initPage();
    }
    private void initPage() {
        setUpPageActions();
    }
    private void setUpPageActions() {
        binding.registerBtn.setOnClickListener(view -> {
            startActivity(new Intent(this, MainActivity.class));
        });
    }
}
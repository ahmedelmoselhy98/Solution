package com.elmoselhy.solution.ui.corporation;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.elmoselhy.solution.MainActivity;
import com.elmoselhy.solution.R;
import com.elmoselhy.solution.base.BaseActivity;
import com.elmoselhy.solution.commons.Validator;
import com.elmoselhy.solution.databinding.ActivityCorporationLoginBinding;
import com.elmoselhy.solution.viewmodels.CorporationViewModel;

public class CorporationLoginActivity extends BaseActivity {
    ActivityCorporationLoginBinding binding;
    CorporationViewModel corporationViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_corporation_login);
        initPage();
    }
    private void initPage() {
        setUpPageActions();
        setUpViewModel();
    }
    private void setUpPageActions() {
        binding.loginBtn.setOnClickListener(view -> {
            login();
        });
        binding.registerTv.setOnClickListener(view -> {
            startActivity(new Intent(this, CorporationRegisterActivity.class));
        });
    }
    private void setUpViewModel() {
        corporationViewModel = new ViewModelProvider(this).get(CorporationViewModel.class);
        corporationViewModel.setContext(this);
        setUpObserver();
    }
    private void login() {
        if (isFormHasErrors())
            return;
        corporationViewModel.login(binding.emailInput.getEditText().getText().toString(),binding.passwordInput.getEditText().getText().toString());
    }
    private void setUpObserver() {
        corporationViewModel.observeLogin().observe(this, result -> {
            startActivity(new Intent(this, MainActivity.class));
        });
        binding.setViewModel(corporationViewModel);
        binding.setLifecycleOwner(this);
    }
    private boolean isFormHasErrors() {
        binding.emailInput.setError(null);
        binding.passwordInput.setError(null);
        String email = binding.emailInput.getEditText().getText().toString();
        String password = binding.emailInput.getEditText().getText().toString();
        View focusView = null;
        boolean hasErrors = false;
        if (TextUtils.isEmpty(email)) {
            binding.emailInput.setError(getString(R.string.required));
            binding.emailInput.startAnimation(Validator.shakeError());
            focusView = binding.emailInput.getEditText();
            hasErrors = true;
        }else if (TextUtils.isEmpty(password)) {
            binding.passwordInput.setError(getString(R.string.required));
            binding.passwordInput.startAnimation(Validator.shakeError());
            focusView = binding.passwordInput.getEditText();
            hasErrors = true;
        }
        if (hasErrors) {
            focusView.requestFocus();
        }
        return hasErrors;
    }
}
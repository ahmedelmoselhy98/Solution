package com.elmoselhy.solution.ui.corporation;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.elmoselhy.solution.MainActivity;
import com.elmoselhy.solution.R;
import com.elmoselhy.solution.base.BaseActivity;
import com.elmoselhy.solution.commons.Utils;
import com.elmoselhy.solution.commons.Validator;
import com.elmoselhy.solution.databinding.ActivityCorporationRegisterBinding;
import com.elmoselhy.solution.model.response.Account;
import com.elmoselhy.solution.viewmodels.CorporationViewModel;
import com.nguyenhoanglam.imagepicker.model.Image;
import com.nguyenhoanglam.imagepicker.ui.imagepicker.ImagePicker;

public class CorporationRegisterActivity extends BaseActivity {
    ActivityCorporationRegisterBinding binding;
    CorporationViewModel corporationViewModel;
    Account user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_corporation_register);
        initPage();
    }

    private void initPage() {
        user = new Account();
        setUpPageActions();
        setUpViewModel();
    }

    private void setUpViewModel() {
        corporationViewModel = new ViewModelProvider(this).get(CorporationViewModel.class);
        corporationViewModel.setContext(this);
        setUpObserver();
    }

    private void setUpPageActions() {
        binding.profileIv.setOnClickListener(view -> {
            Utils.openImagesPicker(this);
        });
        binding.registerBtn.setOnClickListener(view -> {
            register();
        });
    }

    private void register() {
        if (isFormHasErrors())
            return;
        user.setName(binding.nameInput.getEditText().getText().toString());
        user.setDescription(binding.descriptionInput.getEditText().getText().toString());
        user.setEmail(binding.emailInput.getEditText().getText().toString());
        user.setPhone(binding.phoneInput.getEditText().getText().toString());
        corporationViewModel.corporationRegister(user,binding.passwordInput.getEditText().getText().toString());
    }

    private void setUpObserver() {
        corporationViewModel.observeRegister().observe(this, result -> {
            startActivity(new Intent(this, MainActivity.class));
        });
        corporationViewModel.observeImageUrl().observe(this, imageUrl -> {
            user.setImage(imageUrl);
            Utils.bindUser(binding.profileIv, imageUrl);
        });

        binding.setViewModel(corporationViewModel);
        binding.setLifecycleOwner(this);
    }


    private boolean isFormHasErrors() {
        binding.nameInput.setError(null);
        binding.emailInput.setError(null);
        binding.phoneInput.setError(null);
        binding.passwordInput.setError(null);
        binding.confirmPasswordInput.setError(null);
        String name = binding.nameInput.getEditText().getText().toString();
        String description = binding.descriptionInput.getEditText().getText().toString();
        String email = binding.emailInput.getEditText().getText().toString();
        String phone = binding.phoneInput.getEditText().getText().toString();
        String password = binding.passwordInput.getEditText().getText().toString();
        String confirmPassword = binding.passwordInput.getEditText().getText().toString();

        View focusView = null;
        boolean hasErrors = false;
        if (TextUtils.isEmpty(name)) {
            binding.nameInput.setError(getString(R.string.required));
            binding.nameInput.startAnimation(Validator.shakeError());
            focusView = binding.nameInput.getEditText();
            hasErrors = true;
        } else if (TextUtils.isEmpty(description)) {
            binding.descriptionInput.setError(getString(R.string.required));
            binding.descriptionInput.startAnimation(Validator.shakeError());
            focusView = binding.descriptionInput.getEditText();
            hasErrors = true;
        } else if (TextUtils.isEmpty(email)) {
            binding.emailInput.setError(getString(R.string.required));
            binding.emailInput.startAnimation(Validator.shakeError());
            focusView = binding.emailInput.getEditText();
            hasErrors = true;
        } else if (TextUtils.isEmpty(phone)) {
            binding.phoneInput.setError(getString(R.string.required));
            binding.phoneInput.startAnimation(Validator.shakeError());
            focusView = binding.phoneInput.getEditText();
            hasErrors = true;
        } else if (TextUtils.isEmpty(password)) {
            binding.passwordInput.setError(getString(R.string.required));
            binding.passwordInput.startAnimation(Validator.shakeError());
            focusView = binding.passwordInput.getEditText();
            hasErrors = true;
        } else if (TextUtils.isEmpty(confirmPassword)) {
            binding.confirmPasswordInput.setError(getString(R.string.required));
            binding.confirmPasswordInput.startAnimation(Validator.shakeError());
            focusView = binding.confirmPasswordInput.getEditText();
            hasErrors = true;
        } else if (!password.equals(confirmPassword)) {
            binding.passwordInput.setError(getString(R.string.dont_match));
            binding.passwordInput.startAnimation(Validator.shakeError());
            focusView = binding.passwordInput.getEditText();
            hasErrors = true;
        }
        if (hasErrors) {
            focusView.requestFocus();
        }
        return hasErrors;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (ImagePicker.shouldHandleResult(requestCode, resultCode, data, requestCode)) {
            // Get a list of picked images
            for (Image image : ImagePicker.getImages(data)) {
                Uri selectedFileUri = image.getUri();
                corporationViewModel.upLoadImage(CorporationRegisterActivity.this, selectedFileUri);
            }
        }
    }
}
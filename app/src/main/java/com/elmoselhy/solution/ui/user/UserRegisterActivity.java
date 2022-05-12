package com.elmoselhy.solution.ui.user;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.elmoselhy.solution.R;
import com.elmoselhy.solution.base.BaseActivity;
import com.elmoselhy.solution.commons.Utils;
import com.elmoselhy.solution.commons.Validator;
import com.elmoselhy.solution.databinding.ActivityUserRegisterBinding;
import com.elmoselhy.solution.model.response.Account;
import com.elmoselhy.solution.viewmodels.UserViewModel;
import com.nguyenhoanglam.imagepicker.model.Image;
import com.nguyenhoanglam.imagepicker.ui.imagepicker.ImagePicker;

public class UserRegisterActivity extends BaseActivity {
    ActivityUserRegisterBinding binding;
    UserViewModel userViewModel;
    Account user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_user_register);
        initPage();
    }

    private void initPage() {
        user = new Account();
        setUpPageActions();
        setUpViewModel();
    }

    private void setUpViewModel() {
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        userViewModel.setContext(this);
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
        user.setEmail(binding.emailInput.getEditText().getText().toString());
        user.setPhone(binding.phoneInput.getEditText().getText().toString());
        userViewModel.userRegister(user,binding.passwordInput.getEditText().getText().toString());
    }

    private void setUpObserver() {
        userViewModel.observeRegister().observe(this, result -> {
            startActivity(new Intent(this, UserHomeActivity.class));
        });
        userViewModel.observeImageUrl().observe(this, imageUrl -> {
            user.setImage(imageUrl);
            Utils.bindUser(binding.profileIv, imageUrl);
        });
        binding.setViewModel(userViewModel);
        binding.setLifecycleOwner(this);
    }


    private boolean isFormHasErrors() {
        binding.nameInput.setError(null);
        binding.emailInput.setError(null);
        binding.phoneInput.setError(null);
        binding.passwordInput.setError(null);
        binding.confirmPasswordInput.setError(null);
        String name = binding.nameInput.getEditText().getText().toString();
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
                userViewModel.upLoadUserImage(UserRegisterActivity.this, selectedFileUri);
            }
        }
    }
}
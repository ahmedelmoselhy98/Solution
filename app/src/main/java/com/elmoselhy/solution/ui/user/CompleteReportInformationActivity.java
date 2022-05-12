package com.elmoselhy.solution.ui.user;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.elmoselhy.solution.R;
import com.elmoselhy.solution.commons.Keys;
import com.elmoselhy.solution.commons.Utils;
import com.elmoselhy.solution.commons.Validator;
import com.elmoselhy.solution.databinding.ActivityCompleteReportInformationBinding;
import com.elmoselhy.solution.model.response.Report;
import com.elmoselhy.solution.viewmodels.UserViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.Gson;
import com.nguyenhoanglam.imagepicker.model.Image;
import com.nguyenhoanglam.imagepicker.ui.imagepicker.ImagePicker;

public class CompleteReportInformationActivity extends AppCompatActivity {
    ActivityCompleteReportInformationBinding binding;
    Report report;
    UserViewModel userViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_complete_report_information);
        initPage();
    }

    private void initPage() {
        report = new Gson().fromJson(getIntent().getStringExtra(Keys.IntentKeys.REPORT), Report.class);
        report.setUserId(FirebaseAuth.getInstance().getCurrentUser().getUid());
        setUpViewModel();
        binding.makeAReportBtn.setOnClickListener(view -> {
            makeReport();
        });
        setUpPageActions();
    }

    private void setUpViewModel() {
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        userViewModel.setContext(this);
        setUpObservers();
        binding.setViewModel(userViewModel);
        binding.setLifecycleOwner(this);
    }
    private void setUpObservers() {
        userViewModel.observeAddReport().observe(this,result -> {
            if (result){
                startActivity(new Intent(this,UserHomeActivity.class));
                finishAffinity();
            }
        });
        userViewModel.observeImageUrl().observe(this, imageUrl -> {
            report.setImage(imageUrl);
            Utils.bindUser(binding.reportIv, imageUrl);
        });
    }

    private void setUpPageActions() {
        binding.backIv.setOnClickListener(view -> {
            onBackPressed();
        });
        binding.reportIv.setOnClickListener(view -> {
            Utils.openImagesPicker(this);
        });
    }

    private void makeReport() {
        if (isFormHasErrors())
            return;
        report.setTitle(binding.titleInput.getEditText().getText().toString());
        report.setDescription(binding.descriptionInput.getEditText().getText().toString());
        userViewModel.addReportToDatabase(report);
    }

    private boolean isFormHasErrors() {
        binding.titleInput.setError(null);
        binding.descriptionInput.setError(null);
        String title = binding.titleInput.getEditText().getText().toString();
        String description = binding.descriptionInput.getEditText().getText().toString();
        View focusView = null;
        boolean hasErrors = false;
        if (TextUtils.isEmpty(title)) {
            binding.titleInput.setError(getString(R.string.required));
            binding.titleInput.startAnimation(Validator.shakeError());
            focusView = binding.titleInput.getEditText();
            hasErrors = true;
        }else if (TextUtils.isEmpty(description)) {
            binding.descriptionInput.setError(getString(R.string.required));
            binding.descriptionInput.startAnimation(Validator.shakeError());
            focusView = binding.descriptionInput.getEditText();
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
                userViewModel.upLoadReportImage(CompleteReportInformationActivity.this, selectedFileUri);
            }
        }
    }
}
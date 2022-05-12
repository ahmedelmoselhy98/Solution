package com.elmoselhy.solution.viewmodels;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.elmoselhy.solution.R;
import com.elmoselhy.solution.base.BaseViewModel;
import com.elmoselhy.solution.model.response.Account;
import com.elmoselhy.solution.model.response.Report;
import com.elmoselhy.solution.repositories.AppRepository;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.List;

public class UserViewModel extends BaseViewModel {
    AppRepository appRepository = new AppRepository();
    Context context;

    public void setContext(Context context) {
        this.context = context;
    }

    private MutableLiveData<Boolean> registerLiveData = new MutableLiveData<>();
    private MutableLiveData<Boolean> loginLiveData = new MutableLiveData<>();
    private MutableLiveData<Boolean> addUserSuccessLiveData = new MutableLiveData<>();
    private MutableLiveData<Boolean> addReportLiveData = new MutableLiveData<>();
    private MutableLiveData<Account> getUserLiveData = new MutableLiveData<>();
    private MutableLiveData<List<Account>> getCorporationListLiveData = new MutableLiveData<>();
    private MutableLiveData<List<Report>> getReportListLiveData = new MutableLiveData<>();
    private MutableLiveData<String> imageUrlLiveData = new MutableLiveData<>();

    public LiveData<Boolean> observeLogin() {
        return loginLiveData;
    }
    public LiveData<Boolean> observeRegister() {
        return registerLiveData;
    }
    public LiveData<Boolean> observeAddReport() {
        return addReportLiveData;
    }
    public LiveData<List<Account>> observeCorporationList() {
        return getCorporationListLiveData;
    }
    public LiveData<List<Report>> observeReportList() {
        return getReportListLiveData;
    }
    public LiveData<Account> observeGetUser() {
        return getUserLiveData;
    }
    public LiveData<String> observeImageUrl() {
        return imageUrlLiveData;
    }
    public void logout() {
        appRepository.auth().signOut();
    }
    public void login(String email, String password) {
        loading.setValue(true);
        appRepository.auth().signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    loading.setValue(false);
                    if (task.isComplete() && task.isSuccessful()) {
                        getUserFromDatabase(task.getResult().getUser().getUid());
                        getUserLiveData.observe((LifecycleOwner) context, result -> {
                            Toast.makeText(context, context.getString(R.string.success), Toast.LENGTH_SHORT).show();
                            loginLiveData.setValue(true);
                        });
                    } else {
                        Toast.makeText(context, context.getString(R.string.error_In_Login), Toast.LENGTH_SHORT).show();
                    }
                });
    }
    public void userRegister(Account user, String password) {
        loading.setValue(true);
        appRepository.auth().createUserWithEmailAndPassword(user.getEmail(), password)
                .addOnCompleteListener(task -> {
                    loading.setValue(false);
                    if (task.isComplete() && task.isSuccessful()) {
                        user.setId(task.getResult().getUser().getUid());
                        addUserToDatabase(user);
                        addUserSuccessLiveData.observe((LifecycleOwner) context, result -> {
                            Toast.makeText(context, context.getString(R.string.success), Toast.LENGTH_SHORT).show();
                            registerLiveData.setValue(true);
                        });
                    } else {
                        Toast.makeText(context, context.getString(R.string.error_In_Register), Toast.LENGTH_SHORT).show();
                    }
                });
    }
    public void addUserToDatabase(Account user) {
        loading.setValue(true);
        appRepository.usersRef().child(user.getId()).setValue(user)
                .addOnCompleteListener(task -> {
                    loading.setValue(false);
                    if (task.isComplete() && task.isSuccessful()) {
                        addUserSuccessLiveData.setValue(true);
                    } else {
                        Toast.makeText(context, context.getString(R.string.error_In_Register), Toast.LENGTH_SHORT).show();
                    }
                });
    }
    public void addReportToDatabase(Report report) {
        loading.setValue(true);
        report.setId(appRepository.reportsRef().push().getKey());
        appRepository.reportsRef().child(report.getId()).setValue(report)
                .addOnCompleteListener(task -> {
                    loading.setValue(false);
                    if (task.isComplete() && task.isSuccessful()) {
                        addReportLiveData.setValue(true);
                        Toast.makeText(context, context.getString(R.string.success), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, context.getString(R.string.error_In_add_report), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void getUserFromDatabase(String id) {
        loading.setValue(true);
        appRepository.usersRef().child(id).get()
                .addOnCompleteListener(task -> {
                    loading.setValue(false);
                    if (task.isComplete() && task.isSuccessful()) {
                        Account user = task.getResult().getValue(Account.class);
                        getUserLiveData.setValue(user);
                    } else {
                        Toast.makeText(context, context.getString(R.string.error_In_Register), Toast.LENGTH_SHORT).show();
                    }
                });
    }
    public void getCorporationListDatabase() {
        loading.setValue(true);
        appRepository.corporationRef().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                loading.setValue(false);
                if (snapshot.exists()){
                    ArrayList<Account> corporations = new ArrayList<>();
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        corporations.add(dataSnapshot.getValue(Account.class));
                    }
                    getCorporationListLiveData.setValue(corporations);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void getReportListDatabase() {
        loading.setValue(true);
        appRepository.reportsRef().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                loading.setValue(false);
                if (snapshot.exists()){
                    ArrayList<Report> reports = new ArrayList<>();
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        reports.add(dataSnapshot.getValue(Report.class));
                    }
                    getReportListLiveData.setValue(reports);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void upLoadUserImage(Context context, Uri uri) {
        loading.setValue(true);
        String imageName = System.currentTimeMillis() + ".jpg";
        appRepository.usersStorageRef().child(imageName).putFile(uri).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }

                // Continue with the task to get the download URL
                return appRepository.usersStorageRef().child(imageName).getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                loading.setValue(false);
                if (task.isSuccessful()) {
                    Uri downloadUri = task.getResult();
                    imageUrlLiveData.setValue(downloadUri.toString());
                    Log.e("upLoadImage: ", downloadUri.toString());
                } else {
                    // Handle failures
                    // ...
                    Log.e("upLoadImage: ", task.getException().getMessage());
                }
            }
        });
    }
    public void upLoadReportImage(Context context, Uri uri) {
        loading.setValue(true);
        String imageName = System.currentTimeMillis() + ".jpg";
        appRepository.reportsStorageRef().child(imageName).putFile(uri).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }

                // Continue with the task to get the download URL
                return appRepository.reportsStorageRef().child(imageName).getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                loading.setValue(false);
                if (task.isSuccessful()) {
                    Uri downloadUri = task.getResult();
                    imageUrlLiveData.setValue(downloadUri.toString());
                    Log.e("upLoadImage: ", downloadUri.toString());
                } else {
                    // Handle failures
                    // ...
                    Log.e("upLoadImage: ", task.getException().getMessage());
                }
            }
        });
    }

}

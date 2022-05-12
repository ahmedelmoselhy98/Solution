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
import com.elmoselhy.solution.repositories.CorporationRepository;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.UploadTask;

public class CorporationViewModel extends BaseViewModel {
    CorporationRepository corporationRepository = new CorporationRepository();
    Context context;

    public void setContext(Context context) {
        this.context = context;
    }

    private MutableLiveData<Boolean> registerLiveData = new MutableLiveData<>();
    private MutableLiveData<Boolean> loginLiveData = new MutableLiveData<>();
    private MutableLiveData<Boolean> addCorporationSuccessLiveData = new MutableLiveData<>();
    private MutableLiveData<Account> getCorporationLiveData = new MutableLiveData<>();
    private MutableLiveData<String> imageUrlLiveData = new MutableLiveData<>();

    public LiveData<Boolean> observeLogin() {
        return loginLiveData;
    }
    public LiveData<Boolean> observeRegister() {
        return registerLiveData;
    }
    public LiveData<Account> observeGetCorporation() {
        return getCorporationLiveData;
    }
    public LiveData<String> observeImageUrl() {
        return imageUrlLiveData;
    }
    public void logout() {
        corporationRepository.auth().signOut();
    }
    public void login(String email, String password) {
        loading.setValue(true);
        corporationRepository.auth().signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    loading.setValue(false);
                    if (task.isComplete() && task.isSuccessful()) {
                        getCorporationToDatabase(task.getResult().getUser().getUid());
                        getCorporationLiveData.observe((LifecycleOwner) context, result -> {
                            loginLiveData.setValue(true);
                        });
                    } else {
                        Toast.makeText(context, context.getString(R.string.error_In_Login), Toast.LENGTH_SHORT).show();
                    }
                });
    }
    public void corporationRegister(Account user, String password) {
        loading.setValue(true);
        corporationRepository.auth().createUserWithEmailAndPassword(user.getEmail(), password)
                .addOnCompleteListener(task -> {
                    loading.setValue(false);
                    if (task.isComplete() && task.isSuccessful()) {
                        user.setId(task.getResult().getUser().getUid());
                        addCorporationToDatabase(user);
                        addCorporationSuccessLiveData.observe((LifecycleOwner) context, result -> {
                            registerLiveData.setValue(true);
                        });
                    } else {
                        Toast.makeText(context, context.getString(R.string.error_In_Register), Toast.LENGTH_SHORT).show();
                    }
                });
    }
    public void addCorporationToDatabase(Account user) {
        loading.setValue(true);
        corporationRepository.corporationRef().child(user.getId()).setValue(user)
                .addOnCompleteListener(task -> {
                    loading.setValue(false);
                    if (task.isComplete() && task.isSuccessful()) {
                        addCorporationSuccessLiveData.setValue(true);
                    } else {
                        Toast.makeText(context, context.getString(R.string.error_In_Register), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void getCorporationToDatabase(String id) {
        loading.setValue(true);
        corporationRepository.corporationRef().child(id).get()
                .addOnCompleteListener(task -> {
                    loading.setValue(false);
                    if (task.isComplete() && task.isSuccessful()) {
                        Account user = task.getResult().getValue(Account.class);
                        getCorporationLiveData.setValue(user);
                    } else {
                        Toast.makeText(context, context.getString(R.string.error_In_Register), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    //
    public void upLoadImage(Context context, Uri uri) {
        loading.setValue(true);
        String imageName = System.currentTimeMillis() + ".jpg";
        corporationRepository.corporationStorageRef().child(imageName).putFile(uri).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }

                // Continue with the task to get the download URL
                return corporationRepository.corporationStorageRef().child(imageName).getDownloadUrl();
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

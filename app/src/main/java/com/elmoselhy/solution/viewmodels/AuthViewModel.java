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
import com.elmoselhy.solution.model.response.User;
import com.elmoselhy.solution.repositories.AuthRepository;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.UploadTask;

public class AuthViewModel extends BaseViewModel {
    AuthRepository authRepository = new AuthRepository();
    Context context;

    public void setContext(Context context) {
        this.context = context;
    }

    private MutableLiveData<Boolean> registerLiveData = new MutableLiveData<>();
    private MutableLiveData<Boolean> loginLiveData = new MutableLiveData<>();
    private MutableLiveData<Boolean> addUserSuccessLiveData = new MutableLiveData<>();
    private MutableLiveData<User> getUserLiveData = new MutableLiveData<>();
    private MutableLiveData<String> imageUrlLiveData = new MutableLiveData<>();

    public LiveData<Boolean> observeLogin() {
        return loginLiveData;
    }
    public LiveData<Boolean> observeRegister() {
        return registerLiveData;
    }
    public LiveData<User> observeGetUser() {
        return getUserLiveData;
    }
    public LiveData<String> observeImageUrl() {
        return imageUrlLiveData;
    }
    public void logout() {
        authRepository.auth().signOut();
    }
    public void login(String email, String password) {
        authRepository.auth().signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isComplete() && task.isSuccessful()) {
                        getUserToDatabase(task.getResult().getUser().getUid());
                        getUserLiveData.observe((LifecycleOwner) context, result -> {
                            loginLiveData.setValue(true);
                        });
                    } else {
                        Toast.makeText(context, context.getString(R.string.error_In_Login), Toast.LENGTH_SHORT).show();
                    }
                });
    }
    public void userRegister(User user, String password) {
        authRepository.auth().createUserWithEmailAndPassword(user.getEmail(), password)
                .addOnCompleteListener(task -> {
                    if (task.isComplete() && task.isSuccessful()) {
                        user.setId(task.getResult().getUser().getUid());
                        addUserToDatabase(user);
                        addUserSuccessLiveData.observe((LifecycleOwner) context, result -> {
                            registerLiveData.setValue(true);
                        });

                    } else {
                        Toast.makeText(context, context.getString(R.string.error_In_Register), Toast.LENGTH_SHORT).show();
                    }
                });
    }
    public void addUserToDatabase(User user) {
        authRepository.usersRef().child(user.getId()).setValue(user)
                .addOnCompleteListener(task -> {
                    if (task.isComplete() && task.isSuccessful()) {
                        addUserSuccessLiveData.setValue(true);
                    } else {
                        Toast.makeText(context, context.getString(R.string.error_In_Register), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void getUserToDatabase(String id) {
        authRepository.usersRef().child(id).get()
                .addOnCompleteListener(task -> {
                    if (task.isComplete() && task.isSuccessful()) {
                        User user = task.getResult().getValue(User.class);
                        getUserLiveData.setValue(user);
                    } else {
                        Toast.makeText(context, context.getString(R.string.error_In_Register), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    //
    public void upLoadImage(Context context, Uri uri) {
        loading.setValue(true);
        String imageName = System.currentTimeMillis() + ".jpg";
        authRepository.usersStorageRef().child(imageName).putFile(uri).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }

                // Continue with the task to get the download URL
                return authRepository.usersStorageRef().child(imageName).getDownloadUrl();
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

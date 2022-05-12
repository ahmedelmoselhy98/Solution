package com.elmoselhy.solution.repositories;

import android.content.Context;

import com.elmoselhy.solution.commons.Keys;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class AppRepository {


    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;
    StorageReference storageReference;

    public AppRepository() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = database.getReference();
        storageReference = FirebaseStorage.getInstance().getReference();

    }

    public FirebaseAuth auth() {
        return firebaseAuth;
    }

    public DatabaseReference usersRef() {
        return databaseReference.child(Keys.USERS);
    }

    public StorageReference usersStorageRef() {
        return storageReference.child(Keys.STORAGE_USERS);
    }

    public StorageReference reportsStorageRef() {
        return storageReference.child(Keys.STORAGE_REPORT);
    }

    public DatabaseReference corporationRef() {
        return databaseReference.child(Keys.CORPORATION);
    }

    public StorageReference corporationStorageRef() {
        return storageReference.child(Keys.STORAGE_CORPORATION);
    }

    public DatabaseReference reportsRef() {
        return databaseReference.child(Keys.REPORTS);
    }

}

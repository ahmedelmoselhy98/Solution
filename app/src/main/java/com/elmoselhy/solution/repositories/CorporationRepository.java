package com.elmoselhy.solution.repositories;

import com.elmoselhy.solution.commons.Keys;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class CorporationRepository {


    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;
    StorageReference storageReference;

    public CorporationRepository() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = database.getReference();
        storageReference = FirebaseStorage.getInstance().getReference();

    }

    public FirebaseAuth auth() {
        return firebaseAuth;
    }
    public DatabaseReference corporationRef() {
        return databaseReference.child(Keys.CORPORATION);
    }

    public StorageReference corporationStorageRef() {
        return storageReference.child(Keys.STORAGE_CORPORATION);
    }

}

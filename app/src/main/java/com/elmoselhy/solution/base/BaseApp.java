package com.elmoselhy.solution.base;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;

public class BaseApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
}

package com.elmoselhy.solution.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.elmoselhy.solution.R;
import com.elmoselhy.solution.base.BaseActivity;
import com.elmoselhy.solution.ui.user.UserHomeActivity;
import com.google.firebase.auth.FirebaseAuth;

public class SplashActivity extends BaseActivity {

    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        initPage();
    }
    private void initPage() {
        firebaseAuth = FirebaseAuth.getInstance();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (firebaseAuth.getCurrentUser() != null) {
                    startActivity(new Intent(SplashActivity.this, UserHomeActivity.class));
                } else
                    startActivity(new Intent(SplashActivity.this, ChooseClientTypeActivity.class));
            finish();
            }
        }, 2000);
    }
}
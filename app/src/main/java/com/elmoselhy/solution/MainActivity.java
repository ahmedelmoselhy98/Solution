package com.elmoselhy.solution;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import com.elmoselhy.solution.R;
import com.elmoselhy.solution.base.BaseActivity;

public class MainActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
package com.elmoselhy.solution.ui.user;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;

import com.elmoselhy.solution.R;
import com.elmoselhy.solution.base.BaseActivity;
import com.elmoselhy.solution.commons.Keys;
import com.elmoselhy.solution.databinding.ActivityConfirmLocationBinding;
import com.elmoselhy.solution.model.response.Report;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;

import java.security.Key;

public class ConfirmLocationActivity extends BaseActivity implements OnMapReadyCallback {


    ActivityConfirmLocationBinding binding;
    private GoogleMap mMap;
    Report report;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_confirm_location);
        initPage();
    }

    private void initPage() {
        report = new Gson().fromJson(getIntent().getStringExtra(Keys.IntentKeys.REPORT), Report.class);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        setUpPageActions();
    }

    private void setUpPageActions() {
        binding.confirmBtn.setOnClickListener(view -> {
            report.setLatitude(30.5503);
            report.setLongitude(31.0106);
            startActivity(new Intent(this, CompleteReportInformationActivity.class)
                    .putExtra(Keys.IntentKeys.REPORT,new Gson().toJson(report)));

        });
        binding.backIv.setOnClickListener(view -> {
            onBackPressed();
        });
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        // Add a marker in Sydney and move the camera
        LatLng location = new LatLng(30.5503, 31.0106);
        mMap.addMarker(new MarkerOptions()
                .position(location)
                .title("Marker in Shebin"));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location, 15f));
    }
}
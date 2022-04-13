package com.elmoselhy.solution.ui.user;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;

import com.elmoselhy.solution.R;
import com.elmoselhy.solution.base.BaseActivity;
import com.elmoselhy.solution.databinding.ActivityConfirmLocationBinding;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class ConfirmLocationActivity extends BaseActivity implements OnMapReadyCallback {


    ActivityConfirmLocationBinding binding;
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_confirm_location);
        initPage();
    }

    private void initPage() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        setUpPageActions();
    }

    private void setUpPageActions() {
            binding.confirmBtn.setOnClickListener(view -> {
                startActivity(new Intent(this,CompleteReportInformationActivity.class));
            });
        binding.backIv.setOnClickListener(view -> {
            onBackPressed();
        });
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(30.5503, 31.0106);
        mMap.addMarker(new MarkerOptions()
                .position(sydney)
                .title("Marker in Shebin"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
}
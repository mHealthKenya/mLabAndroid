package com.example.kenweezy.mytablayouts.eidvl.remotelogin.remoteOptions.ButtonOptions;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;

import com.example.kenweezy.mytablayouts.FragmentBodaFound;
import com.example.kenweezy.mytablayouts.FragmentSampleDelivered;
import com.example.kenweezy.mytablayouts.R;
import com.example.kenweezy.mytablayouts.eidvl.eidvlOptions;
import com.example.kenweezy.mytablayouts.eidvl.remotelogin.VleidSampleRemoteLogin;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class SampleTransportation extends AppCompatActivity implements OnMapReadyCallback{

    private  GoogleMap mMap;
    public SampleTransportation() {
        super(R.layout.sample_transportation);
    }
    private Button cancelBtn;

    private TextView labName;
    String health_facility_name;
    double health_facility_latitude, health_facility_longitude;
    double lab_latitude, lab_longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sample_transportation);
        setToolBar();
        changeStatusBarColor("#3F51B5");

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.fragment2, FragmentBodaFound.class, null)
                    .commit();


        }


    }

    private void changeStatusBarColor(String color){
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.parseColor(color));
        }
    }

    public void setToolBar(){

        try{

            Toolbar toolbar = (Toolbar) findViewById(R.id.sampletransportationtoolbar);
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle("EID/VL Sample Transportation");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);

            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getApplicationContext(),VleidSampleRemoteLogin.class));
                    finish();
                }
            });

        }
        catch(Exception e){


        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34.0, 151.0);
        LatLng newyork = new LatLng(40.7128, -74.0060);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.addMarker(new MarkerOptions().position(newyork).title("Marker in NewYork"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(newyork, 15f));

    }

    public void GoToCheckRejectedSamples(View v){

        try{
            setContentView(R.layout.sample_transportation);
        }
        catch(Exception e){
        }
    }


}
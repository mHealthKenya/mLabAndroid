package com.example.kenweezy.mytablayouts.eidvl.remotelogin.remoteOptions.ButtonOptions;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.kenweezy.mytablayouts.R;
import com.example.kenweezy.mytablayouts.eidvl.eidvlOptions;
import com.example.kenweezy.mytablayouts.eidvl.remotelogin.VleidSampleRemoteLogin;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;



public class SampleTransportation extends AppCompatActivity implements OnMapReadyCallback{

    private TextView labName;
    String health_facility_name;
    double health_facility_latitude, health_facility_longitude;
    double lab_latitude, lab_longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sample_transportation);
        setToolBar();
        getLocation();
    }




    public void getLocation(){
        Bundle bundle=getIntent().getExtras();
        String selectedLab=bundle.getString("selectedLab");
        labName = (TextView)findViewById(R.id.bodaFound);
        labName.setText("Found Boda to "+selectedLab.toString());
        health_facility_latitude = -1.06042;
        health_facility_longitude = 34.47627;
        health_facility_name = "Migori District Hospital";

        if(selectedLab == "Kemri Nairobi"){
            lab_latitude = 0.016037354681606393;
            lab_latitude = 34.73013014734319;
        }


    }

    public void setToolBar(){

        try{

            Toolbar toolbar = (Toolbar) findViewById(R.id.eidvlremotelogintoolbar);
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle("EID/VL Sample Transportation");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);

        }
        catch(Exception e){


        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        //mMap = googleMap;
        googleMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
        googleMap.getUiSettings().setZoomControlsEnabled(true);
        googleMap.getUiSettings().setZoomGesturesEnabled(true);
        googleMap.getUiSettings().setCompassEnabled(true);
        LatLng sydney = new LatLng(0.016037354681606393, 34.73013014734319);
        googleMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        //Toast.makeText(this, "Map shown", Toast.LENGTH_LONG).show();

        // Toast.makeText(Context context.this, " ", Toast.LENGTH_LONG).show();
    }

    public void GoToCheckRejectedSamples(View v){

        try{



            setContentView(R.layout.sample_transportation);

        }
        catch(Exception e){


        }
    }
}
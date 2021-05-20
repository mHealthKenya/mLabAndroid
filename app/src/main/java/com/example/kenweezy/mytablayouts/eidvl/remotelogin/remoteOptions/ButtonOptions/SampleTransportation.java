package com.example.kenweezy.mytablayouts.eidvl.remotelogin.remoteOptions.ButtonOptions;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import com.example.kenweezy.mytablayouts.R;
import com.example.kenweezy.mytablayouts.eidvl.remotelogin.VleidSampleRemoteLogin;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by KenMusembi
 * throughout many a day in may 2021
 * this activity holds the logic for transitioning between the
 * boda found and delivery status fragments.
 */

public class SampleTransportation extends AppCompatActivity {
    public SampleTransportation() {
        super(R.layout.sample_transportation);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sample_transportation);
        //setTool() sets the toolbar, initiating certain attributes like title
        setToolBar();
        changeStatusBarColor("#3F51B5");

        //we check if there is any fragment in savedinstance
        if (savedInstanceState == null) {
            //if there is no fragment, we set fragment2 on top of the stack and commit
            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    //fragment2 is FragmentFoundBoda with map activity
                    .add(R.id.fragment2, FragmentBodaFound.class, null)
                    .commit();
        }
    }

    private void changeStatusBarColor(String color) {
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.parseColor(color));
        }
    }

    public void setToolBar() {
        try
        {
            Toolbar toolbar = (Toolbar) findViewById(R.id.sampletransportationtoolbar);
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle("EID/VL Sample Transportation");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);

            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getApplicationContext(), VleidSampleRemoteLogin.class));
                    finish();
                }
            });

        } catch (Exception e) {


        }
    }
}
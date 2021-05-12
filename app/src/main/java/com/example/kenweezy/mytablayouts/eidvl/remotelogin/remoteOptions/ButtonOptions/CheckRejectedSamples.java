package com.example.kenweezy.mytablayouts.eidvl.remotelogin.remoteOptions.ButtonOptions;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;

import com.example.kenweezy.mytablayouts.R;

public class CheckRejectedSamples extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.check_rejected_samples);
        setToolBar();
    }

    public void setToolBar(){

        try{

            Toolbar toolbar = (Toolbar) findViewById(R.id.eidvlremotelogintoolbar);
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle("EID/VL Rejected Samples");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);

        }
        catch(Exception e){


        }
    }
}
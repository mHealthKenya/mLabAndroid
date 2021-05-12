package com.example.kenweezy.mytablayouts.eidvl.remotelogin.remoteOptions.ButtonOptions;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.widget.TextView;

import com.example.kenweezy.mytablayouts.R;

public class SampleTransportationStatus extends AppCompatActivity {

    private TextView labName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.sample_transportation_status);
        setToolBar();

    }
    public void setToolBar(){
        Bundle bundle=getIntent().getExtras();
        String selectedLab=bundle.getString("selectedLab");
        labName = (TextView)findViewById(R.id.sampleDelivered);
        labName.setText("Sample Delivered to "+selectedLab.toString());

        try{

            Toolbar toolbar = (Toolbar) findViewById(R.id.eidvlremotelogintoolbar);
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle("EID/VL Sample Transportation Status");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);

        }
        catch(Exception e){


        }
    }

}
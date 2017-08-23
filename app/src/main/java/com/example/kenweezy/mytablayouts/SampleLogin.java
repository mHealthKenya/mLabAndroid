package com.example.kenweezy.mytablayouts;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

/**
 * Created by kennedy on 8/21/17.
 */

public class SampleLogin extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.samplelogin);
        setToolBar();
    }


    public void setToolBar(){

        try{

            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_sample);
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle("Sample Remote Login");
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        catch(Exception e){


        }
    }
}

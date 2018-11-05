package com.example.kenweezy.mytablayouts.AddClient;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.example.kenweezy.mytablayouts.R;

public class ClientOptions extends AppCompatActivity {

    private Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.client_options);
        setToolbar();
    }

    public void setToolbar(){

        try{

            toolbar = (Toolbar) findViewById(R.id.toolbarclientoptons);
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle("Client Options");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
        }
        catch(Exception e){


        }
    }

    public void RegisterClient(View v){

        try{

            Intent myint=new Intent(getApplicationContext(),Register.class);
            startActivity(myint);

//            Toast.makeText(this, "register client", Toast.LENGTH_SHORT).show();
        }
        catch(Exception e){


        }
    }

    public void IndividualResults(View v){
        try{

            Intent myint=new Intent(getApplicationContext(),Individualresults.class);
            startActivity(myint);

        }
        catch(Exception e){

        }
    }
}

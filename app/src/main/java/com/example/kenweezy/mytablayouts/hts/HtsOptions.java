package com.example.kenweezy.mytablayouts.hts;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.kenweezy.mytablayouts.R;
import com.example.kenweezy.mytablayouts.hts.Htssampleremote.HtsSampleRemoteLogin;

public class HtsOptions extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hts_options);

        setToolBar();
        changeStatusBarColor("#3F51B5");
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

            Toolbar toolbar = (Toolbar) findViewById(R.id.htsoptionstoolbar);
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle("Hts Options");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);

        }
        catch(Exception e){


        }
    }

    public void GoToHtsResults(View v){

        try{

            Intent myint=new Intent(getApplicationContext(), HtsresultsTab.class);
            startActivity(myint);


        }
        catch(Exception e){


        }
    }

    public void GoToHtsSampleRemoteLogin(View v){

        try{

            Intent myint=new Intent(getApplicationContext(), HtsSampleRemoteLogin.class);
            startActivity(myint);


//            Toast.makeText(this, "work in progress", Toast.LENGTH_SHORT).show();

        }
        catch(Exception e){


        }
    }
}

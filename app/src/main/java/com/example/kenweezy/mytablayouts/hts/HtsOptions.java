package com.example.kenweezy.mytablayouts.hts;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.example.kenweezy.mytablayouts.Config.Config;
import com.example.kenweezy.mytablayouts.R;
import com.example.kenweezy.mytablayouts.hts.Htssampleremote.HtsSampleRemoteLogin;

public class HtsOptions extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hts_options);

        setToolBar();
        changeStatusBarColor();
    }

    private void changeStatusBarColor(){
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.parseColor(Config.statusBarColor));
        }
    }

    public void setToolBar(){

        try{

            Toolbar toolbar = (Toolbar) findViewById(R.id.htsoptionstoolbar);
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle("HTS Options");
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

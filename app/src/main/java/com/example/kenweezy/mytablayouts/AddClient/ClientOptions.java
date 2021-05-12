package com.example.kenweezy.mytablayouts.AddClient;

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

public class ClientOptions extends AppCompatActivity {

    private Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.client_options);
        setToolbar();
        changeStatusBarColor();
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

    private void changeStatusBarColor(){
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.parseColor(Config.statusBarColor));
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

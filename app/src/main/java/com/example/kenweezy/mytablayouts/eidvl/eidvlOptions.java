package com.example.kenweezy.mytablayouts.eidvl;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.kenweezy.mytablayouts.MainActivity;
import com.example.kenweezy.mytablayouts.R;
import com.example.kenweezy.mytablayouts.eidvl.remotelogin.VleidSampleRemoteLogin;

public class eidvlOptions extends AppCompatActivity {

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.eidvl_options);

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

            Toolbar toolbar = (Toolbar) findViewById(R.id.eidvloptionstoolbar);
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle("EID/VL Options");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);

        }
        catch(Exception e){


        }
    }

    public void GoToEidvlResults(View v){

        try{

            new LongOperation().execute();


        }
        catch(Exception e){


        }
    }



    private class LongOperation extends AsyncTask<String, Void, String>
    {
        protected void onPreExecute()
        {
            try{

                progressDialog = new ProgressDialog(eidvlOptions.this);
                progressDialog.setTitle("Getting Results...");
                progressDialog.setMessage("Please wait...");
                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressDialog.setMax(100);
                progressDialog.setProgress(0);
                progressDialog.setCancelable(false);
                progressDialog.show();


            }
            catch(Exception e){

                Toast.makeText(eidvlOptions.this, "error loading progress dialog, try again", Toast.LENGTH_SHORT).show();

            }


        }

        protected String doInBackground(String... params)
        {
            try
            {
                Intent inty=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(inty);
            }
            catch (Exception e) {
                Toast.makeText(eidvlOptions.this, "error getting results, try again", Toast.LENGTH_SHORT).show();
//                System.out.print(""+e.printStackTrace());
            }
            return null;
        }
        protected void onPostExecute(String result)
        {

            progressDialog.dismiss();


        }
    }

    public void GoToEidvlSampleRemoteLogin(View v){

        try{



            Intent myint=new Intent(eidvlOptions.this, VleidSampleRemoteLogin.class);
            startActivity(myint);

//            Toast.makeText(this, "work in progress", Toast.LENGTH_SHORT).show();

        }
        catch(Exception e){


        }
    }
}

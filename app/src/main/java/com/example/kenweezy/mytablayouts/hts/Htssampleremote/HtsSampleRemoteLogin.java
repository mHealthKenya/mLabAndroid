package com.example.kenweezy.mytablayouts.hts.Htssampleremote;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.kenweezy.mytablayouts.Config.Config;
import com.example.kenweezy.mytablayouts.R;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

public class HtsSampleRemoteLogin extends AppCompatActivity {

    MaterialBetterSpinner SpinnerSex,SpinnerdeliveryPoint,Spinnertestkit1,Spinnertestkit2,Spinnerfinalresult;
    String SelectedSex,SelecteddeliveryPoint,Selectedtestkit1,Selectedtestkit2,Selectedfinalresult;
    private ArrayAdapter<String> arrayAdapterSex,arrayAdapterDeliveryPoint,arrayAdapterTestkit1,arrayAdapterTestkit2,arrayAdapterFinalResult;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hts_sample_remote_login);
        setToolBar();
        changeStatusBarColor("#3F51B5");

        initialise();
        setSpinnerAdapters();
        setSpinnerListeners();
    }

    public void CancelHtsSamples(View v){

        try{

            Toast.makeText(this, "cancelling", Toast.LENGTH_SHORT).show();
        }
        catch(Exception e){


        }
    }

    public void SubmitHtsSample(View v){

        try{

            Toast.makeText(this, "submitting", Toast.LENGTH_SHORT).show();
        }
        catch(Exception e){


        }
    }



    private void setSpinnerListeners(){

        try{

            setSpinnerDeliveryPointListener();
            setSpinnerFinalresultListener();
            setSpinnerSexListener();
            setSpinnerTestkit1Listener();
            setSpinnerTestkit2Listener();
        }
        catch(Exception e){

            Toast.makeText(this, "error setting adapters", Toast.LENGTH_SHORT).show();
        }
    }

    private void initialise(){

        try{

            SpinnerSex=(MaterialBetterSpinner) findViewById(R.id.htssamplesex);
            SpinnerdeliveryPoint=(MaterialBetterSpinner) findViewById(R.id.htssampleservicedeliverypoint);
            Spinnertestkit1=(MaterialBetterSpinner) findViewById(R.id.htssampletestkit1);
            Spinnertestkit2=(MaterialBetterSpinner) findViewById(R.id.htssampletestkit2);
            Spinnerfinalresult=(MaterialBetterSpinner) findViewById(R.id.htssamplefinalresult);

            SelectedSex="";
            SelecteddeliveryPoint="";
            Selectedtestkit1="";
            Selectedtestkit2="";
            Selectedfinalresult="";

            arrayAdapterSex = new ArrayAdapter<String>(this,
                    android.R.layout.simple_dropdown_item_1line, Config.SPINNERLISTSEX);
            arrayAdapterDeliveryPoint = new ArrayAdapter<String>(this,
                    android.R.layout.simple_dropdown_item_1line, Config.SPINNERLISTSEX);
            arrayAdapterTestkit1 = new ArrayAdapter<String>(this,
                    android.R.layout.simple_dropdown_item_1line, Config.SPINNERLISTSEX);
            arrayAdapterTestkit2 = new ArrayAdapter<String>(this,
                    android.R.layout.simple_dropdown_item_1line, Config.SPINNERLISTSEX);
            arrayAdapterFinalResult = new ArrayAdapter<String>(this,
                    android.R.layout.simple_dropdown_item_1line, Config.SPINNERLISTSEX);

        }
        catch(Exception e){


        }
    }

    public void setSpinnerAdapters(){

        try{
            SpinnerSex.setAdapter(arrayAdapterSex);
            SpinnerdeliveryPoint.setAdapter(arrayAdapterDeliveryPoint);
            Spinnertestkit1.setAdapter(arrayAdapterTestkit1);
            Spinnertestkit2.setAdapter(arrayAdapterTestkit2);
            Spinnerfinalresult.setAdapter(arrayAdapterFinalResult);


        }
        catch(Exception e){


            Toast.makeText(this, "error setting adapters", Toast.LENGTH_SHORT).show();
        }
    }


    private void changeStatusBarColor(String color){
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.parseColor(color));
        }
    }

    private void setToolBar(){

        try{

            Toolbar toolbar = (Toolbar) findViewById(R.id.htssampletoolbar);
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle("Hts sample remote login");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);

        }
        catch(Exception e){


        }
    }



    //start set spinner listeners

    private void setSpinnerSexListener(){

        try{


            SpinnerSex.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {

                    SelectedSex = SpinnerSex.getText().toString();

//                    Toast.makeText(Report.this, "selected "+selectedWhere, Toast.LENGTH_SHORT).show();



                }
            });

        }
        catch(Exception e){


        }
    }


    private void setSpinnerDeliveryPointListener(){

        try{


            SpinnerdeliveryPoint.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {

                    SelecteddeliveryPoint = SpinnerdeliveryPoint.getText().toString();

//                    Toast.makeText(Report.this, "selected "+selectedWhere, Toast.LENGTH_SHORT).show();



                }
            });

        }
        catch(Exception e){


        }
    }



    private void setSpinnerTestkit1Listener(){

        try{


            Spinnertestkit1.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {

                    Selectedtestkit1= Spinnertestkit1.getText().toString();

//                    Toast.makeText(Report.this, "selected "+selectedWhere, Toast.LENGTH_SHORT).show();



                }
            });

        }
        catch(Exception e){


        }
    }


    private void setSpinnerTestkit2Listener(){

        try{


            Spinnertestkit2.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {

                    Selectedtestkit2 = Spinnertestkit2.getText().toString();

//                    Toast.makeText(Report.this, "selected "+selectedWhere, Toast.LENGTH_SHORT).show();



                }
            });

        }
        catch(Exception e){


        }
    }


    private void setSpinnerFinalresultListener(){

        try{


            Spinnerfinalresult.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {

                    Selectedfinalresult = Spinnerfinalresult.getText().toString();

//                    Toast.makeText(Report.this, "selected "+selectedWhere, Toast.LENGTH_SHORT).show();



                }
            });

        }
        catch(Exception e){


        }
    }

    //end set spinner listeners


}

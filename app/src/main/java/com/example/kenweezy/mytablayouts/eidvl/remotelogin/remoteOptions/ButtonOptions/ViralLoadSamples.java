package com.example.kenweezy.mytablayouts.eidvl.remotelogin.remoteOptions.ButtonOptions;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.kenweezy.mytablayouts.Config.Config;
import com.example.kenweezy.mytablayouts.R;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

public class ViralLoadSamples extends AppCompatActivity {

    MaterialBetterSpinner SpinnerSex,Spinnertype;
    private ArrayAdapter<String> arrayAdapterSex,arrayAdapterType;
    String selectedSex,selectedType;
//    EditText


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vleid_sample_remote_login_viralloadsamples);
        setToolBar();
        changeStatusBarColor("#3F51B5");
        initialise();
        setSpinnerAdapters();
        setSpinnerSexListener();
        setSpinnerTypeListener();
    }




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

                    selectedSex = SpinnerSex.getText().toString();

//                    Toast.makeText(Report.this, "selected "+selectedWhere, Toast.LENGTH_SHORT).show();



                }
            });

        }
        catch(Exception e){


        }
    }


    private void setSpinnerTypeListener(){

        try{


            Spinnertype.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {

                    selectedType = Spinnertype.getText().toString();

//                    Toast.makeText(Report.this, "selected "+selectedWhere, Toast.LENGTH_SHORT).show();



                }
            });

        }
        catch(Exception e){


        }
    }

    private void initialise(){

        try{

            selectedSex ="";
            selectedType="";
            SpinnerSex =(MaterialBetterSpinner) findViewById(R.id.vlsamplesex);
            Spinnertype =(MaterialBetterSpinner) findViewById(R.id.vlsampletype);

            arrayAdapterSex = new ArrayAdapter<String>(this,
                    android.R.layout.simple_dropdown_item_1line, Config.SPINNERLISTSEX);
            arrayAdapterType = new ArrayAdapter<String>(this,
                    android.R.layout.simple_dropdown_item_1line, Config.SPINNERLISTSAMPLETYPE);
        }
        catch(Exception e){


        }
    }

    public void setSpinnerAdapters(){

        try{
            SpinnerSex.setAdapter(arrayAdapterSex);
            Spinnertype.setAdapter(arrayAdapterType);






        }
        catch(Exception e){


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

    public void setToolBar(){

        try{

            Toolbar toolbar = (Toolbar) findViewById(R.id.eidvlremoteloginviralloadtoolbar);
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle("Viral Load samples");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);

        }
        catch(Exception e){


        }
    }


}

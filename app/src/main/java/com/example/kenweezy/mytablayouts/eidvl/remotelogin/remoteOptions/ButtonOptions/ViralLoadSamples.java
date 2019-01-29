package com.example.kenweezy.mytablayouts.eidvl.remotelogin.remoteOptions.ButtonOptions;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.kenweezy.mytablayouts.Config.Config;
import com.example.kenweezy.mytablayouts.DateTimePicker.DateTimePicker;
import com.example.kenweezy.mytablayouts.R;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

public class ViralLoadSamples extends AppCompatActivity {

    MaterialBetterSpinner SpinnerSex,Spinnertype;
    EditText ccnumber,patientname,dob,datecollection,artstart,currentregimen,dateartregimen,artline,justcode;


    private ArrayAdapter<String> arrayAdapterSex,arrayAdapterType;
    String selectedSex,selectedType,ccnumberS,patientnameS,dobS,datecollectionS,artstartS,currentregimenS,dateartregimenS,artlineS,justcodeS;

    DateTimePicker dtp;
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

        setDateArtRegimen();
        setArtStart();
        setDatecollection();
        setDob();
    }

    private void clearfields(){

        try{

            SpinnerSex.setText("");
            Spinnertype.setText("");
            ccnumber.setText("");
            patientname.setText("");
            dob.setText("");
            datecollection.setText("");
            artstart.setText("");
            currentregimen.setText("");
            dateartregimen.setText("");
            artline.setText("");
            justcode.setText("");


            ccnumberS="";
            patientnameS="";
            dobS="";
            datecollectionS="";
            artstartS="";
            currentregimenS="";
            dateartregimenS="";
            artlineS="";
            justcodeS="";

            selectedSex="";
            selectedType="";



        }
        catch(Exception e){


        }
    }

    private void validate(){

        try{
//            selectedSex,selectedType,
            ccnumberS=ccnumber.getText().toString();
            patientnameS=patientname.getText().toString();
            dobS=dob.getText().toString();
            datecollectionS=datecollection.getText().toString();
            artstartS=artstart.getText().toString();
            currentregimenS=currentregimen.getText().toString();
            dateartregimenS=dateartregimen.getText().toString();
            artlineS=artline.getText().toString();
            justcodeS=justcode.getText().toString();


            if(ccnumberS.isEmpty()){

                Toast.makeText(this, "ccnumber is required", Toast.LENGTH_SHORT).show();
            }
            else if(patientnameS.isEmpty()){

                Toast.makeText(this, "patient name is required", Toast.LENGTH_SHORT).show();
            }
            else if(dobS.isEmpty()){

                Toast.makeText(this, "Date of birth is required", Toast.LENGTH_SHORT).show();
            }
            else if(datecollectionS.isEmpty()){

                Toast.makeText(this, "date of collection is required", Toast.LENGTH_SHORT).show();
            }
            else if(artstartS.isEmpty()){

                Toast.makeText(this, "art start is required", Toast.LENGTH_SHORT).show();
            }
            else if(currentregimenS.isEmpty()){

                Toast.makeText(this, "current regimen is required", Toast.LENGTH_SHORT).show();
            }
            else if(dateartregimenS.isEmpty()){

                Toast.makeText(this, "date art regimen is required", Toast.LENGTH_SHORT).show();
            }
            else if(artlineS.isEmpty()){

                Toast.makeText(this, "ART Line is required", Toast.LENGTH_SHORT).show();
            }
            else if(justcodeS.isEmpty()){

                Toast.makeText(this, "Justification code is required", Toast.LENGTH_SHORT).show();
            }
            else if(selectedType.isEmpty()){

                Toast.makeText(this, "type is required", Toast.LENGTH_SHORT).show();
            }
            else if(selectedSex.isEmpty()){

                Toast.makeText(this, "sex is required", Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(this, "submitting", Toast.LENGTH_SHORT).show();
            }


        }
        catch(Exception e){


        }
    }


    private void setDob(){

        try{
            //            EditText ccnumber,patientname,dob,datecollection,artstart,currentregimen,dateartregimen,artline,justcode;

            dob.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    dtp.setDatePicker(dob);

                }
            });



        }
        catch(Exception e){


        }
    }


    private void setArtStart(){

        try{
            //            EditText ccnumber,patientname,dob,datecollection,artstart,currentregimen,dateartregimen,artline,justcode;


            artstart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    dtp.setDatePicker(artstart);

                }
            });


        }
        catch(Exception e){


        }
    }



    private void setDateArtRegimen(){

        try{
            //            EditText ccnumber,patientname,dob,datecollection,artstart,currentregimen,dateartregimen,artline,justcode;


            dateartregimen.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    dtp.setDatePicker(dateartregimen);

                }
            });


        }
        catch(Exception e){


        }
    }


    private void setDatecollection(){

        try{
            //            EditText ccnumber,patientname,dob,datecollection,artstart,currentregimen,dateartregimen,artline,justcode;

            datecollection.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    dtp.setDatePicker(datecollection);

                }
            });



        }
        catch(Exception e){


        }
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

//            EditText ccnumber,patientname,dob,datecollection,artstart,currentregimen,dateartregimen,artline,justcode;

            ccnumberS="";
            patientnameS="";
            dobS="";
            datecollectionS="";
            artstartS="";
            currentregimenS="";
            dateartregimenS="";
            artlineS="";
            justcodeS="";

            ccnumber=(EditText) findViewById(R.id.vlsampleccnumber);
            patientname=(EditText) findViewById(R.id.vlsamplepatientname);
            dob=(EditText) findViewById(R.id.vlsampledob);
            datecollection=(EditText) findViewById(R.id.vlsampledatecollection);
            artstart=(EditText) findViewById(R.id.vlsampleartstart);
            currentregimen=(EditText) findViewById(R.id.vlsamplecurrentregimen);
            dateartregimen=(EditText) findViewById(R.id.vlsampledateartregimen);
            artline=(EditText) findViewById(R.id.vlsampleartline);
            justcode=(EditText) findViewById(R.id.vlsamplejustcode);


            dtp=new DateTimePicker(ViralLoadSamples.this);
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

    public void CancelVlSamples(View v){

        try{

            clearfields();

        }
        catch(Exception e){


        }


    }

    public void SubmitSample(View v){

        try{

            validate();

        }
        catch(Exception e){


        }
    }


}

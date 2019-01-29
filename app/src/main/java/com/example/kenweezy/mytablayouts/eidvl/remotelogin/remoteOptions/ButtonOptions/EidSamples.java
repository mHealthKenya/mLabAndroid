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
import android.widget.Toast;

import com.example.kenweezy.mytablayouts.Config.Config;
import com.example.kenweezy.mytablayouts.DateTimePicker.DateTimePicker;
import com.example.kenweezy.mytablayouts.R;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

public class EidSamples extends AppCompatActivity {

    MaterialBetterSpinner SpinnerSex, SpinnerRegimen,Spinneralivedead;
    EditText heinumber,patientname,dob,entrypoint,collectiondate,prophylaxiscode,infantfeeding,pcr,alivedead,motherage,haartdate;
    private ArrayAdapter<String> arrayAdapterSex, arrayAdapterRegimen,arrayAdapterAliveDead;
    String selectedSex,selectedRegimen,selectedAlive,heinumberS,patientnameS,dobS,entrypointS,collectiondateS,prophylaxiscodeS,infantfeedingS,pcrS,alivedeadS,motherageS,haartdateS;

    DateTimePicker dtp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vleid_sample_remote_login_eid);
        setToolBar();
        changeStatusBarColor("#3F51B5");
        initialise();
        setSpinnerAdapters();
        setSpinnerSexListener();
        setSpinnerRegimenListener();
        setSpinnerAliveDeadListener();

        setHaartdate();
        setCollectionDate();
        setDob();
    }


    private void clearFields(){

        try{

            SpinnerSex.setText("");
            SpinnerRegimen.setText("");
            Spinneralivedead.setText("");
            heinumber.setText("");
            patientname.setText("");
            dob.setText("");
            entrypoint.setText("");
            collectiondate.setText("");
            prophylaxiscode.setText("");
            infantfeeding.setText("");
            pcr.setText("");
            alivedead.setText("");
            motherage.setText("");
            haartdate.setText("");



            heinumberS="";
            patientnameS="";
            dobS="";
            entrypointS="";
            collectiondateS="";
            prophylaxiscodeS="";
            infantfeedingS="";
            pcrS="";
            alivedeadS="";
            motherageS="";
            haartdateS="";



            selectedSex ="";
            selectedRegimen="";
            selectedAlive="";

        }
        catch(Exception e){


        }
    }
    private void setHaartdate(){
        try{

            haartdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dtp.setDatePicker(haartdate);
                }
            });
        }
        catch(Exception e){


        }
    }

    private void setCollectionDate(){

        try{

            collectiondate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dtp.setDatePicker(collectiondate);
                }
            });
        }
        catch(Exception e){


        }
    }

    private void setDob(){

        try{

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

    private void validate(){

        try{

            heinumberS=heinumber.getText().toString();
            patientnameS=patientname.getText().toString();
            dobS=dob.getText().toString();
            entrypointS=entrypoint.getText().toString();
            collectiondateS=collectiondate.getText().toString();
            prophylaxiscodeS=prophylaxiscode.getText().toString();
            infantfeedingS=infantfeeding.getText().toString();
            pcrS=pcr.getText().toString();
            alivedeadS=alivedead.getText().toString();
            motherageS=motherage.getText().toString();
            haartdateS=haartdate.getText().toString();



            if(selectedSex.isEmpty()){

                Toast.makeText(this, "sex is required", Toast.LENGTH_SHORT).show();
            }
            else if(selectedRegimen.isEmpty()){

                Toast.makeText(this, "Regimen is required", Toast.LENGTH_SHORT).show();
            }
            else if(selectedAlive.isEmpty()){

                Toast.makeText(this, "Alive or dead is required", Toast.LENGTH_SHORT).show();
            }
            else if(heinumberS.isEmpty()){

                Toast.makeText(this, "heinumber is required", Toast.LENGTH_SHORT).show();
            }
            else if(patientnameS.isEmpty()){

                Toast.makeText(this, "patientname is required", Toast.LENGTH_SHORT).show();
            }
            else if(dobS.isEmpty()){

                Toast.makeText(this, "dob is required", Toast.LENGTH_SHORT).show();
            }
            else if(entrypointS.isEmpty()){

                Toast.makeText(this, "entrypoint is required", Toast.LENGTH_SHORT).show();
            }
            else if(collectiondateS.isEmpty()){

                Toast.makeText(this, "collectiondate is required", Toast.LENGTH_SHORT).show();
            }
            else if(prophylaxiscodeS.isEmpty()){

                Toast.makeText(this, "prophylaxiscode is required", Toast.LENGTH_SHORT).show();
            }
            else if(infantfeedingS.isEmpty()){

                Toast.makeText(this, "infantfeeding is required", Toast.LENGTH_SHORT).show();
            }
            else if(pcrS.isEmpty()){

                Toast.makeText(this, "pcr is required", Toast.LENGTH_SHORT).show();
            }
            else if(alivedeadS.isEmpty()){

                Toast.makeText(this, "alivedead is required", Toast.LENGTH_SHORT).show();
            }
            else if(motherageS.isEmpty()){

                Toast.makeText(this, "motherage is required", Toast.LENGTH_SHORT).show();
            }
            else if(haartdateS.isEmpty()){

                Toast.makeText(this, "haartdate is required", Toast.LENGTH_SHORT).show();
            }
            else{

                Toast.makeText(this, "submitting", Toast.LENGTH_SHORT).show();
            }
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


    private void setSpinnerAliveDeadListener(){

        try{


            Spinneralivedead.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {

                    selectedAlive = Spinneralivedead.getText().toString();

//                    Toast.makeText(Report.this, "selected "+selectedWhere, Toast.LENGTH_SHORT).show();



                }
            });

        }
        catch(Exception e){


        }
    }

    private void setSpinnerRegimenListener(){

        try{


            SpinnerRegimen.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {

                    selectedRegimen = SpinnerRegimen.getText().toString();

//                    Toast.makeText(Report.this, "selected "+selectedWhere, Toast.LENGTH_SHORT).show();



                }
            });

        }
        catch(Exception e){


        }
    }



    private void initialise(){

        try{

            dtp=new DateTimePicker(EidSamples.this);
            heinumber=(EditText) findViewById(R.id.eidsampleheinumber);
            patientname=(EditText) findViewById(R.id.eidsamplepatientname);
            dob=(EditText) findViewById(R.id.eidsampledob);
            entrypoint=(EditText) findViewById(R.id.eidsampleentrypoint);
            collectiondate=(EditText) findViewById(R.id.eidsamplecollectiondate);
            prophylaxiscode=(EditText) findViewById(R.id.eidsampleprophylaxiscode);
            infantfeeding=(EditText) findViewById(R.id.eidsampleinfantfeeding);
            pcr=(EditText) findViewById(R.id.eidsamplepcr);
            alivedead=(EditText) findViewById(R.id.eidsamplealivedead);
            motherage=(EditText) findViewById(R.id.eidmotherage);
            haartdate=(EditText) findViewById(R.id.eidsamplehaartdate);

            heinumberS="";
            patientnameS="";
            dobS="";
            entrypointS="";
            collectiondateS="";
            prophylaxiscodeS="";
            infantfeedingS="";
            pcrS="";
            alivedeadS="";
            motherageS="";
            haartdateS="";



            selectedSex ="";
            selectedRegimen="";
            selectedAlive="";

            Spinneralivedead =(MaterialBetterSpinner) findViewById(R.id.eidsamplealivedead);
            SpinnerSex =(MaterialBetterSpinner) findViewById(R.id.eidsamplesex);
            SpinnerRegimen =(MaterialBetterSpinner) findViewById(R.id.eidsampepmtctregimen);

            arrayAdapterSex = new ArrayAdapter<String>(this,
                    android.R.layout.simple_dropdown_item_1line, Config.SPINNERLISTSEX);
            arrayAdapterRegimen = new ArrayAdapter<String>(this,
                    android.R.layout.simple_dropdown_item_1line, Config.SPINNERLISTSAMPLETYPE);

            arrayAdapterAliveDead = new ArrayAdapter<String>(this,
                    android.R.layout.simple_dropdown_item_1line, Config.SPINNERLISTSAMPLETYPE);
        }
        catch(Exception e){


        }
    }

    public void setSpinnerAdapters(){

        try{
            SpinnerSex.setAdapter(arrayAdapterSex);
            SpinnerRegimen.setAdapter(arrayAdapterRegimen);
            Spinneralivedead.setAdapter(arrayAdapterAliveDead);






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

            Toolbar toolbar = (Toolbar) findViewById(R.id.eidvlremotelogineidtoolbar);
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle("Eid samples");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);

        }
        catch(Exception e){


        }
    }

    public void CancelEidSamples(View v){

        try{

            clearFields();

        }
        catch(Exception e){


        }
    }

    public void SubmitEidSample(View v){

        try{

            validate();


        }
        catch(Exception e){


        }
    }


}

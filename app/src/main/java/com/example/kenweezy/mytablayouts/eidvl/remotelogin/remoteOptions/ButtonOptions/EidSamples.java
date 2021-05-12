package com.example.kenweezy.mytablayouts.eidvl.remotelogin.remoteOptions.ButtonOptions;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kenweezy.mytablayouts.AccessServer.AccessServer;
import com.example.kenweezy.mytablayouts.Config.Config;
import com.example.kenweezy.mytablayouts.DateTimePicker.DateTimePicker;
import com.example.kenweezy.mytablayouts.R;
//import com.example.kenweezy.mytablayouts.SSLTrustCertificate.SSLTrust;
import com.example.kenweezy.mytablayouts.UsersTable;
import com.example.kenweezy.mytablayouts.encryption.Base64Encoder;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class EidSamples extends AppCompatActivity {

    MaterialBetterSpinner SpinnerSex, SpinnerRegimen,Spinneralivedead,spinnerentrypoint,spinnerprophylaxiscode,spinnerinfantfeeding,spinnerpcr;
    EditText heinumber,patientname,dob,collectiondate,alivedead,motherage,haartdate,mothervlresultsE,mothercccnumberE,infantcccnumberE,otherEntrypointE,otherProphylaxiscodeE;
    private ArrayAdapter<String> arrayAdapterSex, arrayAdapterRegimen,arrayAdapterAliveDead,arrayAdapterEntrypoint,arrayAdapterProphylaxiscode,arrayAdapterInfantfeeding,arrayAdapterPcr;
    String selectedSex,selectedRegimen,selectedAlive,heinumberS,patientnameS,dobS,collectiondateS,alivedeadS,motherageS,haartdateS,selectedEntrypoint,selectedProphylaxiscode,selectedInfantfeeding,selectedPcr,mothervlresultsS,mothercccnumberS,infantcccnumberS,otherEntrypointS,otherProphylaxiscodeS, labNameS, labId;
    TextView labName;

    DateTimePicker dtp;
    DatePickerDialog dp;
    AccessServer acs;

    final Calendar myCalendar = Calendar.getInstance();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vleid_sample_remote_login_eid);
        dob = (EditText)findViewById(R.id.eidsampledob);
        dob.setInputType(InputType.TYPE_NULL);
        labName = findViewById(R.id.labNameTextView);
        Bundle bundle=getIntent().getExtras();
        String selectedLab=bundle.getString("selectedLab");
        labName.setText(selectedLab);
        setToolBar();
        changeStatusBarColor("#3F51B5");
        initialise();

        //SSLTrust.nuke();

        setSpinnerAdapters();
        setSpinnerSexListener();
        setSpinnerRegimenListener();
        setSpinnerAliveDeadListener();

        setSpinnerEntrypointListener();
        setSpinnerInfantfeedingListener();
        setSpinnerPcrListener();
        setSpinnerProphylaxiscodeListener();

        setHaartdate();
        setCollectionDate();
        setDob();
    }


    private void clearFields(){

        try{

            SpinnerSex.setText("");
            SpinnerRegimen.setText("");
            Spinneralivedead.setText("");

            spinnerpcr.setText("");
            spinnerinfantfeeding.setText("");
            spinnerprophylaxiscode.setText("");
            spinnerentrypoint.setText("");

            heinumber.setText("");
            patientname.setText("");
            dob.setText("");

            collectiondate.setText("");

            alivedead.setText("");
            motherage.setText("");
            haartdate.setText("");



            heinumberS="";
            patientnameS="";
            dobS="";

            collectiondateS="";

            alivedeadS="";
            motherageS="";
            haartdateS="";
            otherEntrypointS="";
            otherProphylaxiscodeS="";



            selectedSex ="";
            selectedRegimen="";
            selectedAlive="";
            selectedProphylaxiscode="";
            selectedPcr="";
            selectedInfantfeeding="";
            selectedEntrypoint="";

        }
        catch(Exception e){


        }
    }
    private void setHaartdate(){
        try{

            haartdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    myCalendar.getInstance();
                    int day = myCalendar.get(Calendar.DAY_OF_MONTH);
                    int month = myCalendar.get(Calendar.MONTH);
                    int year = myCalendar.get(Calendar.YEAR);
                    dp = new DatePickerDialog(EidSamples.this, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker datePicker, int year, int monthOfyear, int dayOfMonth) {
                            haartdate.setText(year  + "-" + (monthOfyear +1) + "-" + dayOfMonth);
                        }
                    }, year, month, day);
                    dp.getDatePicker().setMaxDate(System.currentTimeMillis());
                    dp.show();
                    System.out.println(dp);
                    // dtp.setDatePicker(dob);


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
                    myCalendar.getInstance();
                    int day = myCalendar.get(Calendar.DAY_OF_MONTH);
                    int month = myCalendar.get(Calendar.MONTH);
                    int year = myCalendar.get(Calendar.YEAR);
                    dp = new DatePickerDialog(EidSamples.this, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker datePicker, int year, int monthOfyear, int dayOfMonth) {
                            collectiondate.setText(year  + "-" + (monthOfyear +1) + "-" + dayOfMonth);
                        }
                    }, year, month, day);
                    dp.getDatePicker().setMaxDate(System.currentTimeMillis());
                    dp.show();
                    System.out.println(dp);
                    // dtp.setDatePicker(dob);


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
                    myCalendar.getInstance();
                    int day = myCalendar.get(Calendar.DAY_OF_MONTH);
                    int month = myCalendar.get(Calendar.MONTH);
                    int year = myCalendar.get(Calendar.YEAR);
                    dp = new DatePickerDialog(EidSamples.this, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker datePicker, int year, int monthOfyear, int dayOfMonth) {
                            dob.setText(year  + "-" + (monthOfyear +1) + "-" + dayOfMonth);
                        }
                    }, year, month, day);
                    dp.getDatePicker().setMaxDate(System.currentTimeMillis());
                    dp.show();
                    System.out.println(dp);
                   // dtp.setDatePicker(dob);


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

            collectiondateS=collectiondate.getText().toString();

            alivedeadS=alivedead.getText().toString();
            motherageS=motherage.getText().toString();
            haartdateS=haartdate.getText().toString();
            mothervlresultsS=mothervlresultsE.getText().toString();
            mothercccnumberS=mothercccnumberE.getText().toString();
            labNameS = labName.getText().toString();

            if(labNameS.equals("KU Teaching and Referring Hospital")){
                labId = "1";
            } else  if(labNameS.equals("Kisumu Lab")){
                labId = "2";
            } else  if(labNameS.equals("Alupe")){
                labId = "3";
            } else  if(labNameS.equals("Walter Reed")){
                labId = "4";
            }  else  if(labNameS.equals("Ampath")){
                labId = "5";
            } else  if(labNameS.equals("Coast Lab")){
                labId = "6";
            } else  if(labNameS.equals("KNH")){
                labId = "7";
            }

            if(selectedEntrypoint.equals("IPD")){
                selectedEntrypoint = "1";
            } else  if(selectedEntrypoint.equals("OPD")){
                selectedEntrypoint = "2";
            } else  if(selectedEntrypoint.equals("MATERNITY")){
                selectedEntrypoint = "3";
            } else  if(selectedEntrypoint.equals("CCC")){
                selectedEntrypoint = "4";
            }  else  if(selectedEntrypoint.equals("MCH/PMTCT")){
                selectedEntrypoint = "5";
            } else  if(selectedEntrypoint.equals("Other")){
                selectedEntrypoint = "6";
            }

            if(selectedRegimen.equals("PM1X=Any other Regimen")){
                selectedRegimen = "1";
            } else  if(selectedRegimen.equals("PM3= AZT+3TC+NVP")){
                selectedRegimen = "2";
            } else  if(selectedRegimen.equals("PM4= AZT+ 3TC+ EFV")){
                selectedRegimen = "3";
            } else  if(selectedRegimen.equals("PM5= AZT+3TC+ LPV/r")){
                selectedRegimen = "4";
            }  else  if(selectedRegimen.equals("PM6= TDC+3TC+NVP")){
                selectedRegimen = "5";
            } else  if(selectedRegimen.equals("PM7= TDF+3TC+LPV/r")){
                selectedRegimen = "6";
            } else  if(selectedRegimen.equals("PM9= TDF+3TC+EFV")){
                selectedRegimen = "2";
            } else  if(selectedRegimen.equals("PM10= AZT+3TC+ATV/r")){
                selectedRegimen = "3";
            } else  if(selectedRegimen.equals("PM11= TDF+3TC+ATV/r")){
                selectedRegimen = "4";
            }  else  if(selectedRegimen.equals("PM12=TDF+3TC+DTG")){
                selectedRegimen = "5";
            } else  if(selectedRegimen.equals("PM13=None")){
                selectedRegimen = "6";
            }

            if(selectedPcr.equals("1= Initial PCR")){
                selectedPcr = "1";
            } else  if(selectedPcr.equals("2= 2nd PCR")){
                selectedPcr = "2";
            } else  if(selectedPcr.equals("3= 3rd PCR")){
                selectedPcr = "3";
            } else  if(selectedPcr.equals("4= Confirmatory PCR and Baseline VL")){
                selectedPcr = "4";
            }  else  if(selectedPcr.equals("5= Discrepant PCR (tie breaker)")){
                selectedPcr = "5";
            } else  if(selectedPcr.equals("6= Sample redraw")){
                selectedPcr = "6";
            }

            if(infantcccnumberE.isShown()){
                infantcccnumberS=infantcccnumberE.getText().toString();
            }




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

                Toast.makeText(this, "HEI number is required", Toast.LENGTH_SHORT).show();
            }
            else if(heinumberS.length() < 10 || heinumberS.length() >15){

                Toast.makeText(this, "HEI number should be between 10 to 15", Toast.LENGTH_SHORT).show();
            }
            else if(patientnameS.isEmpty()){

                Toast.makeText(this, "patientname is required", Toast.LENGTH_SHORT).show();
            }
            else if(dobS.isEmpty()){

                Toast.makeText(this, "dob is required", Toast.LENGTH_SHORT).show();
            }
            else if(selectedEntrypoint.isEmpty()){

                Toast.makeText(this, "entrypoint is required", Toast.LENGTH_SHORT).show();
            }
            else if(collectiondateS.isEmpty()){

                Toast.makeText(this, "collectiondate is required", Toast.LENGTH_SHORT).show();
            }
            else if(selectedProphylaxiscode.isEmpty()){

                Toast.makeText(this, "prophylaxiscode is required", Toast.LENGTH_SHORT).show();
            }
            else if(selectedInfantfeeding.isEmpty()){

                Toast.makeText(this, "infantfeeding is required", Toast.LENGTH_SHORT).show();
            }
            else if(selectedPcr.isEmpty()){

                Toast.makeText(this, "pcr is required", Toast.LENGTH_SHORT).show();
            }
            else if(alivedeadS.isEmpty()){

                Toast.makeText(this, "alivedead is required", Toast.LENGTH_SHORT).show();
            }
            else if(mothervlresultsS.isEmpty()){

                Toast.makeText(this, "mother vl results is required", Toast.LENGTH_SHORT).show();
            }
            else if(mothercccnumberS.isEmpty()){

                Toast.makeText(this, "mother ccc number is required", Toast.LENGTH_SHORT).show();
            }
            else if(infantcccnumberE.isShown() && infantcccnumberS.isEmpty()){

                Toast.makeText(this, "infant ccc number is required", Toast.LENGTH_SHORT).show();

            }
            else if(motherageS.isEmpty()){

                Toast.makeText(this, "motherage is required", Toast.LENGTH_SHORT).show();
            }
            else if(haartdateS.isEmpty()){

                Toast.makeText(this, "haartdate is required", Toast.LENGTH_SHORT).show();
            }
            else{

                if(otherProphylaxiscodeE.isShown()){

                    otherProphylaxiscodeS=otherProphylaxiscodeE.getText().toString();




                }

                if(otherEntrypointE.isShown()){

                    otherEntrypointS=otherEntrypointE.getText().toString();


                }

                if(otherProphylaxiscodeE.isShown() && otherProphylaxiscodeS.isEmpty()){

                    Toast.makeText(this, "other prophylaxis is required", Toast.LENGTH_SHORT).show();

                }
                else if(otherEntrypointE.isShown() && otherEntrypointS.isEmpty()){

                    Toast.makeText(this, "other entry point is required", Toast.LENGTH_SHORT).show();
                }

                String userPhoneNumber="";

                List<UsersTable> myl=UsersTable.findWithQuery(UsersTable.class,"select * from Users_table limit 1");
                for(int y=0;y<myl.size();y++){

                    userPhoneNumber=myl.get(y).getPhonenumber();
                }

                if(!infantcccnumberE.isShown()){

                    infantcccnumberS="-1";

                }
                if(!otherEntrypointE.isShown()){

                    otherEntrypointS="-1";

                }
                if(!otherProphylaxiscodeE.isShown()){

                    otherProphylaxiscodeS="-1";

                }
//                Toast.makeText(this, "submitting", Toast.LENGTH_SHORT).show();
                System.out.println(userPhoneNumber);

              /*  String message="EID*"+selectedSex+"*"+selectedRegimen+"*"+selectedAlive+"*"+heinumberS+"*"+patientnameS
                        +"*"+dobS+"*"+selectedEntrypoint+"*"+otherEntrypointS+"*"+collectiondateS+"*"+selectedProphylaxiscode+"*"+otherProphylaxiscodeS+"*"+selectedInfantfeeding+"*"
                        +selectedPcr+"*"+alivedeadS+"*"+motherageS+"*"+haartdateS+"*"+mothercccnumberS+"*"+mothervlresultsS+"*"+infantcccnumberS+"*"+labName+"*"+ labId +"*"+ entrypoint +"*"+ regimen +"*"+ pcrType;
*/

                String message= "EID*" + selectedSex + "*" + selectedRegimen + "*" + selectedAlive+"*"+heinumberS+"*"+patientnameS
                        +"*"+dobS+"*"+selectedEntrypoint+"*"+collectiondateS+"*"+selectedProphylaxiscode+"*"+selectedInfantfeeding+"*"
                        +selectedPcr+"*"+alivedeadS+"*"+motherageS+"*"+haartdateS +"*"+ labNameS+"*"+ labId ;//+ "*" + infantcccnumberS;
                System.out.println(labNameS);
                System.out.println(labId);
                System.out.println("**phone encrypted**********"+Base64Encoder.encryptString(userPhoneNumber)+"***message encrypted******"+Base64Encoder.encryptString(message));


                acs.submitEidVlData(Base64Encoder.encryptString(userPhoneNumber), Base64Encoder.encryptString(message));

            }
        }
        catch(Exception e){


        }
    }




    private void setSpinnerEntrypointListener(){

        try{


            spinnerentrypoint.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {

                    selectedEntrypoint = spinnerentrypoint.getText().toString();

//                    Toast.makeText(EidSamples.this, "selected "+selectedEntrypoint, Toast.LENGTH_SHORT).show();
                    if(selectedEntrypoint.contentEquals("Other")){

                        otherEntrypointE.setVisibility(View.VISIBLE);
                    }
                    else{

                        otherEntrypointE.setVisibility(View.GONE);
                        otherEntrypointE.setText("");
                        otherEntrypointS="";
                    }



                }
            });

        }
        catch(Exception e){


        }
    }



    private void setSpinnerProphylaxiscodeListener(){

        try{


            spinnerprophylaxiscode.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {

                    selectedProphylaxiscode = spinnerprophylaxiscode.getText().toString();

                    if(selectedProphylaxiscode.contentEquals("Other")){

                        otherProphylaxiscodeE.setVisibility(View.VISIBLE);
                    }
                    else{
                        otherProphylaxiscodeE.setVisibility(View.GONE);
                        otherProphylaxiscodeE.setText("");
                        otherProphylaxiscodeS="";
                    }

//                    Toast.makeText(Report.this, "selected "+selectedWhere, Toast.LENGTH_SHORT).show();



                }
            });

        }
        catch(Exception e){


        }
    }




    private void setSpinnerInfantfeedingListener(){

        try{


            spinnerinfantfeeding.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {

                    selectedInfantfeeding = spinnerinfantfeeding.getText().toString();

//                    Toast.makeText(Report.this, "selected "+selectedWhere, Toast.LENGTH_SHORT).show();



                }
            });

        }
        catch(Exception e){


        }
    }





    private void setSpinnerPcrListener(){

        try{


            spinnerpcr.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {

                    selectedPcr = spinnerpcr.getText().toString();

                    if(selectedPcr.contentEquals(Config.SPINNERLISTPCR[3])){

                        infantcccnumberE.setVisibility(View.VISIBLE);

                    }
                    else{

                        infantcccnumberE.setVisibility(View.GONE);
                    }

//                    Toast.makeText(Report.this, "selected "+selectedWhere, Toast.LENGTH_SHORT).show();



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

            acs=new AccessServer(EidSamples.this);

            dtp=new DateTimePicker(EidSamples.this);
            heinumber=(EditText) findViewById(R.id.eidsampleheinumber);
            patientname=(EditText) findViewById(R.id.eidsamplepatientname);
            dob=(EditText) findViewById(R.id.eidsampledob);

            collectiondate=(EditText) findViewById(R.id.eidsamplecollectiondate);

            alivedead=(EditText) findViewById(R.id.eidsamplealivedead);
            motherage=(EditText) findViewById(R.id.eidmotherage);
            haartdate=(EditText) findViewById(R.id.eidsamplehaartdate);
            mothervlresultsE=(EditText) findViewById(R.id.eidsamplmothervlresults);
            mothercccnumberE=(EditText) findViewById(R.id.eidsamplmothercccnumber);
            infantcccnumberE=(EditText) findViewById(R.id.eidsampleinfantcccnumber);
            otherEntrypointE=(EditText) findViewById(R.id.eidsampleotherentrypoint);
            otherProphylaxiscodeE=(EditText) findViewById(R.id.eidsampleotherprophylaxiscode);

            heinumberS="";
            patientnameS="";
            dobS="";

            collectiondateS="";

            alivedeadS="";
            motherageS="";
            haartdateS="";

            mothercccnumberS="";
            mothervlresultsS="";
            infantcccnumberS="";



            selectedSex ="";
            selectedRegimen="";
            selectedAlive="";
            selectedEntrypoint="";
            selectedInfantfeeding="";
            selectedPcr="";
            selectedProphylaxiscode="";

            Spinneralivedead =(MaterialBetterSpinner) findViewById(R.id.eidsamplealivedead);
            SpinnerSex =(MaterialBetterSpinner) findViewById(R.id.eidsamplesex);
            SpinnerRegimen =(MaterialBetterSpinner) findViewById(R.id.eidsampepmtctregimen);

            spinnerentrypoint =(MaterialBetterSpinner) findViewById(R.id.eidsampleentrypointselect);
            spinnerinfantfeeding =(MaterialBetterSpinner) findViewById(R.id.eidsampleinfantfeedingselect);
            spinnerpcr =(MaterialBetterSpinner) findViewById(R.id.eidsamplepcrselect);
            spinnerprophylaxiscode =(MaterialBetterSpinner) findViewById(R.id.eidsampleprophylaxiscodeselect);

            arrayAdapterSex = new ArrayAdapter<String>(this,
                    android.R.layout.simple_dropdown_item_1line, Config.SPINNERLISTSEX);

            arrayAdapterRegimen = new ArrayAdapter<String>(this,
                    android.R.layout.simple_dropdown_item_1line, Config.SPINNERLISTREGIMEN);

            arrayAdapterAliveDead = new ArrayAdapter<String>(this,
                    android.R.layout.simple_dropdown_item_1line, Config.SPINNERLISTALIVEDEAD);

            arrayAdapterEntrypoint = new ArrayAdapter<String>(this,
                    android.R.layout.simple_dropdown_item_1line, Config.SPINNERLISTENTRYPOINT);

            arrayAdapterInfantfeeding = new ArrayAdapter<String>(this,
                    android.R.layout.simple_dropdown_item_1line, Config.SPINNERLISTINFANTFEEDING);

            arrayAdapterPcr = new ArrayAdapter<String>(this,
                    android.R.layout.simple_dropdown_item_1line, Config.SPINNERLISTPCR);

            arrayAdapterProphylaxiscode = new ArrayAdapter<String>(this,
                    android.R.layout.simple_dropdown_item_1line, Config.SPINNERLISTPROPHYLAXISCODE);
        }
        catch(Exception e){


        }
    }

    public void setSpinnerAdapters(){

        try{
            SpinnerSex.setAdapter(arrayAdapterSex);
            SpinnerRegimen.setAdapter(arrayAdapterRegimen);
            Spinneralivedead.setAdapter(arrayAdapterAliveDead);
            spinnerprophylaxiscode.setAdapter(arrayAdapterProphylaxiscode);
            spinnerpcr.setAdapter(arrayAdapterPcr);
            spinnerinfantfeeding.setAdapter(arrayAdapterInfantfeeding);
            spinnerentrypoint.setAdapter(arrayAdapterEntrypoint);






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

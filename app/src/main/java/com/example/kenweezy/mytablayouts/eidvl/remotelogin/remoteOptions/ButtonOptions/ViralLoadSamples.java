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
import java.util.List;

public class ViralLoadSamples extends AppCompatActivity {

    MaterialBetterSpinner SpinnerSex, Spinnertype, SpinnerCurrentRegimen, SpinnerArtLine, SpinnerJustcode;
    EditText ccnumber, patientname, dob, datecollection, artstart, dateartregimen;


    private ArrayAdapter<String> arrayAdapterSex, arrayAdapterType, arrayAdapterCurrentArtRegimen, arrayAdapterArtLine, arrayAdapterJustCode;
    String selectedSex, selectedType, selectedCurrentRegimen, selectedArtLine, selectedJustCode, ccnumberS, patientnameS, dobS, datecollectionS, artstartS, dateartregimenS, labNameS, labId;
    TextView labName;


    DateTimePicker dtp;
    DatePickerDialog dp;
    AccessServer acs;
//    EditText

    final Calendar myCalendar = Calendar.getInstance();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vleid_sample_remote_login_viralloadsamples);
        dob = (EditText)findViewById(R.id.vlsampledob);
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
        setSpinnerTypeListener();
        setSpinnerArtLineListener();
        setSpinnerJustCodeListener();
        setSpinnerCurrentRegimenListener();


        setDateArtRegimen();
        setArtStart();
        setDatecollection();
        setDob();
    }

    private void clearfields() {

        try {

            SpinnerSex.setText("");
            Spinnertype.setText("");
            SpinnerCurrentRegimen.setText("");
            SpinnerJustcode.setText("");
            SpinnerArtLine.setText("");


            ccnumber.setText("");
            patientname.setText("");
            dob.setText("");
            datecollection.setText("");
            artstart.setText("");

            dateartregimen.setText("");

            ccnumberS = "";
            patientnameS = "";
            dobS = "";
            datecollectionS = "";
            artstartS = "";

            dateartregimenS = "";
            selectedSex = "";
            selectedType = "";
            selectedArtLine = "";
            selectedJustCode = "";
            selectedCurrentRegimen = "";


        } catch (Exception e) {


        }
    }

    private void validate() {

        try {
//            selectedSex,selectedType,
            ccnumberS = ccnumber.getText().toString();
            patientnameS = patientname.getText().toString();
            dobS = dob.getText().toString();
            datecollectionS = datecollection.getText().toString();
            artstartS = artstart.getText().toString();

            dateartregimenS = dateartregimen.getText().toString();
            labNameS = labName.getText().toString();



//            "VL*"+ccnumberS+"*"+patientnameS+"*"+dobS+"*"+datecollectionS+"*"+artstartS+"*"
////                        +currentregimenS+"*"+dateartregimenS+"*"+artlineS+"*"+justcodeS+"*"+selectedType
////                        +"*"+selectedSex;


            System.out.println("****submitted data*********");
            System.out.println("sex***" + selectedSex);
            System.out.println("type****" + selectedType);
            System.out.println("ccnumber****" + ccnumberS);
            System.out.println("patient****" + patientnameS);
            System.out.println("dob****" + dobS);
            System.out.println("datecollection****" + datecollectionS);
            System.out.println("artstart****" + artstartS);

            System.out.println("art regimen date****" + dateartregimenS);

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

            if(selectedJustCode.equals("1=Routine VL")){
                selectedJustCode = "1";
            } else  if(selectedJustCode.equals("2=Confirmation of treatment failure (repeat VL)")){
                selectedJustCode = "2";
            } else  if(selectedJustCode.equals("3=Clinical failure")){
                selectedJustCode = "3";
            } else  if(selectedJustCode.equals("4=Single drug substitution")){
                selectedJustCode = "4";
            }  else  if(selectedJustCode.equals("5=Baseline VL (for infants diagnosed through EID)")){
                selectedJustCode = "5";
            }


            if(selectedCurrentRegimen.equals("1= TDF+ 3TC+ EFV")){
                selectedCurrentRegimen = "1";
            } else  if(selectedCurrentRegimen.equals("2=TDF+3TC+NVP")){
                selectedCurrentRegimen = "2";
            } else  if(selectedCurrentRegimen.equals("3=TDF+3TC+ DTG")){
                selectedCurrentRegimen = "3";
            } else  if(selectedCurrentRegimen.equals("4=AZT+3TC+NVP")){
                selectedCurrentRegimen = "4";
            }  else  if(selectedCurrentRegimen.equals("5=AZT+3TC+EFV|")){
                selectedCurrentRegimen = "5";
            } else  if(selectedCurrentRegimen.equals("6=ABC+3TC+NVP")){
                selectedCurrentRegimen = "6";
            } else  if(selectedCurrentRegimen.equals("7=ABC+3TC+EFV")){
                selectedCurrentRegimen = "7";
            } else  if(selectedCurrentRegimen.equals("8= ABC+3TC+DTG")){
            selectedCurrentRegimen = "8";
        } else  if(selectedCurrentRegimen.equals("9=ABC+3TC+LPV/r|")){
            selectedCurrentRegimen = "9";
        } else  if(selectedCurrentRegimen.equals("10=AZT+3TC+LPV/r+ RTV")){
            selectedCurrentRegimen = "10";
        }  else  if(selectedCurrentRegimen.equals("11=TDF+3TC +ATV/r")){
            selectedCurrentRegimen = "11";
        } else  if(selectedCurrentRegimen.equals("12=ABC+3TC+DTG")){
            selectedCurrentRegimen = "12";
        }else  if(selectedCurrentRegimen.equals("13=ABC+3TC+ATV/r")){
            selectedCurrentRegimen = "13";
        } else  if(selectedCurrentRegimen.equals("14=AZT+3TC+ATV/r")){
            selectedCurrentRegimen = "14";
        }  else  if(selectedCurrentRegimen.equals("15=AZT+3TC+DRV/r")){
            selectedCurrentRegimen = "15";
        } else  if(selectedCurrentRegimen.equals("16=Other")){
            selectedCurrentRegimen = "16";
        }


            if (ccnumberS.isEmpty()) {

                Toast.makeText(this, "ccnumber is required", Toast.LENGTH_SHORT).show();
            } else if (ccnumberS.length() != 10) {

                Toast.makeText(this, "ccnumber should be 10 characters", Toast.LENGTH_SHORT).show();
            }else if (patientnameS.isEmpty()) {

                Toast.makeText(this, "patient name is required", Toast.LENGTH_SHORT).show();
            } else if (dobS.isEmpty()) {

                Toast.makeText(this, "Date of birth is required", Toast.LENGTH_SHORT).show();
            } else if (datecollectionS.isEmpty()) {

                Toast.makeText(this, "date of collection is required", Toast.LENGTH_SHORT).show();
            } else if (artstartS.isEmpty()) {

                Toast.makeText(this, "art start is required", Toast.LENGTH_SHORT).show();
            } else if (selectedCurrentRegimen.isEmpty()) {

                Toast.makeText(this, "current regimen is required", Toast.LENGTH_SHORT).show();
            } else if (dateartregimenS.isEmpty()) {

                Toast.makeText(this, "date art regimen is required", Toast.LENGTH_SHORT).show();
            } else if (selectedArtLine.isEmpty()) {

                Toast.makeText(this, "ART Line is required", Toast.LENGTH_SHORT).show();
            } else if (selectedJustCode.isEmpty()) {

                Toast.makeText(this, "Justification code is required", Toast.LENGTH_SHORT).show();
            } else if (selectedType.isEmpty()) {

                Toast.makeText(this, "type is required", Toast.LENGTH_SHORT).show();
            } else if (selectedSex.isEmpty()) {

                Toast.makeText(this, "sex is required", Toast.LENGTH_SHORT).show();
            } else {

                String userPhoneNumber = "";

                List<UsersTable> myl = UsersTable.findWithQuery(UsersTable.class, "select * from Users_table limit 1");
                for (int y = 0; y < myl.size(); y++) {

                    userPhoneNumber = myl.get(y).getPhonenumber();
                }

                String message = "VL*" + ccnumberS + "*" + patientnameS + "*" + dobS + "*" + datecollectionS + "*" + artstartS + "*"
                        + selectedCurrentRegimen + "*" + dateartregimenS + "*" + selectedArtLine + "*" + selectedJustCode + "*" + selectedType
                        + "*" + selectedSex  + "*" + labNameS + "*" + labId;

                System.out.println(labId);
                System.out.println(labNameS);
                System.out.println("**phone encrypted**********" + Base64Encoder.encryptString(userPhoneNumber) + "***message encrypted******" + Base64Encoder.encryptString(message));

                acs.submitEidVlData(Base64Encoder.encryptString(userPhoneNumber), Base64Encoder.encryptString(message));

                Toast.makeText(this, "submitting", Toast.LENGTH_SHORT).show();
            }


        } catch (Exception e) {


        }
    }


    private void setDob() {

        try {
            //            EditText ccnumber,patientname,dob,datecollection,artstart,currentregimen,dateartregimen,artline,justcode;

            dob.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    myCalendar.getInstance();
                    int day = myCalendar.get(Calendar.DAY_OF_MONTH);
                    int month = myCalendar.get(Calendar.MONTH);
                    int year = myCalendar.get(Calendar.YEAR);
                    dp = new DatePickerDialog(ViralLoadSamples.this, new DatePickerDialog.OnDateSetListener() {
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


        } catch (Exception e) {


        }
    }


    private void setArtStart() {

        try {
            //            EditText ccnumber,patientname,dob,datecollection,artstart,currentregimen,dateartregimen,artline,justcode;


            artstart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    myCalendar.getInstance();
                    int day = myCalendar.get(Calendar.DAY_OF_MONTH);
                    int month = myCalendar.get(Calendar.MONTH);
                    int year = myCalendar.get(Calendar.YEAR);
                    dp = new DatePickerDialog(ViralLoadSamples.this, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker datePicker, int year, int monthOfyear, int dayOfMonth) {
                            artstart.setText(year  + "-" + (monthOfyear +1) + "-" + dayOfMonth);
                        }
                    }, year, month, day);
                    dp.getDatePicker().setMaxDate(System.currentTimeMillis());
                    dp.show();
                    System.out.println(dp);
                    // dtp.setDatePicker(dob);


                }
            });


        } catch (Exception e) {


        }
    }


    private void setDateArtRegimen() {

        try {
            //            EditText ccnumber,patientname,dob,datecollection,artstart,currentregimen,dateartregimen,artline,justcode;


            dateartregimen.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    myCalendar.getInstance();
                    int day = myCalendar.get(Calendar.DAY_OF_MONTH);
                    int month = myCalendar.get(Calendar.MONTH);
                    int year = myCalendar.get(Calendar.YEAR);
                    dp = new DatePickerDialog(ViralLoadSamples.this, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker datePicker, int year, int monthOfyear, int dayOfMonth) {
                            dateartregimen.setText(year  + "-" + (monthOfyear +1) + "-" + dayOfMonth);
                        }
                    }, year, month, day);
                    dp.getDatePicker().setMaxDate(System.currentTimeMillis());
                    dp.show();
                    System.out.println(dp);
                    // dtp.setDatePicker(dob);


                }
            });


        } catch (Exception e) {


        }
    }


    private void setDatecollection() {

        try {
            //            EditText ccnumber,patientname,dob,datecollection,artstart,currentregimen,dateartregimen,artline,justcode;

            datecollection.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    myCalendar.getInstance();
                    int day = myCalendar.get(Calendar.DAY_OF_MONTH);
                    int month = myCalendar.get(Calendar.MONTH);
                    int year = myCalendar.get(Calendar.YEAR);
                    dp = new DatePickerDialog(ViralLoadSamples.this, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker datePicker, int year, int monthOfyear, int dayOfMonth) {
                            datecollection.setText(year  + "-" + (monthOfyear +1) + "-" + dayOfMonth);
                        }
                    }, year, month, day);
                    dp.getDatePicker().setMaxDate(System.currentTimeMillis());
                    dp.show();
                    System.out.println(dp);
                    // dtp.setDatePicker(dob);


                }
            });


        } catch (Exception e) {


        }
    }


    private void setSpinnerSexListener() {

        try {


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

        } catch (Exception e) {


        }
    }


    private void setSpinnerArtLineListener() {

        try {


            SpinnerArtLine.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {

                    selectedArtLine = SpinnerArtLine.getText().toString();

//                    Toast.makeText(Report.this, "selected "+selectedWhere, Toast.LENGTH_SHORT).show();


                }
            });

        } catch (Exception e) {


        }
    }


    private void setSpinnerJustCodeListener() {

        try {


            SpinnerJustcode.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {

                    selectedJustCode = SpinnerJustcode.getText().toString();

//                    Toast.makeText(Report.this, "selected "+selectedWhere, Toast.LENGTH_SHORT).show();


                }
            });

        } catch (Exception e) {


        }
    }


    private void setSpinnerCurrentRegimenListener() {

        try {


            SpinnerCurrentRegimen.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {

                    selectedCurrentRegimen = SpinnerCurrentRegimen.getText().toString();

//                    Toast.makeText(Report.this, "selected "+selectedWhere, Toast.LENGTH_SHORT).show();


                }
            });

        } catch (Exception e) {


        }
    }


    private void setSpinnerTypeListener() {

        try {


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

        } catch (Exception e) {


        }
    }

    private void initialise() {

        try {

//            EditText ccnumber,patientname,dob,datecollection,artstart,currentregimen,dateartregimen,artline,justcode;

            acs = new AccessServer(ViralLoadSamples.this);
            ccnumberS = "";
            patientnameS = "";
            dobS = "";
            datecollectionS = "";
            artstartS = "";

            dateartregimenS = "";


            ccnumber = (EditText) findViewById(R.id.vlsampleccnumber);
            patientname = (EditText) findViewById(R.id.vlsamplepatientname);
            dob = (EditText) findViewById(R.id.vlsampledob);
            datecollection = (EditText) findViewById(R.id.vlsampledatecollection);
            artstart = (EditText) findViewById(R.id.vlsampleartstart);

            dateartregimen = (EditText) findViewById(R.id.vlsampledateartregimen);


            dtp = new DateTimePicker(ViralLoadSamples.this);
            selectedSex = "";
            selectedType = "";
            selectedCurrentRegimen = "";
            selectedJustCode = "";
            selectedArtLine = "";

            SpinnerSex = (MaterialBetterSpinner) findViewById(R.id.vlsamplesex);
            Spinnertype = (MaterialBetterSpinner) findViewById(R.id.vlsampletype);
            SpinnerCurrentRegimen = (MaterialBetterSpinner) findViewById(R.id.vlsamplecurrentregimenselect);
            SpinnerArtLine = (MaterialBetterSpinner) findViewById(R.id.vlsampleartlineselect);
            SpinnerJustcode = (MaterialBetterSpinner) findViewById(R.id.vlsamplejustcodeselect);

            arrayAdapterSex = new ArrayAdapter<String>(this,
                    android.R.layout.simple_dropdown_item_1line, Config.SPINNERLISTSEX);
            arrayAdapterType = new ArrayAdapter<String>(this,
                    android.R.layout.simple_dropdown_item_1line, Config.SPINNERLISTSAMPLETYPE);
            arrayAdapterArtLine = new ArrayAdapter<String>(this,
                    android.R.layout.simple_dropdown_item_1line, Config.LINES);
            arrayAdapterCurrentArtRegimen = new ArrayAdapter<String>(this,
                    android.R.layout.simple_dropdown_item_1line, Config.CURRENTARTREGIMENCODES);
            arrayAdapterJustCode = new ArrayAdapter<String>(this,
                    android.R.layout.simple_dropdown_item_1line, Config.JUSTIFICATIONCODE);
        } catch (Exception e) {


        }
    }

    public void setSpinnerAdapters() {

        try {
            SpinnerSex.setAdapter(arrayAdapterSex);
            Spinnertype.setAdapter(arrayAdapterType);
            SpinnerArtLine.setAdapter(arrayAdapterArtLine);
            SpinnerCurrentRegimen.setAdapter(arrayAdapterCurrentArtRegimen);
            SpinnerJustcode.setAdapter(arrayAdapterJustCode);


        } catch (Exception e) {


        }
    }

    private void changeStatusBarColor(String color) {
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.parseColor(color));
        }
    }

    public void setToolBar() {

        try {

            Toolbar toolbar = (Toolbar) findViewById(R.id.eidvlremoteloginviralloadtoolbar);
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle("Viral Load samples");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);

        } catch (Exception e) {


        }
    }

    public void CancelVlSamples(View v) {

        try {

            clearfields();

        } catch (Exception e) {


        }


    }

    public void SubmitSample(View v) {

        try {

            validate();

        } catch (Exception e) {


        }
    }


}
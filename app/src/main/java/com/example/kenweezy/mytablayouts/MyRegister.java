package com.example.kenweezy.mytablayouts;

/**
 * Created by KENWEEZY on 2017-01-13.
 */


import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.List;


public class MyRegister extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Progress pr = new Progress();
    EditText un, pw, cpw, secqnans,phone;
    CheckBox mychkbx, policycheckbox;
    Spinner secqnsp;
    String selectedQuestion = "";
    String selectedQuestionId = "";
    String checkPolicy;
    Button rbutton;

    String[] options = {"Please select Security Question", "what is your favourite pet?", "what is your favourite food?", "what is your mothers first name?"};


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myregister);

        try {

            List<CheckRun> myl2 = CheckRun.findWithQuery(CheckRun.class, "Select * from Check_run");


            for (int y = 0; y < myl2.size(); y++) {
//                    Toast.makeText(this, ""+myl2.get(y).getFirstRun(), Toast.LENGTH_SHORT).show();
            }

            un = (EditText) findViewById(R.id.reguname);
            phone = (EditText) findViewById(R.id.regphone);
            pw = (EditText) findViewById(R.id.regpass);
            cpw = (EditText) findViewById(R.id.cregpass);
            secqnsp = (Spinner) findViewById(R.id.securityQnSpinner);
            secqnans = (EditText) findViewById(R.id.securityans);
            rbutton  = (Button) findViewById(R.id.registerButton);


            populateQuestions();
            setSpinnerListeners();


            mychkbx = (CheckBox) findViewById(R.id.regcbShowPwd);
           // policycheckbox = (CheckBox) findViewById(R.id.regAcceptPoliciy);

            mychkbx.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                    if (!isChecked) {
                        // show password
                        pw.setTransformationMethod(PasswordTransformationMethod.getInstance());
                        cpw.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    } else {
                        // hide password
                        pw.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                        cpw.setTransformationMethod(HideReturnsTransformationMethod.getInstance());

                    }
                }
            });
        } catch (Exception e) {
            Toast.makeText(this, "error checking run " + e, Toast.LENGTH_SHORT).show();
            System.out.println(" error occured checking run " + e);


        }


    }

    public void populateQuestions() {

        try {

            SpinnerAdapter customAdapter = new SpinnerAdapter(getApplicationContext(), options);

            secqnsp.setAdapter(customAdapter);

        } catch (Exception e) {


        }
    }


    public void setSpinnerListeners() {

        try {

            secqnsp.setOnItemSelectedListener(this);


        } catch (Exception e) {

        }
    }


    public void RegisterUser(View v) {

        try {


            RegisterCheck();

        } catch (Exception e) {

            LogindisplayDialog(e.getMessage());

        }
    }

    private Boolean exit = false;

    @Override
    public void onBackPressed() {


        AlertDialog.Builder b = new AlertDialog.Builder(this);

        b.setMessage("Are you sure you want to exit mLab?");
        b.setTitle("exit mLab");
        b.setIcon(android.R.drawable.ic_dialog_alert);
        b.setCancelable(false);

        b.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        b.setNeutralButton("YES", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                finish();


            }
        });

        AlertDialog a = b.create();

        a.show();

        Button bq = a.getButton(DialogInterface.BUTTON_NEGATIVE);
        Button bn = a.getButton(DialogInterface.BUTTON_NEUTRAL);
        bq.setTextColor(Color.RED);
        bn.setTextColor(Color.BLUE);
    }

    public boolean MydialogBuilder(final String message, final String title) {
        final boolean[] exiting = {false};
        AlertDialog.Builder b = new AlertDialog.Builder(this);

        b.setMessage(message + "\n");
        b.setTitle(title);
        b.setCancelable(false);

        b.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                exiting[0] = false;
                dialog.cancel();
            }
        });

        b.setNeutralButton("YES", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                exiting[0] = true;

            }
        });

        AlertDialog a = b.create();

        a.show();

        Button bq = a.getButton(DialogInterface.BUTTON_NEGATIVE);
        Button bn = a.getButton(DialogInterface.BUTTON_NEUTRAL);
        bq.setTextColor(Color.RED);
        bn.setTextColor(Color.BLUE);
        return exiting[0];
    }






    public void privacyPolicyURL(View view){

        Uri uri = Uri.parse("https://mlab.mhealthkenya.co.ke/terms");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

    public void RegisterCheck() {
        pr.progressing(this, "Validating Registeration Details", "Register Validation");


        try {
            String myphone = phone.getText().toString();
            String myun = un.getText().toString();
            String mypass = pw.getText().toString();
            String cmypass = cpw.getText().toString();
            String ans = secqnans.getText().toString();

            if (myphone.isEmpty()) {
                pr.DissmissProgress();
                phone.setError("Phonenumber is required");

            }
            else if (myun.isEmpty()) {
                pr.DissmissProgress();
                un.setError("Username is required");

            } else if (mypass.isEmpty()) {
                pr.DissmissProgress();
                pw.setError("Password is required");

            } else if (cmypass.isEmpty()) {
                pr.DissmissProgress();
                pw.setError("confirm Password is required");

            } else if ((!cmypass.isEmpty()) && (!cmypass.contentEquals(mypass))) {
                pr.DissmissProgress();
                cpw.setError("passwords do not match");

            } else if (selectedQuestionId.contentEquals("0")) {
                Toast.makeText(this, "please select a security question", Toast.LENGTH_SHORT).show();


            } else if (ans.trim().isEmpty()) {

                Toast.makeText(this, "please specify your security question answer", Toast.LENGTH_SHORT).show();
            } else {

                UsersTable ut = new UsersTable(myun, mypass, selectedQuestion, ans,myphone);
                ut.save();
                CheckRun cr = new CheckRun("runned");
                cr.save();
                pr.DissmissProgress();

                RegistrationConf("Choose Your Action ?", "REGISTRATION SUCCESSFUL");


            }
        } catch (Exception e) {
            pr.DissmissProgress();
            LogindisplayDialog(e.getMessage());

        }
    }


    public void RegistrationConf(final String message, final String title) {

        AlertDialog.Builder b = new AlertDialog.Builder(this);

        b.setMessage(message + "\n");
        b.setTitle(title);
        b.setCancelable(false);

        b.setNegativeButton("CONTINUE TO LOGIN", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent i = new Intent(getApplicationContext(), Mylogin.class);
                // Closing all the Activities
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
                finish();
                dialog.cancel();
            }
        });

        b.setPositiveButton("EXIT APP", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                dialog.cancel();
                finish();

            }
        });

        AlertDialog a = b.create();

        a.show();

        Button bq = a.getButton(DialogInterface.BUTTON_NEGATIVE);
        Button bn = a.getButton(DialogInterface.BUTTON_NEUTRAL);
        Button bP = a.getButton(DialogInterface.BUTTON_POSITIVE);
        bq.setTextColor(Color.GREEN);
        bn.setTextColor(Color.BLUE);
        bP.setTextColor(Color.RED);

    }


    public void LogindisplayDialog(String message) {

        try {

            AlertDialog.Builder adb = new AlertDialog.Builder(this);
            adb.setTitle("REGISTER ERROR");
            adb.setIcon(R.mipmap.error);
            adb.setMessage(message.toUpperCase());
            adb.setCancelable(false);

            adb.setPositiveButton("Try Again", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    // Toast.makeText(Login.this,message,Toast.LENGTH_LONG).show();

                }
            });


            AlertDialog mydialog = adb.create();
            mydialog.show();
        } catch (Exception e) {


        }

    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        try {


            Spinner spin = (Spinner) parent;

            if (spin.getId() == R.id.securityQnSpinner) {


                selectedQuestion = parent.getSelectedItem().toString();
                selectedQuestionId = Integer.toString(position);
//                Toast.makeText(this, "selected "+selectedQuestion, Toast.LENGTH_SHORT).show();

            }

        } catch (Exception e) {


        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}


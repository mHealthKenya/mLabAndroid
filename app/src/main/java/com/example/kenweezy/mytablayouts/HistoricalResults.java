package com.example.kenweezy.mytablayouts;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.kenweezy.mytablayouts.AccessServer.AccessServer;
import com.example.kenweezy.mytablayouts.Checkinternet.CheckInternet;
import com.example.kenweezy.mytablayouts.Config.Config;
//import com.example.kenweezy.mytablayouts.SSLTrustCertificate.SSLTrust;
import com.example.kenweezy.mytablayouts.encryption.Base64Encoder;
import com.example.kenweezy.mytablayouts.encryption.MCrypt;
import com.example.kenweezy.mytablayouts.sendmessages.SendMessage;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by kennedy on 9/11/17.
 */

public class HistoricalResults extends AppCompatActivity {

    EditText frmw, tow, mflcode;
    DatePickerDialog datePickerDialog;
    Myshortcodes msc = new Myshortcodes();
    MCrypt mcrypt = new MCrypt();

    SendMessage sm;
    Base64Encoder encoder;
    AccessServer acs;
    CheckInternet chk;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.historical_results);
        setToolBar();
        initialise();
        mflcodeInpuListener();

        //SSLTrust.nuke();

        setDatePickerFrm();
        checkFrmDateListener();
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


    public void initialise() {

        try {

            frmw = (EditText) findViewById(R.id.histfilter_frmweek);
            tow = (EditText) findViewById(R.id.histfilter_toweek);
            mflcode = (EditText) findViewById(R.id.histfilter_mflcode);
            sm = new SendMessage(HistoricalResults.this);
            encoder = new Base64Encoder();
            chk=new CheckInternet(HistoricalResults.this);
            acs=new AccessServer(HistoricalResults.this);


        } catch (Exception e) {


        }
    }

    public void mflcodeInpuListener() {

        try {

            mflcode.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {


                    if (mflcode.getText().toString().trim().isEmpty()) {
                        frmw.setEnabled(false);


                    } else {

                        frmw.setEnabled(true);


                    }


                }
            });
        } catch (Exception e) {


        }


    }

    public void setDatePickerFrm() {

        try {

            frmw.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // calender class's instance and get current date , month and year from calender
                    final Calendar c = Calendar.getInstance();
                    int mYear = c.get(Calendar.YEAR); // current year
                    int mMonth = c.get(Calendar.MONTH); // current month
                    int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                    // date picker dialog
                    datePickerDialog = new DatePickerDialog(HistoricalResults.this,
                            new DatePickerDialog.OnDateSetListener() {

                                @Override
                                public void onDateSet(DatePicker view, int year,
                                                      int monthOfYear, int dayOfMonth) {
                                    // set day of month , month and year value in the edit text
                                    frmw.setText(dayOfMonth + "/"
                                            + (monthOfYear + 1) + "/" + year);

                                }
                            }, mYear, mMonth, mDay);
                    datePickerDialog.show();
                }
            });
        } catch (Exception e) {


        }


    }


    public void setDatePickerTo() {

        try {

            tow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // calender class's instance and get current date , month and year from calender
                    final Calendar c = Calendar.getInstance();
                    int mYear = c.get(Calendar.YEAR); // current year
                    int mMonth = c.get(Calendar.MONTH); // current month
                    int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                    // date picker dialog
                    datePickerDialog = new DatePickerDialog(HistoricalResults.this,
                            new DatePickerDialog.OnDateSetListener() {

                                @Override
                                public void onDateSet(DatePicker view, int year,
                                                      int monthOfYear, int dayOfMonth) {
                                    // set day of month , month and year value in the edit text
                                    tow.setText(dayOfMonth + "/"
                                            + (monthOfYear + 1) + "/" + year);

                                }
                            }, mYear, mMonth, mDay);
                    datePickerDialog.show();
                }
            });
        } catch (Exception e) {


        }


    }


    public void ValidateToDate() {

        try {

            String frmweeks = frmw.getText().toString().trim();
            if (!frmweeks.isEmpty()) {

                tow.setEnabled(true);
                setDatePickerTo();
                checkToDateListener();

            } else {
                tow.setText("");
                frmw.setEnabled(false);
                tow.setEnabled(false);
            }
        } catch (Exception e) {


        }
    }


    public void checkToDateListener() {

        try {

            tow.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//                    Toast.makeText(getActivity(), "before change", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
//                    Toast.makeText(getActivity(), "on change", Toast.LENGTH_SHORT).show();


                }

                @Override
                public void afterTextChanged(Editable s) {

//                    Toast.makeText(getActivity(), "after change", Toast.LENGTH_SHORT).show();

                    try {


                        String timeStamp = new SimpleDateFormat("yyyy.MM.dd").format(new Date());
                        String currentArray[] = timeStamp.split("\\.");
                        String currentDate = currentArray[2];
                        String currentMonth = currentArray[1];
                        String currentYear = currentArray[0];

                        int cdateI = Integer.parseInt(currentDate);
                        int cmnthI = Integer.parseInt(currentMonth);
                        int cyearI = Integer.parseInt(currentYear);


                        String frmweeks = frmw.getText().toString().trim();

                        System.out.println("***from weeks is**" + frmweeks);
                        String[] weekarray = frmweeks.split("/");
                        String edate = weekarray[0];
                        String emnth = weekarray[1];
                        String eyear = weekarray[2];

                        int edateI = Integer.parseInt(edate);
                        int emnthI = Integer.parseInt(emnth);
                        int eyearI = Integer.parseInt(eyear);


                        String toweeks = tow.getText().toString().trim();

                        System.out.println("***to weeks is**" + toweeks);
                        String[] toweekarray = toweeks.split("/");
                        String toedate = toweekarray[0];
                        String toemnth = toweekarray[1];
                        String toeyear = toweekarray[2];

                        int toedateI = Integer.parseInt(toedate);
                        int toemnthI = Integer.parseInt(toemnth);
                        int toeyearI = Integer.parseInt(toeyear);


                        if (toeyearI == cyearI && toemnthI == cmnthI && toedateI > cdateI) {

                            tow.setText("");
                            Toast.makeText(getApplicationContext(), "choose a date less than today", Toast.LENGTH_SHORT).show();


                        } else if (toeyearI == cyearI && toemnthI > cmnthI) {

                            tow.setText("");
                            Toast.makeText(getApplicationContext(), "choose a date less than today", Toast.LENGTH_SHORT).show();


                        } else if (toeyearI > cyearI) {

                            tow.setText("");
                            Toast.makeText(getApplicationContext(), "choose a date less than today", Toast.LENGTH_SHORT).show();

                        } else if (toeyearI == eyearI && toemnthI == emnthI && toedateI < edateI) {
                            tow.setText("");
                            Toast.makeText(getApplicationContext(), "choose a date greater than from week", Toast.LENGTH_SHORT).show();
                        } else if (toeyearI == eyearI && toemnthI < emnthI) {
                            tow.setText("");
                            Toast.makeText(getApplicationContext(), "choose a date greater than from week", Toast.LENGTH_SHORT).show();
                        } else if (toeyearI < eyearI) {

                            tow.setText("");
                            Toast.makeText(getApplicationContext(), "choose a date greater than from week", Toast.LENGTH_SHORT).show();
                        } else {

//
                            String myfrm = frmw.getText().toString();
                            String mytow = tow.getText().toString();

                            if (!checkResultsExistance(myfrm, mytow)) {


                                String mymflcode = mflcode.getText().toString().trim();
                                String sendMessage = "histr*" + mymflcode + "*" + myfrm + "*" + mytow;

                                String userPhoneNumber="";

                                List<UsersTable> myl=UsersTable.findWithQuery(UsersTable.class,"select * from Users_table limit 1");
                                for(int y=0;y<myl.size();y++){

                                    userPhoneNumber=myl.get(y).getPhonenumber();
                                }

                                Toast.makeText(HistoricalResults.this, ""+userPhoneNumber, Toast.LENGTH_SHORT).show();

                                if(chk.isInternetAvailable()){

                                    acs.getHistoricalResultsFromDb(userPhoneNumber,sendMessage);
                                }
                                else{

                                    sm.sendMessageApi(encoder.encryptString(sendMessage), msc.sendSmsShortcode);


                                }



                                Toast.makeText(HistoricalResults.this, "Request for historical results was successful", Toast.LENGTH_SHORT).show();
                                tow.setText("");
                                frmw.setText("");
                                tow.setEnabled(false);
                                frmw.setEnabled(false);
                                mflcode.setText("");


                            } else {

                                Toast.makeText(HistoricalResults.this, "Results already exist, check your existing results", Toast.LENGTH_SHORT).show();
                                tow.setText("");
                                frmw.setText("");
                                tow.setEnabled(false);
                                frmw.setEnabled(false);
                                mflcode.setText("");

                            }


                        }


                    } catch (Exception e) {


                    }


                }
            });
        } catch (Exception e) {


        }
    }


    public boolean checkResultsExistance(String frmweek, String toweek) {
        boolean resultExists = false;

        try {


            List<Messages> bdy = Messages.findWithQuery(Messages.class, "Select * from Messages group by m_body", null);

            for (int x = 0; x < bdy.size(); x++) {


                String ndate = bdy.get(x).getmTimeStamp();

                String[] checkSplitdate = ndate.split("/");


                if (checkSplitdate.length > 1) {

                } else {
                    DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss.SSS");
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTimeInMillis(Long.parseLong(ndate));
                    ndate = formatter.format(calendar.getTime());

                }

                String mynewndate[] = ndate.split("\\s+");
                String thendate = mynewndate[0];
                String thendatesplit[] = thendate.split("/");

                String theday = thendatesplit[0];
                String themnth = thendatesplit[1];
                String theyear = thendatesplit[2];


                String fromthedate[] = frmweek.split("/");
                String tothedate[] = toweek.split("/");

                String frmday = fromthedate[0];
                String frmmnth = fromthedate[1];
                String frmyear = fromthedate[2];

                String today = tothedate[0];
                String tomnth = tothedate[1];
                String toyear = tothedate[2];

                int thedayi = Integer.parseInt(theday);
                int themnthi = Integer.parseInt(themnth);
                int theyeari = Integer.parseInt(theyear);

                int frmdayi = Integer.parseInt(frmday);
                int frmmnthi = Integer.parseInt(frmmnth);
                int frmyeari = Integer.parseInt(frmyear);

                int todayi = Integer.parseInt(today);
                int tomnthi = Integer.parseInt(tomnth);
                int toyeari = Integer.parseInt(toyear);


                if (theyeari == toyeari && theyeari == frmyeari && themnthi == tomnthi && themnthi == frmmnthi && thedayi <= todayi && thedayi >= frmdayi) {

                    resultExists = true;


                } else if (theyeari == toyeari && theyeari == frmyeari && themnthi <= tomnthi && themnthi >= frmmnthi) {

                    resultExists = true;

                } else if (theyeari < toyeari && theyeari > frmyeari) {

                    resultExists = true;


                } else {

                    resultExists = false;
                }


            }


        } catch (Exception e) {

            resultExists = false;


        }

        return resultExists;
    }


    public void checkFrmDateListener() {

        try {

            frmw.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//                    Toast.makeText(getActivity(), "before change", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
//                    Toast.makeText(getActivity(), "on change", Toast.LENGTH_SHORT).show();


                }

                @Override
                public void afterTextChanged(Editable s) {

//                    DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
//                    Calendar cal = Calendar.getInstance();
//                    System.out.println("**current date is**"+dateFormat.format(cal)); //2016/11/16 12:08:43


                    try {


                        String timeStamp = new SimpleDateFormat("yyyy.MM.dd").format(new Date());
                        String currentArray[] = timeStamp.split("\\.");
                        String currentDate = currentArray[2];
                        String currentMonth = currentArray[1];
                        String currentYear = currentArray[0];
                        System.out.println("**** the current date is *****" + timeStamp);

                        String frmweeks = frmw.getText().toString().trim();

                        System.out.println("***from weeks is**" + frmweeks);
                        String[] weekarray = frmweeks.split("/");
                        String edate = weekarray[0];
                        String emnth = weekarray[1];
                        String eyear = weekarray[2];

                        int edateI = Integer.parseInt(edate);
                        int emnthI = Integer.parseInt(emnth);
                        int eyearI = Integer.parseInt(eyear);

                        int cdateI = Integer.parseInt(currentDate);
                        int cmnthI = Integer.parseInt(currentMonth);
                        int cyearI = Integer.parseInt(currentYear);

                        if (eyearI == cyearI && emnthI == cmnthI && edateI > cdateI) {

                            frmw.setText("");
                            Toast.makeText(getApplicationContext(), "choose a date less than today", Toast.LENGTH_SHORT).show();
                            ValidateToDate();


                        } else if (eyearI == cyearI && emnthI > cmnthI) {

                            frmw.setText("");
                            Toast.makeText(getApplicationContext(), "choose a date less than today", Toast.LENGTH_SHORT).show();
                            ValidateToDate();


                        } else if (eyearI > cyearI) {

                            frmw.setText("");
                            Toast.makeText(getApplicationContext(), "choose a date less than today", Toast.LENGTH_SHORT).show();
                            ValidateToDate();


                        } else {

                            ValidateToDate();

                        }


                    } catch (Exception e) {


                    }


                }
            });
        } catch (Exception e) {


        }
    }


    public void setToolBar() {

        try {

            Toolbar toolbar = (Toolbar) findViewById(R.id.histrestoolbar);
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle("HISTORICAL RESULTS");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        } catch (Exception e) {


        }
    }
}

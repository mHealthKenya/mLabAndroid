package com.example.kenweezy.mytablayouts;

/**
 * Created by KENWEEZY on 2017-01-13.
 */


import android.Manifest;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.provider.Telephony;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.telephony.TelephonyManager;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kenweezy.mytablayouts.Config.Config;
import com.example.kenweezy.mytablayouts.encryption.Base64Encoder;
import com.example.kenweezy.mytablayouts.encryption.MCrypt;
import com.example.kenweezy.mytablayouts.getsimdetails.getSimDetails;
import com.example.kenweezy.mytablayouts.sendmessages.SendMessage;
import com.facebook.stetho.Stetho;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;


public class Mylogin extends AppCompatActivity {

    MCrypt mcrypt = new MCrypt();

    TextView fp;
    getSimDetails gsd;

    private static final int PERMS_REQUEST_CODE = 12345;

    Myshortcodes msc = new Myshortcodes();

    public static final String KEY_EMAIL = "email";
    public static final String KEY_PASSWORD = "password";
    Progress pr = new Progress();

    public static final String MyPREFERENCES = "MyPrefs";
    public static final String USERNAME = "unameKey";

    String myPackageName;
    EditText un, pw;
    CheckBox mychkbx;
    public static final String UNAME = "UNAME";
    public static final String LOGGED_IN = "logged_in";
    SharedPreferences sharedpreferences;
    public static final String SETTING_INFOS = "SETTING_Infos";

    SendMessage sm;
    Base64Encoder encoder;


    private void initialise() {

        sm = new SendMessage(Mylogin.this);
        encoder = new Base64Encoder();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mylogin);

        myPackageName = getApplicationContext().getPackageName();
        initialise();

//        checkMessageCount();

//        checkDefaultApp();


//        refreshSmsInboxTestCount();
//        printSimDetails();
//        showContacts();
        loadMessages();

//        getContacts2();
        String getName = "";
        Intent intent = getIntent();
        Bundle bd = intent.getExtras();
        if (bd != null) {
            getName = (String) bd.get("msg");
            if (getName == null) {
//                Toast.makeText(this, "its empty", Toast.LENGTH_SHORT).show();
            } else {

                if (getName.contentEquals("my alarm")) {
//                    Toast.makeText(this, "tibimm working", Toast.LENGTH_SHORT).show();
                    sendReadReport();
                } else {
//                    Toast.makeText(this, "not tibimm", Toast.LENGTH_SHORT).show();
                }

            }

        } else {

//            Toast.makeText(this, "null test", Toast.LENGTH_SHORT).show();
        }


//        checkReceiver();

        try {

            /**************************************checking user interaction for first time*/
            long now = new Date().getTime();
            List<UserTimeOut> ml = UserTimeOut.findWithQuery(UserTimeOut.class, "Select * from User_time_out");
            if (ml.size() == 0) {
                UserTimeOut ut = new UserTimeOut(Long.toString(now));
                ut.save();

            } else {
                UserTimeOut myut = UserTimeOut.findById(UserTimeOut.class, 1);
                myut.lasttime = Long.toString(now);
                myut.save();


            }
//            Toast.makeText(this, "user interacted at "+now, Toast.LENGTH_SHORT).show();

            /**************************************checking user interaction for first time*/


            List<CheckRun> myl2 = CheckRun.findWithQuery(CheckRun.class, "Select * from Check_run");

            if (myl2.size() == 0) {


//                Toast.makeText(this, "first run", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getApplicationContext(), MyRegister.class);
                // Closing all the Activities
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
                finish();


            } else {

                for (int y = 0; y < myl2.size(); y++) {
//                    Toast.makeText(this, ""+myl2.get(y).getFirstRun(), Toast.LENGTH_SHORT).show();
                }

//                Toast.makeText(this, "second run", Toast.LENGTH_SHORT).show();

                un = (EditText) findViewById(R.id.uname);
                pw = (EditText) findViewById(R.id.pass);
                fp = (TextView) findViewById(R.id.forgotpass);

                forgotPassListener();


                SharedPreferences settings = getSharedPreferences(SETTING_INFOS, 0);

                String name = settings.getString(UNAME, "");
                un.setText(name);


                mychkbx = (CheckBox) findViewById(R.id.cbShowPwd);


                mychkbx.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                        if (!isChecked) {
                            // show password
                            pw.setTransformationMethod(PasswordTransformationMethod.getInstance());
                        } else {
                            // hide password
                            pw.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                        }
                    }
                });


            }
        } catch (Exception e) {

            Toast.makeText(this, "error checking run " + e, Toast.LENGTH_SHORT).show();
            System.out.println(" error occured checking run " + e);


        }

//        sendReadReport();
        Stetho.initializeWithDefaults(this);
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

    public void loadMessages() {

        try {

            System.out.println("********load messages*****");


        } catch (Exception e) {


        }
    }


    public void printSimDetails() {

        TelephonyManager tMgr = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        String operator = tMgr.getSimOperator(); // this returns the MCC+MNC
        /* Do whatever checks you need here */
        Toast.makeText(this, "" + operator, Toast.LENGTH_SHORT).show();
    }

    public void forgotPassListener() {

        try {

            fp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent myint = new Intent(Mylogin.this, Forgotpassword.class);
                    startActivity(myint);

                }
            });
        } catch (Exception e) {


        }
    }

    public void checkMessageCount() {

        try {

            List<Messages> myl = Messages.findWithQuery(Messages.class, "select * from messages", null);
            List<Messages> myl1 = Messages.findWithQuery(Messages.class, "select * from messages group by m_body", null);

            Toast.makeText(this, "all messages " + myl.size(), Toast.LENGTH_SHORT).show();
            Toast.makeText(this, "grouped messages " + myl1.size(), Toast.LENGTH_SHORT).show();

        } catch (Exception e) {


        }
    }


    public void checkDefaultApp() {


        try {


            if (!Telephony.Sms.getDefaultSmsPackage(this).equals(myPackageName)) {

                DefaultAppDialog("We request you to make Mlab the default Messaging application for other features to work..");


            } else {

            }


        } catch (Exception e) {


        }
    }

    @Override
    public void onUserInteraction() {
        super.onUserInteraction();

        /**************************************checking user interaction for first time*/
        long now = new Date().getTime();
        List<UserTimeOut> ml = UserTimeOut.findWithQuery(UserTimeOut.class, "Select * from User_time_out");
        if (ml.size() == 0) {
            UserTimeOut ut = new UserTimeOut(Long.toString(now));
            ut.save();

        } else {
            UserTimeOut myut = UserTimeOut.findById(UserTimeOut.class, 1);
            myut.lasttime = Long.toString(now);
            myut.save();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();


        List myl2 = CheckRun.findWithQuery(CheckRun.class, "Select * from Check_run");

        if (myl2.size() == 0) {

        } else {

            SharedPreferences settings = getSharedPreferences(SETTING_INFOS, 0);
            SharedPreferences.Editor myedit = settings.edit();
            myedit.putString(UNAME, un.getText().toString());
            myedit.putString(LOGGED_IN, "false");
            myedit.commit();


        }
    }


    public void LoginUser(View v) {

        try {


            loginCheck();

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


//        new AlertDialog.Builder(this)
//                .setIcon(android.R.drawable.ic_dialog_alert)
//                .setTitle("Closing Activity")
//                .setMessage("Are you sure you want to close this activity?")
//                .setPositiveButton("Yes", new DialogInterface.OnClickListener()
//                {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        finish();
//                    }
//
//                })
//                .setNegativeButton("No", null)
//                .show();
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

    public void loginCheck() {
        pr.progressing(this, "Validating login Details", "Login Validation");

        try {
            String myun = un.getText().toString();
            String mypass = pw.getText().toString();

            if (myun.isEmpty()) {
                pr.DissmissProgress();
                un.setError("Username is required");

            } else if (mypass.isEmpty()) {
                pr.DissmissProgress();
                pw.setError("Password is required");

            } else {

                List<UsersTable> myl3 = UsersTable.findWithQuery(UsersTable.class, "Select * from Users_table");
                String storedUn = "";
                String storedPass = "";
                for (int t = 0; t < myl3.size(); t++) {
                    storedUn = myl3.get(t).getUsername();
                    storedPass = myl3.get(t).getPassword();
                }

                if (myun.contentEquals(storedUn) && mypass.contentEquals(storedPass)) {
                    pr.DissmissProgress();


                    if (hasPermissions()) {

//                        getContentResolver().delete(Uri.parse("content://sms"), "address=?", new String[] {msc.mainShortcode});
                        Intent myint = new Intent(getApplicationContext(), Options.class);

                        SharedPreferences settings = getSharedPreferences(SETTING_INFOS, 0);
                        SharedPreferences.Editor myedit = settings.edit();
                        myedit.putString(LOGGED_IN, "true");
                        myedit.commit();

                        startActivity(myint);

                    } else {

                        requestPerms();
                    }


                } else {
                    pr.DissmissProgress();
                    LogindisplayDialog("ERROR LOGGING IN");

                }

            }
        } catch (Exception e) {
            pr.DissmissProgress();
            LogindisplayDialog(e.getMessage());

        }
    }

//    public void checkReceiver(){
//
//        AlarmManager alarmMgr = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
//        Intent intent = new Intent(this, Mylogin.class);
//        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0);
//        Calendar time = Calendar.getInstance();
//        time.setTimeInMillis(System.currentTimeMillis());
//        time.add(Calendar.SECOND, 30);
////        alarmMgr.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
////                SystemClock.elapsedRealtime() + time.getTimeInMillis(),
////                (AlarmManager.INTERVAL_HALF_HOUR)/8, pendingIntent);
//
//        alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP, time.getTimeInMillis(),10, pendingIntent);
////        alarmMgr.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, time.getTimeInMillis(), time.getTimeInMillis(), pendingIntent);
//    }


    public void LogindisplayDialog(String message) {

        try {

            AlertDialog.Builder adb = new AlertDialog.Builder(this);
            adb.setTitle("LOGIN ERROR");
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


    public void DefaultAppDialog(String message) {

        final String myPackageName = getPackageName();

        try {

            AlertDialog.Builder adb = new AlertDialog.Builder(this);
            adb.setTitle("DEAFULT MESSAGING APPLICATION");
            adb.setIcon(R.mipmap.logo);
            adb.setMessage(message.toUpperCase());
            adb.setCancelable(false);

            adb.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {


                    Intent intent =
                            new Intent(Telephony.Sms.Intents.ACTION_CHANGE_DEFAULT);
                    intent.putExtra(Telephony.Sms.Intents.EXTRA_PACKAGE_NAME,
                            myPackageName);
                    startActivity(intent);

                    // Toast.makeText(Login.this,message,Toast.LENGTH_LONG).show();

                }
            });

            adb.setNegativeButton("NO", new DialogInterface.OnClickListener() {
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


    //check permissions granted at runtime in api 23 and above
    @SuppressLint("WrongConstant")
    private boolean hasPermissions() {


        int res = 0;

        String[] permissions = new String[]{
                Manifest.permission.INTERNET


        };

        for (String perms : permissions) {
            res = checkCallingOrSelfPermission(perms);

            if (!(res == PackageManager.PERMISSION_GRANTED)) {
                return false;
            }

        }
        return true;


    }
//end check permissions

    private void requestPerms() {

//        Intent intent = new Intent(Telephony.Sms.Intents.ACTION_CHANGE_DEFAULT);
//        intent.putExtra(Telephony.Sms.Intents.EXTRA_PACKAGE_NAME, "com.example.kenweezy.mytablayouts");

        String[] permissions = new String[]{

                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                android.Manifest.permission.INTERNET
        };

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permissions, PERMS_REQUEST_CODE);
//            if(!Telephony.Sms.getDefaultSmsPackage(getApplicationContext()).equals("com.example.kenweezy.mytablayouts")) {
//                Intent intent2 = new Intent(Telephony.Sms.Intents.ACTION_CHANGE_DEFAULT);
//                intent2.putExtra(Telephony.Sms.Intents.EXTRA_PACKAGE_NAME,
//                        "com.example.kenweezy.mytablayouts");
//                startActivity(intent2);
////
//            }

            NotificationCompat.Builder builder = new NotificationCompat.Builder(
                    getApplicationContext());
            builder.setAutoCancel(true);

        }

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            if(!Telephony.Sms.getDefaultSmsPackage(getApplicationContext()).equals(getApplicationContext().getPackageName())) {
//                Intent intent2 = new Intent(Telephony.Sms.Intents.ACTION_CHANGE_DEFAULT);
//                intent2.putExtra(Telephony.Sms.Intents.EXTRA_PACKAGE_NAME,
//                        getApplicationContext().getPackageName());
//                 startActivity(intent2);
//
//            }
//        }

    }


    public void sendReadReport() {

        try {

            int read = 0;
            int todayReceived = 0;
            int todayRead = 0;
            String unread = "";
            String tstamp = "";
            int allmesages = 0;
            String dt = "";
            String mth = "";
            String yr = "";

            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
//            System.out.println("testing "+timestamp);
            String x = timestamp.toString();
            String[] spliting = x.split("\\s+");
            System.out.println("testing new " + spliting[0]);
            String mydate = spliting[0];
            String[] splitedMydate = mydate.split("-");
            dt = splitedMydate[2];
            mth = splitedMydate[1];
            yr = splitedMydate[0];


            try {


//            getContentResolver().delete(Uri.parse("content://sms/inbox"), "address=? or address=?", new String[] {"+254713559850", "0713559850"});
            } catch (Exception e) {

                Toast.makeText(this, "error occured " + e, Toast.LENGTH_SHORT).show();
                AlertDialog.Builder myd = new AlertDialog.Builder(this);
                myd.setMessage(e.toString());
                myd.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });

                AlertDialog ad = myd.create();
                ad.show();
            }


            List<Messages> myl2 = Messages.findWithQuery(Messages.class, "Select * from Messages group by m_body", null);
            for (int y = 0; y < myl2.size(); y++) {
                allmesages = myl2.size();
                tstamp = myl2.get(y).getmTimeStamp();

                String[] splitedDate = tstamp.split("/");
                String yeararr = splitedDate[2];//returns the string like 2017 11:10:30.366
                String[] myyeararr = yeararr.split("\\s+");//split the white space to get only the year e.g 2017


                String mymnth = splitedDate[1];
                String myyear = myyeararr[0];
                String myday = splitedDate[0];

                if (mymnth.contentEquals(mth) && myyear.contentEquals(yr) && myday.contentEquals(dt)) {

                    todayReceived += 1;
                }


            }

            List<Messages> myl3 = Messages.findWithQuery(Messages.class, "Select * from Messages where read=? group by m_body", "read");
            for (int y = 0; y < myl3.size(); y++) {
                read = myl3.size();

                tstamp = myl3.get(y).getDateRead();


                String[] spliting2 = tstamp.split("\\s+");
                System.out.println("testing new " + spliting2[0]);
                String mydate1 = spliting2[0];

                String[] splitedMydate1 = mydate1.split("-");
                dt = splitedMydate1[2];
                mth = splitedMydate1[1];
                yr = splitedMydate1[0];


                Timestamp timestampnow = new Timestamp(System.currentTimeMillis());
//            System.out.println("testing "+timestamp);
                String xnow = timestampnow.toString();
                String[] splitingnow = xnow.split("\\s+");
                System.out.println("testing new " + splitingnow[0]);
                String mydatenow = spliting[0];
                String[] splitedMydatenow = mydatenow.split("-");
                String dt1 = splitedMydatenow[2];
                String mth1 = splitedMydatenow[1];
                String yr1 = splitedMydatenow[0];


                if (mth1.contentEquals(mth) && yr1.contentEquals(yr) && dt1.contentEquals(dt)) {

                    todayRead += 1;
                }

            }
            String messReport = "mlab" + "*" + mydate + "*" + todayReceived + "*" + todayRead + "*" + allmesages + "*" + read;

            sm.sendMessageApi(encoder.encryptString(messReport), msc.sendSmsShortcode);


        } catch (Exception e) {

        }
    }


}


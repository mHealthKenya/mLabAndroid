package com.example.kenweezy.mytablayouts.AddClient;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Telephony;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.appcompat.widget.Toolbar;
import android.telephony.SmsManager;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.kenweezy.mytablayouts.Config.Config;
import com.example.kenweezy.mytablayouts.Mylogin;
import com.example.kenweezy.mytablayouts.Myshortcodes;
import com.example.kenweezy.mytablayouts.Progress;
import com.example.kenweezy.mytablayouts.R;
import com.example.kenweezy.mytablayouts.UserTimeOut;
import com.example.kenweezy.mytablayouts.encryption.MCrypt;
import com.example.kenweezy.mytablayouts.sendmessages.SendMessage;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by abdi on 07/03/2017.
 */

public class Register extends AppCompatActivity {
    private static final int PERMS_REQUEST_CODE=12345;

    private Toolbar toolbar;

    Myshortcodes msc=new Myshortcodes();
    MCrypt mcrypt=new MCrypt();
    SendMessage sm;


    public static final String KEY_CCNO = "cc no";
    public static final String KEY_FNAME = "first name";
    public static final String KEY_LNAME = "last name";
    public static final String KEY_PNO = "phone number";
    String consent="";
    private RadioGroup radioSexGroup;
    private RadioButton radioConsentButton;
    Progress pr=new Progress();

    public static final String MyPREFERENCES = "MyPrefs";
    public static final String USERNAME = "unameKey";
    EditText cc,fn,ln,pn;
    CheckBox mychkbx;
    public static final String CCNO = "CCNO";
    public static final String FNAME = "FNAME";
    public static final String LNAME = "LNAME";
    public static final String PNO = "PNO";
    public static final String LOGGED_IN = "logged_in";
    SharedPreferences sharedpreferences;
    public static final String SETTING_INFOS = "SETTING_Infos";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        toolbar = (Toolbar) findViewById(R.id.toolbarreg);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Register Client");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        cc=(EditText) findViewById(R.id.ccno);
        fn=(EditText) findViewById(R.id.fname);
        ln=(EditText) findViewById(R.id.lname);
        pn=(EditText) findViewById(R.id.pno);
        sm = new SendMessage(Register.this);

        changeStatusBarColor();


        SharedPreferences settings = getSharedPreferences(SETTING_INFOS, 0);






    }


    private void changeStatusBarColor(){
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.parseColor(Config.statusBarColor));
        }
    }


    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radio_results:
                if (checked)
                   radioConsentButton=(RadioButton) findViewById(R.id.radio_results);
                   consent=radioConsentButton.getText().toString();
//                   Toast.makeText(this, "you checked on "+consent, Toast.LENGTH_SHORT).show();
                    break;
            case R.id.radio_notification:
                if (checked)
                    radioConsentButton=(RadioButton) findViewById(R.id.radio_notification);
                    consent=radioConsentButton.getText().toString();
//                    Toast.makeText(this, "you checked on "+consent, Toast.LENGTH_SHORT).show();
                    break;
        }
    }



    @Override
    protected void onStop() {
        super.onStop();
        SharedPreferences settings = getSharedPreferences(SETTING_INFOS, 0);
        SharedPreferences.Editor myedit=settings.edit();
        myedit.putString(CCNO,cc.getText().toString());
        myedit.putString(FNAME,fn.getText().toString());
        myedit.putString(LNAME,ln.getText().toString());
        myedit.putString(PNO,pn.getText().toString());
        myedit.putString(LOGGED_IN,"false");
        myedit.commit();
    }


    /**public void LoginUser(View v){

     try{




     registerCheck();

     }
     catch(Exception e){

     regsiterdisplayDialog(e.getMessage());

     }
     } */


    public void registerCheck(View v){
        pr.progressing(this,"Validating register Details","register Validation");

        try {
            String myccno = cc.getText().toString();
            String myfn = fn.getText().toString();
            String myln = ln.getText().toString();
            String mypn = pn.getText().toString();


            if (myccno.isEmpty()) {
                pr.DissmissProgress();
                cc.setError("ccc no is required"); //username is required

            } else if (myfn.isEmpty()) {
                pr.DissmissProgress();
                fn.setError("first name is required");  //password is required

            }else if (myln.isEmpty()) {
                pr.DissmissProgress();
                ln.setError("last name is required");  //password is required

            }else if (mypn.isEmpty()) {
                pr.DissmissProgress();
                pn.setError("phone number is required");  //password is required

            }
            else if (mypn.length()!=10) {
                pr.DissmissProgress();
                pn.setError("Enter a valid phone number");  //password is required

            }
            else if(!(mypn.substring(0,2).contentEquals("07"))){
                pr.DissmissProgress();
                pn.setError("Enter a valid phone number");

            }

            else if (consent.isEmpty()) {
                pr.DissmissProgress();
                Toast.makeText(this, "user consent is required", Toast.LENGTH_SHORT).show(); //password is required

            }

            else{

                String finalString="mlab"+"*"+myccno+"*"+myfn+"*"+myln+"*"+mypn+"*"+consent;


                String encrypted = MCrypt.bytesToHex( mcrypt.encrypt(finalString));

                sm.sendMessageApi(encrypted,msc.registerShortcode);

                pr.DissmissProgress();
                Toast.makeText(this, "Registration successful", Toast.LENGTH_SHORT).show();
                cc.setText("");
                fn.setText("");
                ln.setText("");
                pn.setText("");
                radioConsentButton.setChecked(false);


            }

        }
        catch(Exception e){
            pr.DissmissProgress();
            // registerdisplayDialog(e.getMessage());

        }
    }






    //check permissions granted at runtime in api 23 and above
    private boolean hasPermissions(){


        int res = 0;

        String[] permissions = new String[]{
                android.Manifest.permission.SEND_SMS,
                android.Manifest.permission.READ_SMS,
                android.Manifest.permission.RECEIVE_SMS

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

    private void requestPerms(){

//        Intent intent = new Intent(Telephony.Sms.Intents.ACTION_CHANGE_DEFAULT);
//        intent.putExtra(Telephony.Sms.Intents.EXTRA_PACKAGE_NAME, "com.example.kenweezy.mytablayouts");

        String[] permissions=new String[]{
                android.Manifest.permission.SEND_SMS,
                android.Manifest.permission.READ_SMS,
                android.Manifest.permission.GET_ACCOUNTS,
                android.Manifest.permission.RECEIVE_SMS,
                android.Manifest.permission.READ_CONTACTS,
                android.Manifest.permission.INTERNET
        };

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            requestPermissions(permissions,PERMS_REQUEST_CODE);
            if(!Telephony.Sms.getDefaultSmsPackage(getApplicationContext()).equals("com.example.kenweezy.mytablayouts")) {
                Intent intent2 = new Intent(Telephony.Sms.Intents.ACTION_CHANGE_DEFAULT);
                intent2.putExtra(Telephony.Sms.Intents.EXTRA_PACKAGE_NAME,
                        "com.example.kenweezy.mytablayouts");
                startActivity(intent2);
//
            }

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
    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        Toast.makeText(this, "back pressed", Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onUserInteraction() {
        super.onUserInteraction();
        long now=new Date().getTime();
        List<UserTimeOut> ml=UserTimeOut.findWithQuery(UserTimeOut.class,"Select * from User_time_out");
        if(ml.size()==0){
            UserTimeOut ut=new UserTimeOut(Long.toString(now));
            ut.save();

        }
        else{
            String mytime="";
            /******get the current stored interaction time*/
            for(int x=0;x<ml.size();x++){
                mytime=ml.get(x).getLasttime();

            }

            /******get the current stored interaction time*/

            /****insert the new interaction time***/
            UserTimeOut myut=UserTimeOut.findById(UserTimeOut.class,1);
            myut.setLasttime(Long.toString(now));
            myut.save();
            /****insert the new interaction time***/

            long mytimelong=Long.parseLong(mytime);
            long diff=now-mytimelong;

            if((diff/1000)>180){
//                Toast.makeText(this, "logging out", Toast.LENGTH_SHORT).show();


                Intent i = new Intent(getApplicationContext(), Mylogin.class);
                // Closing all the Activities
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

//                SharedPreferences settings = getSharedPreferences(SETTING_INFOS, 0);
//                SharedPreferences.Editor myedit=settings.edit();
//                myedit.putString(LOGGED_IN,"false");
//                myedit.commit();

                startActivity(i);
                finish();

//                return true;

            }
            else{

//                Toast.makeText(this, "interaction difference is "+diff, Toast.LENGTH_SHORT).show();
            }



        }

    }


}





package com.example.kenweezy.mytablayouts;

/**
 * Created by kennedy on 5/31/17.
 */

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by kennedy on 5/31/17.
 */

public class Compose extends AppCompatActivity {
    EditText addr,msg;
    SmsManager smsManager = SmsManager.getDefault();
    private static MainActivity inst;

    private static final int READ_SMS_PERMISSIONS_REQUEST = 1;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);

        setContentView(R.layout.compose);

        addr=(EditText) findViewById(R.id.myaddr);
        msg=(EditText) findViewById(R.id.mybdy);

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void sendSms(View v){

        try{

            if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS)
                    != PackageManager.PERMISSION_GRANTED) {
                getPermissionToReadSMS();
            } else {
                smsManager.sendTextMessage(addr.getText().toString(), null, msg.getText().toString(), null, null);
                Toast.makeText(this, "Message sent!", Toast.LENGTH_SHORT).show();
            }


        }
        catch(Exception e){


        }
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    public void getPermissionToReadSMS() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            if (shouldShowRequestPermissionRationale(
                    Manifest.permission.READ_SMS)) {
                Toast.makeText(this, "Please allow permission!", Toast.LENGTH_SHORT).show();
            }
            requestPermissions(new String[]{Manifest.permission.READ_SMS},
                    READ_SMS_PERMISSIONS_REQUEST);
        }
    }

}


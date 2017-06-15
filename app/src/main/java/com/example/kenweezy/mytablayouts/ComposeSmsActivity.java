package com.example.kenweezy.mytablayouts;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by KENWEEZY on 2017-05-08.
 */

public class ComposeSmsActivity extends AppCompatActivity {
    EditText addr,msgBdy;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.compose_message);
        initialise();


    }
    public void initialise(){
        addr=(EditText) findViewById(R.id.addr);
        msgBdy=(EditText) findViewById(R.id.msgbdy);



    }

    public void sendMessage(View v){

        try{
            String myaddr=addr.getText().toString();
            String mybdy=msgBdy.getText().toString();
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(myaddr, null, mybdy, null, null);
            Toast.makeText(getApplicationContext(), "SMS sent.",
                    Toast.LENGTH_LONG).show();

        }
        catch(Exception e){

            Toast.makeText(getApplicationContext(),
                    "Sending SMS failed.",
                    Toast.LENGTH_LONG).show();


        }
    }

}

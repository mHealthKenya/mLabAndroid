package com.example.kenweezy.mytablayouts;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by KENWEEZY on 2017-05-08.
 */

public class SmsReceiver extends BroadcastReceiver {

    public static final String SMS_BUNDLE = "pdus";
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle intentExtras = intent.getExtras();

        try{

            if (intentExtras != null) {
                Object[] sms = (Object[]) intentExtras.get(SMS_BUNDLE);
                String smsMessageStr = "";
                String smsBody="";
                String address="";
                for (int i = 0; i < sms.length; ++i) {
                    String format = intentExtras.getString("format");
                    SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) sms[i], format);

                    smsBody = smsMessage.getMessageBody().toString();
                    address = smsMessage.getOriginatingAddress();

                }

                Allmessages ms = new Allmessages(address,smsBody);
                ms.save();

//            SmsActivity inst = SmsActivity.instance();
//            inst.updateInbox();
//
                SmsActivity2 inst2 = SmsActivity2.instance();
                inst2.updateInbox();
                SmsActivity inst = SmsActivity.instance();
                inst.updateInbox();
            }


        }
        catch(Exception e){

//            Toast.makeText(context, "smsreceiver error "+e, Toast.LENGTH_SHORT).show();
            System.out.println("smsreceiver error "+e);
            Log.v("SMSRECEIVER..",e.getMessage());


        }


    }
}

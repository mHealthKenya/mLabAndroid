package com.example.kenweezy.mytablayouts;

/**
 * Created by KENWEEZY on 2017-01-13.
 */


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import com.example.kenweezy.mytablayouts.encryption.MCrypt;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class SmsBroadcastReceiver extends BroadcastReceiver {

    Myshortcodes msc=new Myshortcodes();
    MCrypt mcrypt=new MCrypt();





    public static final String SMS_BUNDLE = "pdus";

   

    public void onReceive(Context context, Intent intent) {


       Bundle intentExtras = intent.getExtras();
      


        if (intentExtras != null) {
            Object[] sms = (Object[]) intentExtras.get(SMS_BUNDLE);
            String smsMessageStr = "";
            String getAdd="";
            Long mydate=null;
            String mytimestamp=null;



            for (int i = 0; i < sms.length; ++i) {
                try {
                    SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) sms[i]);

                    String smsBody = smsMessage.getMessageBody().toString();
                    getAdd = smsMessage.getOriginatingAddress();
                    mydate = smsMessage.getTimestampMillis();


                    DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss.SSS");
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTimeInMillis(mydate);
                    mytimestamp = formatter.format(calendar.getTime());


//                DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss.SSS");
//                Calendar calendar = Calendar.getInstance();
//                calendar.setTimeInMillis(mydate);
//                String mytimestamp=formatter.format(calendar.getTime());

                    smsMessageStr += smsBody;
                }
                catch(Exception e){

                    Toast.makeText(context, "error occured"+e, Toast.LENGTH_SHORT).show();
                    System.out.println("smsbroadcastreceiver error "+e);
                    Log.v("SMSRECEIVER BROADCAST..",e.getMessage());
                }
//

            }
            try {
                if(getAdd.contentEquals(msc.mainShortcode)){


                    GetViralCounts gvc=new GetViralCounts();

                    String decryptedmess = new String( mcrypt.decrypt( smsMessageStr ) );


                    //new code here

                    String[] originalArray=decryptedmess.split("\\s+");
                    if(originalArray[0].contentEquals("EID")){
                        originalArray[0].replace("EID","FFEID Results");
                        decryptedmess=decryptedmess.replace("EID","FFEID Results");

                    }
                    else if(originalArray[0].contentEquals("VL")){
                        originalArray[0].replace("VL","FFViral Load Results");
                        decryptedmess=decryptedmess.replace("VL","FFViral Load Results");


                    }
                    String pidArray[]=originalArray[1].split(":");
                    if(pidArray[0].contentEquals("PID")){
                        pidArray[0].replace("PID","Patient ID");
                        decryptedmess=decryptedmess.replace("PID","Patient ID");
                    }
                    String ageArray[]=originalArray[2].split(":");
                    if(ageArray[0].contentEquals("A")){
                        ageArray[0].replace("A","Age");
                        decryptedmess=decryptedmess.replace("A","Age");

                    }
                    String sexArray[]=originalArray[3].split(":");
                    if(sexArray[0].contentEquals("S")){

                        sexArray[0].replace("S","Sex");
                        decryptedmess=decryptedmess.replaceFirst("S","Sex");
                    }
                    String dateArray[]=originalArray[4].split(":");

                    if(dateArray[0].contentEquals("DC")){
                        dateArray[0].replace("DC","Date Collected");
                        decryptedmess=decryptedmess.replaceFirst("DC","Date Collected");
                    }
                    String resultsArray[]=originalArray[5].split(":");

                    if(resultsArray[0].contentEquals("R")){
                        resultsArray[0].replace("R","Result");
                        decryptedmess=decryptedmess.replace("R:","Result:");
                    }

                    //new code here


                    String vcounts=Integer.toString(gvc.getViralCount(decryptedmess));

                    Messages ms = new Messages("false",getAdd,decryptedmess,mytimestamp,"unread","null",vcounts);
                    ms.save();

                    context.getContentResolver().delete(Uri.parse("content://sms"), "address=?", new String[] {msc.mainShortcode});


                    FragmentAll installall = FragmentAll.newInstance();
                    FragmentAllSuppresed installsuppressed = FragmentAllSuppresed.instance();
                    FragmentAllUnsuppressed installunsuppressed = FragmentAllUnsuppressed.instance();
                    FragmentAllVl installinvalid = FragmentAllVl.instance();

                    FragmentEidAll insteidall = FragmentEidAll.instance();
                    FragmentEidInvalid insteidinvalid = FragmentEidInvalid.instance();
                    FragmentEidNegative insteidsuppressed = FragmentEidNegative.instance();
                    FragmentEidPositive insteidunsuppressed = FragmentEidPositive.instance();

                    FragmentVlAll instvlall = FragmentVlAll.instance();
                    FragmentVlSuppressed instvlsuppressed = FragmentVlSuppressed.instance();
                    FragmentVlUnsuppressed instvlunsuppressed = FragmentVlUnsuppressed.instance();
                    FragmentVlInvalid instvlinvalid = FragmentVlInvalid.instance();




                    installall.updateList(smsMessageStr);
                    installsuppressed.updateList(smsMessageStr);
                    installunsuppressed.updateList(smsMessageStr);
                    installinvalid.updateList(smsMessageStr);

                    insteidall.updateList(smsMessageStr);
                    insteidsuppressed.updateList(smsMessageStr);
                    insteidunsuppressed.updateList(smsMessageStr);
                    insteidinvalid.updateList(smsMessageStr);

                    instvlall.updateList(smsMessageStr);
                    instvlsuppressed.updateList(smsMessageStr);
                    instvlunsuppressed.updateList(smsMessageStr);
                    instvlinvalid.updateList(smsMessageStr);


                }


            }
            catch (Exception e){
                Toast.makeText(context, ""+e.toString(), Toast.LENGTH_LONG).show();
                System.out.println("smsbroadcastreceiver error "+e);
                Log.v("SMSRECEIVER BROADCAST..",e.getMessage());

            }
        }


    }




}


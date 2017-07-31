package com.example.kenweezy.mytablayouts;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by kennedy on 5/31/17.
 */

public class Clickedmessage extends AppCompatActivity {

    ArrayList<String> smsMessagesList = new ArrayList<>();
    FloatingActionButton fabutn;
    ListView messages;
    ArrayAdapter arrayAdapter;
    EditText input;
    SmsManager smsManager = SmsManager.getDefault();
    private static Clickedmessage inst;

    private static final int READ_SMS_PERMISSIONS_REQUEST = 1;
    ArrayList<MymodelClicked> messageArray;
    MyadapterClicked adapter;

    public static final String LOGGED_IN = "logged_in";

    public static final String SETTING_INFOS = "SETTING_Infos";

    public static Clickedmessage instance() {
        return inst;
    }

    @Override
    public void onStart() {
        super.onStart();
        inst = this;
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.clickedmessage);
        messages = (ListView) findViewById(R.id.mymessages1);

        Bundle extras = getIntent().getExtras();

        String myadddr= extras.getString("myaddress");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(myadddr);


        if(Build.VERSION.SDK_INT>=21) {
            getWindow().setStatusBarColor(Color.parseColor("#1a1aff"));
        }

        getWindow().setStatusBarColor(Color.parseColor("#1a1aff"));


        messageArray = new ArrayList<MymodelClicked>();
// Create the adapter to convert the array to views
        adapter = new MyadapterClicked(this, messageArray);
// Attach the adapter to a ListView

        messages.setAdapter(adapter);



        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            getPermissionToReadSMS();
        } else {
            refreshSmsInbox();
        }
    }




    public void updateInbox(final String addr,final String bdy) {
        MymodelClicked mod=new MymodelClicked(addr,bdy);
        adapter.add(mod);
        adapter.notifyDataSetChanged();
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


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        // Make sure it's our original READ_CONTACTS request
        if (requestCode == READ_SMS_PERMISSIONS_REQUEST) {
            if (grantResults.length == 1 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Read SMS permission granted", Toast.LENGTH_SHORT).show();
                refreshSmsInbox();
            } else {
                Toast.makeText(this, "Read SMS permission denied", Toast.LENGTH_SHORT).show();
            }

        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }



    }




    public void refreshSmsInbox() {

        adapter.clear();

        Bundle extras = getIntent().getExtras();

        String myadd= extras.getString("myaddress");

        List<Allmessages> bdy = Allmessages.findWithQuery(Allmessages.class,  "Select * from Allmessages where addr=?", myadd);
        if(bdy.size()==0){
            return;

        }
        else{

            for(int y=0;y<bdy.size();y++){

                String addr=bdy.get(y).getAddr();
                String mybdy= bdy.get(y).getBdy();
                MymodelClicked mod=new MymodelClicked(addr,mybdy);

                adapter.add(mod);

            }
        }

    }



    @Override
    public void onUserInteraction() {
        super.onUserInteraction();
        long now = new Date().getTime();
        List<UserTimeOut> ml = UserTimeOut.findWithQuery(UserTimeOut.class, "Select * from User_time_out");
        if (ml.size() == 0) {
            UserTimeOut ut = new UserTimeOut(Long.toString(now));
            ut.save();

        } else {
            String mytime = "";
            /******get the current stored interaction time*/
            for (int x = 0; x < ml.size(); x++) {
                mytime = ml.get(x).getLasttime();

            }

            /******get the current stored interaction time*/

            /****insert the new interaction time***/
            UserTimeOut myut = UserTimeOut.findById(UserTimeOut.class, 1);
            myut.lasttime = Long.toString(now);
            myut.save();
            /****insert the new interaction time***/

            long mytimelong = Long.parseLong(mytime);
            long diff = now - mytimelong;

            if ((diff / 1000) > 60) {
//                Toast.makeText(this, "logging out", Toast.LENGTH_SHORT).show();


                Intent i = new Intent(getApplicationContext(), Mylogin.class);
                // Closing all the Activities
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                SharedPreferences settings = getSharedPreferences(SETTING_INFOS, 0);
                SharedPreferences.Editor myedit = settings.edit();
                myedit.putString(LOGGED_IN, "false");
                myedit.commit();

                startActivity(i);
                finish();

//                return true;

            } else {

//                Toast.makeText(this, "interaction difference is " + diff, Toast.LENGTH_SHORT).show();
            }


        }

    }



}

package com.example.kenweezy.mytablayouts;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

/**
 * Created by KENWEEZY on 2017-01-13.
 */

public class OptionsOld extends AppCompatActivity {

    String statusBarColour;
    String toolBarColour;
    String tabLayoutColour;
    String backgroundColour;

    int sbColour,tbColour,tlColour,bgColour;
    Toolbar tb;
    NotificationCompat.Builder noti;

    public static final String LOGGED_IN = "logged_in";

    public static final String SETTING_INFOS = "SETTING_Infos";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.optionsold);
        tb=(Toolbar) findViewById(R.id.toolbar2);
        tb.setTitleTextColor(Color.parseColor("#f2f2f2"));
        setSupportActionBar(tb);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getDefaultSettings();
//        getNotification();

        SharedPreferences settings = getSharedPreferences(SETTING_INFOS, 0);
        SharedPreferences.Editor myedit=settings.edit();
        myedit.putString(LOGGED_IN,"true");
        myedit.commit();

//        getNotification();



    }

    public void getNotification(){
        int uniqueId=1234;
        Intent myint = null;

        Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        noti= new NotificationCompat.Builder(this);
        noti.setAutoCancel(true);
        noti.setSmallIcon(R.mipmap.ic_launcher);
        noti.setTicker("these is the ticker");
        noti.setContentText("content text");
        noti.setContentTitle("content title");
        noti.setSound(soundUri);
        noti.setWhen(System.currentTimeMillis());

        SharedPreferences settings = getSharedPreferences(SETTING_INFOS, 0);

        String name = settings.getString(LOGGED_IN, "");

        if(name.contentEquals("true")){

            myint=new Intent(getApplicationContext(),MainActivity.class);

        }
        else{
            myint=new Intent(getApplicationContext(),Mylogin.class);

        }


        PendingIntent pi=PendingIntent.getActivity(this,0,myint,PendingIntent.FLAG_UPDATE_CURRENT);
        noti.setContentIntent(pi);

        NotificationManager nm=(NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        nm.notify(uniqueId,noti.build());




    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }



    private void setCustomColour(){

        SharedPreferences sp= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        statusBarColour=sp.getString("status_bar_colours","#000066");
        toolBarColour=sp.getString("tool_bar_colours","#3333ff");
        tabLayoutColour=sp.getString("tablayout_colours","#1a1aff");
        backgroundColour=sp.getString("background_colours","#f2f2f2");

        sbColour= Color.parseColor(statusBarColour);
        tbColour=Color.parseColor(toolBarColour);
        tlColour=Color.parseColor(tabLayoutColour);
        bgColour=Color.parseColor(backgroundColour);

        if(Build.VERSION.SDK_INT>=21) {
            getWindow().setStatusBarColor(sbColour);

        }

        tb.setBackgroundColor(tbColour);
//        viewPager.setBackgroundColor(bgColour);







    }

    private void getDefaultSettings(){
        SharedPreferences sp= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        boolean ds=sp.getBoolean("default_colour",false);
        if(ds){
            tb.setBackgroundColor(Color.parseColor("#3333ff"));
            if(Build.VERSION.SDK_INT>=21) {
                getWindow().setStatusBarColor(Color.parseColor("#000066"));
            }

        }
        else{
            setCustomColour();
        }


    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//
//        int id=item.getItemId();
//
//        switch(id){
//            case R.id.action_settings:
//                Intent myint=new Intent(getApplicationContext(),Settings.class);
//                startActivity(myint);
//                return true;
//
//            case R.id.logout:
//
//                Intent i = new Intent(getApplicationContext(), Mylogin.class);
//                // Closing all the Activities
//                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                startActivity(i);
//                finish();
//
//                return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

    public void goToResults(View v){

        Intent inty=new Intent(getApplicationContext(),MainActivity.class);
        startActivity(inty);


    }

    public void goToDb(View v){
        Toast.makeText(this, "going to db", Toast.LENGTH_SHORT).show();


    }
}

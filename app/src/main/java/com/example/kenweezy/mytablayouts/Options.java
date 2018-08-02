package com.example.kenweezy.mytablayouts;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.customtabs.CustomTabsClient;
import android.support.customtabs.CustomTabsIntent;
import android.support.customtabs.CustomTabsServiceConnection;
import android.support.customtabs.CustomTabsSession;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kenweezy.mytablayouts.hts.HtsresultsTab;

import java.util.Date;
import java.util.List;

/**
 * Created by KENWEEZY on 2017-01-13.
 */

public class Options extends AppCompatActivity {

    ProgressDialog progressDialog;



    final String CUSTOM_TAB_PACKAGE_NAME = "com.android.chrome";
    //final String test = "http://fcas.mhealthkenya.org/index.php/878278?lang=en";
    final String Dashboard = "http://mlab.mhealthkenya.org/login";
    final String gomhealth = "http://mhealthkenya.org";
    final String gopepfar = "https://www.pepfar.gov/countries/c19425.htm";
    final String gocdc = "https://www.cdc.gov/globalhealth/countries/kenya";
    final String gomoh = "http://www.health.go.ke";


    CustomTabsClient mCustomTabsClient;
    CustomTabsSession mCustomTabsSession;
    CustomTabsServiceConnection mCustomTabsServiceConnection;
    CustomTabsIntent mCustomTabsIntent;

    String statusBarColour;
    String toolBarColour;
    String tabLayoutColour;
    String backgroundColour;

    TextView ver;

    int sbColour,tbColour,tlColour,bgColour;
    Toolbar tb;
    NotificationCompat.Builder noti;

    public static final String LOGGED_IN = "logged_in";

    public static final String SETTING_INFOS = "SETTING_Infos";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.options);
//        tb=(Toolbar) findViewById(R.id.toolbar2);
//        tb.setTitleTextColor(Color.parseColor("#f2f2f2"));
//        setSupportActionBar(tb);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getDefaultSettings();
//        getNotification();

        SharedPreferences settings = getSharedPreferences(SETTING_INFOS, 0);
        SharedPreferences.Editor myedit=settings.edit();
        myedit.putString(LOGGED_IN,"true");
        myedit.commit();

        setVersion();

        mCustomTabsServiceConnection = new CustomTabsServiceConnection() {
            @Override
            public void onCustomTabsServiceConnected(ComponentName componentName, CustomTabsClient customTabsClient) {
                mCustomTabsClient= customTabsClient;
                mCustomTabsClient.warmup(0L);
                mCustomTabsSession = mCustomTabsClient.newSession(null);
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                mCustomTabsClient= null;
            }
        };

        CustomTabsClient.bindCustomTabsService(this, CUSTOM_TAB_PACKAGE_NAME, mCustomTabsServiceConnection);

        mCustomTabsIntent = new CustomTabsIntent.Builder(mCustomTabsSession)
                .setShowTitle(false)
                .build();

//        getNotification();



    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.unbindService( mCustomTabsServiceConnection);
        mCustomTabsServiceConnection = null;
    }

    public void HTSResults(View v){

        try{
            Intent myint=new Intent(getApplicationContext(), HtsresultsTab.class);
            startActivity(myint);

        }
        catch(Exception e){


        }
    }

    public void setVersion(){

        try{

            ver=(TextView) findViewById(R.id.version);
            ver.setText("mLab Version 1.093");
        }
        catch(Exception e){


        }
    }
//
//    @Override
//    public void onUserInteraction() {
//        super.onUserInteraction();
//        Toast.makeText(this, "user is interacting", Toast.LENGTH_SHORT).show();
//    }

    public void gomoh(View view) {
        mCustomTabsIntent.launchUrl(this, Uri.parse(gomoh));
    }

    public void gocdc(View view) {
        mCustomTabsIntent.launchUrl(this, Uri.parse(gocdc));
    }
    public void gopepfar(View view) {
        mCustomTabsIntent.launchUrl(this, Uri.parse(gopepfar));
    }

    public void gomhealth(View view) {
        mCustomTabsIntent.launchUrl(this, Uri.parse(gomhealth));
    }

    public void goToDb(View view) {
        mCustomTabsIntent.launchUrl(this, Uri.parse(Dashboard));
    }

    public void doRegister(View v){

        try{
            Intent myint=new Intent(getApplicationContext(),Register.class);
            startActivity(myint);


        }
        catch(Exception e){
            Toast.makeText(this, "unable to load Register", Toast.LENGTH_SHORT).show();


        }
    }

    public void sampleLogin(View v){

        try{


            Intent intent = new Intent(getApplicationContext(),SampleLogin.class);
            startActivity(intent);
        }
        catch(Exception e){

        }
    }

    public void historicalResults(View v){

        try{

            Intent myint=new Intent(getApplicationContext(),HistoricalResults.class);
            startActivity(myint);


        }
        catch(Exception e){


        }
    }

    public void setToolBar(){

        try{


        }
        catch(Exception e){


        }
    }

    public void messaging(View v){

        try{
//            Intent myintt=new Intent(Options.this,SmsActivity.class);
//            startActivity(myintt);

            Intent intent = new Intent(getApplicationContext(),Composing.class);
            startActivity(intent);
        }
        catch(Exception e){

            Toast.makeText(this, "error starting messagiing activity "+e, Toast.LENGTH_SHORT).show();


        }
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

        try{
            new LongOperation().execute();

        }
        catch(Exception e){
            Toast.makeText(this, "error loading results, try again", Toast.LENGTH_SHORT).show();


        }




    }



    private class LongOperation extends AsyncTask<String, Void, String>
    {
        protected void onPreExecute()
        {
            try{

                progressDialog = new ProgressDialog(Options.this);
                progressDialog.setTitle("Getting Results...");
                progressDialog.setMessage("Please wait...");
                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressDialog.setMax(100);
                progressDialog.setProgress(0);
                progressDialog.setCancelable(false);
                progressDialog.show();


            }
            catch(Exception e){

                Toast.makeText(Options.this, "error loading progress dialog, try again", Toast.LENGTH_SHORT).show();

            }


        }

        protected String doInBackground(String... params)
        {
            try
            {
                Intent inty=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(inty);
            }
            catch (Exception e) {
                Toast.makeText(Options.this, "error getting results, try again", Toast.LENGTH_SHORT).show();
//                System.out.print(""+e.printStackTrace());
            }
            return null;
        }
        protected void onPostExecute(String result)
        {

            progressDialog.dismiss();

        }
    }

//    public void goToDb(View v){
//        Toast.makeText(this, "going to db", Toast.LENGTH_SHORT).show();
//
//
//    }

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
            myut.lasttime=Long.toString(now);
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

                SharedPreferences settings = getSharedPreferences(SETTING_INFOS, 0);
                SharedPreferences.Editor myedit=settings.edit();
                myedit.putString(LOGGED_IN,"false");
                myedit.commit();

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


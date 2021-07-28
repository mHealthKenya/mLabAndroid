package com.example.kenweezy.mytablayouts;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.example.kenweezy.mytablayouts.AccessServer.AccessServer;
import com.example.kenweezy.mytablayouts.Checkinternet.CheckInternet;
import com.example.kenweezy.mytablayouts.Config.Config;
import com.example.kenweezy.mytablayouts.GetMessageCount.GetCounts;
import com.example.kenweezy.mytablayouts.MakeCalls.makeCalls;
import com.example.kenweezy.mytablayouts.ProcessReceivedMessage.ProcessMessage;
//import com.example.kenweezy.mytablayouts.SSLTrustCertificate.SSLTrust;
import com.example.kenweezy.mytablayouts.Smsretrieverapi.SmsReceiver;
import com.example.kenweezy.mytablayouts.encryption.Base64Encoder;
import com.example.kenweezy.mytablayouts.encryption.MCrypt;
import com.example.kenweezy.mytablayouts.fragmentTagTable.fragmenttags;
import com.example.kenweezy.mytablayouts.sendmessages.SendMessage;
import com.facebook.stetho.Stetho;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.credentials.Credential;
import com.google.android.gms.auth.api.phone.SmsRetriever;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import org.jetbrains.annotations.NotNull;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import kotlin.jvm.internal.Intrinsics;

import static com.example.kenweezy.mytablayouts.StringSplitter.SplitString.splittedString;

public class MainActivity extends AppCompatActivity implements SmsReceiver.MessageReceiveListener {
    public static final String SMS_BUNDLE = "pdus";
    public static final String LOGGED_IN = "logged_in";
    public static final String SETTING_INFOS = "SETTING_Infos";
    private final int RC_HINT = 2;
    @NotNull
    private final SmsReceiver smsBroadcast = new SmsReceiver();
    public int mval;
    ProgressDialog progressDialog;
    ProgressDialog pd;
    BroadcastReceiver broadcastReceiver;
    Myshortcodes msc = new Myshortcodes();
    boolean incomingMessage = false;//flag to check if a new message has arrived
    String statusBarColour;
    String toolBarColour;
    String tabLayoutColour;
    String backgroundColour;
    boolean vlSelected;
    boolean eidSelected;
    boolean allSelected;
    boolean reportsSelected, reportseidselected, reportsvlselected;
    boolean feidnegselected, feidpositiveselected, feidInvalidSelected;
    int sbColour, tbColour, tlColour, bgColour;
    String[] tabTitle = {"ALL", "EID", "VL", "EID REPORTS", "VL REPORTS"};
    int[] unreadCount = {0, 0, 0, 0, 0};
    FragmentAll myfall;
    FragmentVlInvalid fvlinv;
    FragmentVlSuppressed fvlsup;
    FragmentEidAll myfeid;
    FragmentVlAll myfvl;
    ReportsFragment freport;
    ReportsFragmentVL freportvl;
    ViewPagerAdapter newadapt;
    FragmentEidNegative insteidsuppressed;
    boolean feids, falls, fvls, freports;
    ViewPagerAdapter adapter;
    AHBottomNavigation bottomNavigation;
    boolean oncreate = false;
    Progress pr = new Progress();
    MCrypt mcrypt = new MCrypt();
    GetCounts gc = new GetCounts();
    FloatingActionButton fabref;
    ProcessMessage pm;
    SendMessage sm;
    makeCalls mc;
    private TabLayout tabLayout;

    CheckInternet chk;
    Base64Encoder encoder;
    AccessServer acs;


    //    start sms retriever api
    private ViewPager viewPager;
    private Toolbar toolbar;
    private MenuItem mSearchAction;
    private boolean isSearchOpened = false;

//    end sms retriever api
    private EditText edtSeach;
    @org.jetbrains.annotations.Nullable
    private GoogleApiClient mCredentialsApiClient;

    private static String makeFragmentName(int viewId, long id) {
        return "android:switcher:" + viewId + ":" + id;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //SSLTrust.nuke();

        Stetho.initializeWithDefaults(this);
        initialise();
//        changeStatusBarColor();


//        LoadHeavyStuff();


//        defineSmsReceiver();
//        registerSmsReceiver();
        getFragTags();


        //sms retriever api

        listenForIncomingMessage();
        refreshResultsClicked();


//        generateAppSignature();

        initiateBackgroundService();

        //sms retriever api

    }

    private void changeStatusBarColor(){
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.parseColor(Config.statusBarColor));
        }
    }

    private void initialise() {

        chk=new CheckInternet(MainActivity.this);
        encoder=new Base64Encoder();
        acs=new AccessServer(MainActivity.this);

        fabref = (FloatingActionButton) findViewById(R.id.fabmainres);
        sm = new SendMessage(MainActivity.this);
        pm = new ProcessMessage();
        mc = new makeCalls(MainActivity.this);

    }

    private void refreshResultsClicked() {

        fabref.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String userPhoneNumber="";

                List<UsersTable> myl=UsersTable.findWithQuery(UsersTable.class,"select * from Users_table limit 1");
                for(int y=0;y<myl.size();y++){

                    userPhoneNumber=myl.get(y).getPhonenumber();
                }

                if(chk.isInternetAvailable()){

                    acs.getVlEidResultsFromDb(userPhoneNumber);

                }
                else{

                    sm.sendMessageApi(encoder.encryptString(userPhoneNumber), Config.mainShortcode);

                    listenForIncomingMessage();

                }



//                Toast.makeText(MainActivity.this, "refreshing results", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        new AsyncTaskRunner().execute();
    }

    public void activateSearchOnFirstFragment() {
        try {

            vlSelected = false;
            eidSelected = false;
            allSelected = true;
            reportseidselected = false;
            reportsvlselected = false;
            falls = true;
            feids = false;
            fvls = false;
            myfall = (FragmentAll) newadapt.getFragment(0);
            if (!mSearchAction.isVisible()) {
                mSearchAction.setVisible(true);
            }
            closeSearch(false);
        } catch (Exception e) {

            Toast.makeText(this, "unable to activate search " + e, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        listenForIncomingMessage();
//        activateSearchOnFirstFragment();
//        new AsyncTaskRunner().execute();
    }

    public void method() {

        runOnUiThread(new Runnable() {
            public void run() {
                try {
                    LoadHeavyStuff();

                } catch (Exception e) {
                    e.printStackTrace();
                }

//                LoadHeavyStuff();

            }
        });
    }

    public void MyHeavyJunk() {


        feidnegselected = false;
        feidpositiveselected = false;
        feidInvalidSelected = false;
        try {
//            Messages ms = new Messages("kennedy", "sitati");
//            ms.save();
            List<Messages> myl = Messages.listAll(Messages.class);

            if (myl.size() == 0) {


            } else {


            }

//            listAllMessages(myl);

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


        MyBottomNav();

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);


        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#f2f2f2"));
        tabLayout.setTabTextColors(Color.parseColor("#ffffff"),Color.parseColor("#000050"));
        tabLayout.setSelectedTabIndicatorHeight(5);


        newadapt = ((ViewPagerAdapter) viewPager.getAdapter());

        activateSearchOnFirstFragment();


        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                final int index = position;
                bottomNavigation.setNotificationBackgroundColorResource(R.color.colorPrimaryDark);
//                bottomNavigation.setBackgroundColor(Color.WHITE);
                bottomNavigation.setNotificationTextColorResource(R.color.textColorPrimary);
//                bottomNavigation.setNotificationTextColorResource(Color.WHITE);


                switch (position) {

                    case 0:


                        pr.progressing(MainActivity.this, "loading..", "Getting All Results");
                        Handler mHand0 = new Handler();
                        mHand0.postDelayed(new Runnable() {

                            @Override
                            public void run() {
                                // TODO Auto-generated method stub

                                vlSelected = false;
                                eidSelected = false;
                                allSelected = true;
                                reportseidselected = false;
                                reportsvlselected = false;
//                        myfvl.replaceContent(new FragmentVlSuppressed());
                                MyBottomNav();

                                falls = true;
                                feids = false;
                                fvls = false;
                                myfall = (FragmentAll) newadapt.getFragment(index);
                                if (!mSearchAction.isVisible()) {
                                    mSearchAction.setVisible(true);
                                }
                                closeSearch(false);

                                pr.DissmissProgress();


                                //Dismiss progressBar here

                            }
                        }, 1500);

//                        myfall.test();
//                        Toast.makeText(getApplicationContext(), "fragment"+myfall, Toast.LENGTH_SHORT).show();
                        break;
                    case 1:


                        pr.progressing(MainActivity.this, "loading..", "Getting EID Results");
                        Handler mHand1 = new Handler();
                        mHand1.postDelayed(new Runnable() {

                            @Override
                            public void run() {
                                // TODO Auto-generated method stub


                                vlSelected = false;
                                eidSelected = true;
                                allSelected = false;
                                reportseidselected = false;
                                reportsvlselected = false;
                                MyBottomNav();
                                if (geteidNegativeCount() > 0) {

                                    bottomNavigation.setNotification(Integer.toString(geteidNegativeCount()), 0);

                                } else {
                                    bottomNavigation.setNotification("", 0);

                                }
                                if (geteidPositiveCount() > 0) {
                                    bottomNavigation.setNotification(Integer.toString(geteidPositiveCount()), 1);
                                } else {
                                    bottomNavigation.setNotification("", 1);

                                }

                                if (geteidInvalidCount() > 0) {
                                    bottomNavigation.setNotification(Integer.toString(geteidInvalidCount()), 2);
                                } else {
                                    bottomNavigation.setNotification("", 2);

                                }


                                falls = false;
                                feids = true;
                                fvls = false;
                                myfeid = (FragmentEidAll) newadapt.getFragment(index);

                                if (!mSearchAction.isVisible()) {
                                    mSearchAction.setVisible(true);
                                }
//                        Toast.makeText(getApplicationContext(), "fragment"+myfeid, Toast.LENGTH_SHORT).show();
                                closeSearch(false);

                                pr.DissmissProgress();


                                //Dismiss progressBar here

                            }
                        }, 1500);

                        break;

                    case 2:


                        pr.progressing(MainActivity.this, "loading..", "Getting VL Results");
                        Handler mHand = new Handler();
                        mHand.postDelayed(new Runnable() {

                            @Override
                            public void run() {
                                // TODO Auto-generated method stub


                                vlSelected = true;
                                eidSelected = false;
                                allSelected = false;
                                reportseidselected = false;
                                reportsvlselected = false;
                                MyBottomNav();
                                if (gc.getVlSuppressed() > 0) {
                                    bottomNavigation.setNotification(Integer.toString(gc.getVlSuppressed()), 0);

                                } else {
                                    bottomNavigation.setNotification("", 0);

                                }

                                if (gc.getVlUnsuppressed() > 0) {
                                    bottomNavigation.setNotification(Integer.toString(gc.getVlUnsuppressed()), 1);

                                } else {
                                    bottomNavigation.setNotification("", 1);
                                }

                                if (gc.getInvalidCount() > 0) {
                                    bottomNavigation.setNotification(Integer.toString(gc.getInvalidCount()), 2);

                                } else {

                                    bottomNavigation.setNotification("", 2);
                                }


                                falls = false;
                                feids = false;
                                fvls = true;
                                myfvl = (FragmentVlAll) newadapt.getFragment(index);

                                if (!mSearchAction.isVisible()) {
                                    mSearchAction.setVisible(true);
                                }
                                closeSearch(false);
                                pr.DissmissProgress();


                                //Dismiss progressBar here

                            }
                        }, 1500);


//                        Toast.makeText(getApplicationContext(), "fragment"+myfvl, Toast.LENGTH_SHORT).show();
                        break;

                    case 3:


                        pr.progressing(MainActivity.this, "loading..", "Getting EID Reports");
                        Handler mHand3 = new Handler();
                        mHand3.postDelayed(new Runnable() {

                            @Override
                            public void run() {
                                // TODO Auto-generated method stub


                                vlSelected = false;
                                eidSelected = false;
                                allSelected = false;
                                reportseidselected = true;
                                reportsvlselected = false;
                                MyBottomNav();

                                freport = (ReportsFragment) newadapt.getFragment(index);
                                bottomNavigation.setNotification("", 0);
                                bottomNavigation.setNotification("", 1);
//                        bottomNavigation.setNotification("", 2);
                                falls = false;
                                feids = false;
                                fvls = false;

                                if (mSearchAction.isVisible()) {
                                    mSearchAction.setVisible(false);
                                }
                                closeSearch(true);

                                pr.DissmissProgress();


                                //Dismiss progressBar here

                            }
                        }, 2000);


//                        Toast.makeText(getApplicationContext(), "fragment"+myfvl, Toast.LENGTH_SHORT).show();
                        break;

                    case 4:


                        pr.progressing(MainActivity.this, "loading..", "Getting VL Reports");
                        Handler mHand4 = new Handler();
                        mHand4.postDelayed(new Runnable() {

                            @Override
                            public void run() {
                                // TODO Auto-generated method stub


                                vlSelected = false;
                                eidSelected = false;
                                allSelected = false;
                                reportseidselected = false;
                                reportsvlselected = true;
                                MyBottomNav();

                                freportvl = (ReportsFragmentVL) newadapt.getFragment(index);
                                bottomNavigation.setNotification("", 0);
                                bottomNavigation.setNotification("", 1);
//                        bottomNavigation.setNotification("", 2);
                                falls = false;
                                feids = false;
                                fvls = false;

                                if (mSearchAction.isVisible()) {
                                    mSearchAction.setVisible(false);
                                }
                                closeSearch(true);

                                pr.DissmissProgress();


                                //Dismiss progressBar here

                            }
                        }, 2000);


//                        Toast.makeText(getApplicationContext(), "fragment"+myfvl, Toast.LENGTH_SHORT).show();
                        break;

                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        unreadCount[0] = getAllCount();
        unreadCount[1] = getFfeidCount();
        unreadCount[2] = getVlCount();


        setupTabIcons();

        SharedPreferences settings = getSharedPreferences(SETTING_INFOS, 0);
        SharedPreferences.Editor myedit = settings.edit();
        myedit.putString(LOGGED_IN, "true");
        myedit.commit();

        getDefaultSettings();
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        displayDbVcounts();

    }


    public void LoadHeavyStuff() {


        try {
            progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setTitle("Getting Results...");
            progressDialog.setMessage("Please wait...");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setMax(100);
            progressDialog.setProgress(0);
            progressDialog.setCancelable(false);
            progressDialog.show();


            Handler mHand = new Handler();
            mHand.postDelayed(new Runnable() {

                @Override
                public void run() {
                    // TODO Auto-generated method stub


                    MyHeavyJunk();
                    progressDialog.dismiss();


                    //Dismiss progressBar here

                }
            }, 5000);


//

//            pr.DissmissProgress();
        } catch (Exception e) {


        }
    }


    private void doSearch(CharSequence s) {


        if (falls) {

            myfall.doSearching(s);

        } else if (feids) {

            myfeid.doSearching(s);
        } else if (fvls) {

            myfvl.doSearching(s);
        } else if (feidnegselected) {


            insteidsuppressed = (FragmentEidNegative) newadapt.getFragment(1);
            insteidsuppressed.doSearching(s);


        } else {
//            Toast.makeText(this, "", Toast.LENGTH_SHORT).show();

        }

//
    }


    public void MyBottomNav() {

        bottomNavigation = (AHBottomNavigation) findViewById(R.id.bottom_navigation);


        AHBottomNavigationItem eidNegative = new AHBottomNavigationItem("Negative", R.mipmap.reorder, R.color.colorPrimary);
        AHBottomNavigationItem eidPos = new AHBottomNavigationItem("Positive", R.mipmap.reorder, R.color.colorAccent);
        AHBottomNavigationItem eidInv = new AHBottomNavigationItem("Invalid EID", R.mipmap.reorder, R.color.colorAccent);

        AHBottomNavigationItem vlSuppressed = new AHBottomNavigationItem("Suppressed", R.mipmap.reorder, R.color.colorPrimary);
        AHBottomNavigationItem vlUnsuppressed = new AHBottomNavigationItem("UnSuppressed", R.mipmap.reorder, R.color.colorAccent);
        AHBottomNavigationItem vlInvalid = new AHBottomNavigationItem("Invalid", R.mipmap.reorder, R.color.colorPrimary);

        AHBottomNavigationItem rmonthly = new AHBottomNavigationItem("Monthly", R.mipmap.reorder, R.color.colorPrimary);
//        AHBottomNavigationItem rweekly = new AHBottomNavigationItem("Weekly", R.mipmap.reorder, R.color.colorAccent);
        AHBottomNavigationItem ryearly = new AHBottomNavigationItem("Yearly", R.mipmap.reorder, R.color.colorPrimary);

        AHBottomNavigationItem rvlmonthly = new AHBottomNavigationItem("Monthly VL", R.mipmap.reorder, R.color.colorPrimary);
//        AHBottomNavigationItem rvlweekly = new AHBottomNavigationItem("Weekly VL", R.mipmap.reorder, R.color.colorAccent);
        AHBottomNavigationItem rvlyearly = new AHBottomNavigationItem("Yearly VL", R.mipmap.reorder, R.color.colorPrimary);

// Add items
        if (incomingMessage) {


            bottomNavigation.removeAllItems();
            bottomNavigation.hideBottomNavigation();
            incomingMessage = false;


        } else {

            if (eidSelected) {


                bottomNavigation.removeAllItems();
                bottomNavigation.addItem(eidNegative);

                bottomNavigation.addItem(eidPos);
                bottomNavigation.addItem(eidInv);


                // Set background color

                if (bottomNavigation.isHidden()) {
                    bottomNavigation.restoreBottomNavigation(true);
                }
                bottomNavigation.setDefaultBackgroundColor(Color.parseColor("#ffffff"));
            } else if (vlSelected) {
                bottomNavigation.removeAllItems();
                bottomNavigation.addItem(vlSuppressed);
                bottomNavigation.addItem(vlUnsuppressed);
                bottomNavigation.addItem(vlInvalid);

                // Set background color
                if (bottomNavigation.isHidden()) {
                    bottomNavigation.restoreBottomNavigation();
                }
                bottomNavigation.setDefaultBackgroundColor(Color.parseColor("#ffffff"));
            } else if (reportseidselected) {

                bottomNavigation.removeAllItems();
//            bottomNavigation.addItem(rweekly);
                bottomNavigation.addItem(rmonthly);
                bottomNavigation.addItem(ryearly);

                // Set background color
                if (bottomNavigation.isHidden()) {
                    bottomNavigation.restoreBottomNavigation();
                }
                bottomNavigation.setDefaultBackgroundColor(Color.parseColor("#ffffff"));


            } else if (reportsvlselected) {

                bottomNavigation.removeAllItems();
//            bottomNavigation.addItem(rvlweekly);
                bottomNavigation.addItem(rvlmonthly);
                bottomNavigation.addItem(rvlyearly);

                // Set background color
                if (bottomNavigation.isHidden()) {
                    bottomNavigation.restoreBottomNavigation();
                }
                bottomNavigation.setDefaultBackgroundColor(Color.parseColor("#ffffff"));


            } else {
                bottomNavigation.removeAllItems();
                bottomNavigation.hideBottomNavigation();
            }

        }


// Disable the translation inside the CoordinatorLayout
        bottomNavigation.setBehaviorTranslationEnabled(false);

// Enable the translation of the FloatingActionButton
//        bottomNavigation.manageFloatingActionButtonBehavior(floatingActionButton);

// Change colors
        bottomNavigation.setAccentColor(Color.parseColor("#F63D2B"));
        bottomNavigation.setInactiveColor(Color.parseColor("#747474"));

// Force to tint the drawable (useful for font with icon for example)
        bottomNavigation.setForceTint(true);

// Display color under navigation bar (API 21+)
// Don't forget these lines in your style-v21
// <item name="android:windowTranslucentNavigation">true</item>
// <item name="android:fitsSystemWindows">true</item>
        bottomNavigation.setTranslucentNavigationEnabled(true);

//        bottomNavigation.setTitleState(AHBottomNavigation.TitleState.SHOW_WHEN_ACTIVE);
        bottomNavigation.setTitleState(AHBottomNavigation.TitleState.ALWAYS_SHOW);
//        bottomNavigation.setTitleState(AHBottomNavigation.TitleState.ALWAYS_HIDE);

// Use colored navigation with circle reveal effect
        bottomNavigation.setColored(false);

// Set current item programmatically
//      bottomNavigation.setCurrentItem(-1);

        // Set listeners
        bottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {
                // Do something cool here...
                switch (position) {
                    case 0:
                        if (bottomNavigation.getItem(0).getTitle(getApplicationContext()).toString() == "Suppressed") {


                            pr.DissmissProgress();

                            pr.progressing(MainActivity.this, "loading..", "Getting Suppressed VL Results");
                            Handler mHand = new Handler();
                            mHand.postDelayed(new Runnable() {

                                @Override
                                public void run() {

                                    myfvl.replaceContent(new FragmentVlSuppressed());

                                    pr.DissmissProgress();
                                    //Dismiss progressBar here

                                }
                            }, 1500);
//

//
                        } else if (bottomNavigation.getItem(0).getTitle(getApplicationContext()).toString() == "Monthly") {

                            pr.DissmissProgress();
                            pr.progressing(MainActivity.this, "loading..", "Getting EID monthly Results");
                            Handler mHand = new Handler();
                            mHand.postDelayed(new Runnable() {

                                @Override
                                public void run() {

                                    freport.replaceContent(new FragmentReportsEidMonthly());

                                    pr.DissmissProgress();
                                    //Dismiss progressBar here

                                }
                            }, 1500);
//

//
                        } else if (bottomNavigation.getItem(0).getTitle(getApplicationContext()).toString() == "Monthly VL") {


                            pr.DissmissProgress();
                            pr.progressing(MainActivity.this, "loading..", "Getting VL monthly Results");
                            Handler mHand = new Handler();
                            mHand.postDelayed(new Runnable() {

                                @Override
                                public void run() {

                                    freportvl.replaceContent(new FragmentReportsMonthly());

                                    pr.DissmissProgress();
                                    //Dismiss progressBar here

                                }
                            }, 1500);

//
                        } else {

                            pr.DissmissProgress();
                            pr.progressing(MainActivity.this, "loading..", "Getting EID Negative Results");
                            Handler mHand = new Handler();
                            mHand.postDelayed(new Runnable() {

                                @Override
                                public void run() {

                                    myfeid.replaceContent(new FragmentEidNegative());

                                    feidnegselected = true;
                                    feidpositiveselected = false;

                                    pr.DissmissProgress();
                                    //Dismiss progressBar here

                                }
                            }, 1500);


                        }

                        break;
                    case 1:

                        if (bottomNavigation.getItem(1).getTitle(getApplicationContext()).toString() == "UnSuppressed") {

                            pr.DissmissProgress();
                            pr.progressing(MainActivity.this, "loading..", "Getting VL Unsuppressed Results");
                            Handler mHand = new Handler();
                            mHand.postDelayed(new Runnable() {

                                @Override
                                public void run() {

                                    myfvl.replaceContent(new FragmentVlUnsuppressed());

                                    pr.DissmissProgress();
                                    //Dismiss progressBar here

                                }
                            }, 1500);


//
                        } else if (bottomNavigation.getItem(1).getTitle(getApplicationContext()).toString() == "Yearly") {

                            pr.DissmissProgress();

                            pr.progressing(MainActivity.this, "loading..", "Getting EID Yearly Results");
                            Handler mHand = new Handler();
                            mHand.postDelayed(new Runnable() {

                                @Override
                                public void run() {

                                    freport.replaceContent(new FragmentReportsEidYearly());

                                    pr.DissmissProgress();
                                    //Dismiss progressBar here

                                }
                            }, 1500);
//

//
                        } else if (bottomNavigation.getItem(1).getTitle(getApplicationContext()).toString() == "Yearly VL") {


                            pr.DissmissProgress();
                            pr.progressing(MainActivity.this, "loading..", "Getting VL yearly Results");
                            Handler mHand = new Handler();
                            mHand.postDelayed(new Runnable() {

                                @Override
                                public void run() {

                                    freportvl.replaceContent(new FragmentReportsYearly());

                                    pr.DissmissProgress();
                                    //Dismiss progressBar here

                                }
                            }, 1500);
//

//
                        } else {


                            pr.DissmissProgress();
                            pr.progressing(MainActivity.this, "loading..", "Getting EID Positive Results");
                            Handler mHand = new Handler();
                            mHand.postDelayed(new Runnable() {

                                @Override
                                public void run() {


                                    myfeid.replaceContent(new FragmentEidPositive());
                                    feidpositiveselected = true;
                                    feidnegselected = false;
                                    feidInvalidSelected = false;

                                    pr.DissmissProgress();
                                    //Dismiss progressBar here

                                }
                            }, 1500);


                        }
//                      Toast.makeText(MainActivity.this, ""+bottomNavigation.getItem(1).getTitle(getApplicationContext()), Toast.LENGTH_SHORT).show();
                        break;

                    case 2:

                        if (bottomNavigation.getItem(2).getTitle(getApplicationContext()).toString() == "Invalid") {

                            pr.DissmissProgress();
                            pr.progressing(MainActivity.this, "loading..", "Getting VL invalid Results");
                            Handler mHand = new Handler();
                            mHand.postDelayed(new Runnable() {

                                @Override
                                public void run() {

                                    myfvl.replaceContent(new FragmentVlInvalid());

                                    pr.DissmissProgress();
                                    //Dismiss progressBar here

                                }
                            }, 1500);


//
                        } else {

                            pr.DissmissProgress();
                            pr.progressing(MainActivity.this, "loading..", "Getting EID Invalid Results");
                            Handler mHand = new Handler();
                            mHand.postDelayed(new Runnable() {

                                @Override
                                public void run() {


                                    myfeid.replaceContent(new FragmentEidInvalid());
                                    feidnegselected = false;
                                    feidpositiveselected = false;
                                    feidInvalidSelected = true;

                                    pr.DissmissProgress();
                                    //Dismiss progressBar here

                                }
                            }, 1500);


                        }
//                      Toast.makeText(MainActivity.this, ""+bottomNavigation.getItem(1).getTitle(getApplicationContext()), Toast.LENGTH_SHORT).show();
                        break;


                }
                return true;
            }
        });
        bottomNavigation.setOnNavigationPositionListener(new AHBottomNavigation.OnNavigationPositionListener() {
            @Override
            public void onPositionChange(int y) {
                // Manage the new y position
            }
        });


    }


    private View prepareTabView(int pos) {
        View view = getLayoutInflater().inflate(R.layout.custom_tab, null);
        TextView tv_title = (TextView) view.findViewById(R.id.tv_title);
        TextView tv_count = (TextView) view.findViewById(R.id.tv_count);
        tv_title.setText(tabTitle[pos]);
        if (unreadCount[pos] > 0) {
            tv_count.setVisibility(View.VISIBLE);
            tv_count.setText("" + unreadCount[pos]);
        } else
            tv_count.setVisibility(View.GONE);


        return view;
    }


    private void setCustomColour() {

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        statusBarColour = sp.getString("status_bar_colours", "#000066");
        toolBarColour = sp.getString("tool_bar_colours", "#3333ff");
        tabLayoutColour = sp.getString("tablayout_colours", "#4d4dff");
        backgroundColour = sp.getString("background_colours", "#f2f2f2");

        sbColour = Color.parseColor(statusBarColour);
        tbColour = Color.parseColor(toolBarColour);
        tlColour = Color.parseColor(tabLayoutColour);
        bgColour = Color.parseColor(backgroundColour);

        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(sbColour);

        }

        toolbar.setBackgroundColor(tbColour);
        tabLayout.setBackgroundColor(tlColour);
        viewPager.setBackgroundColor(bgColour);


    }

    private void getDefaultSettings() {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        boolean ds = sp.getBoolean("default_colour", false);
        if (ds) {
            toolbar.setBackgroundColor(Color.parseColor("#3333ff"));
            if (Build.VERSION.SDK_INT >= 21) {
                getWindow().setStatusBarColor(Color.parseColor("#000066"));
            }
            tabLayout.setBackgroundColor(Color.parseColor("#4d4dff"));
            viewPager.setBackgroundColor(Color.parseColor("#f2f2f2"));
        } else {
            setCustomColour();
        }


    }

    public void getFragTags() {
//        fragmenttags.deleteAll(fragmenttags.class);

        List<fragmenttags> myl = fragmenttags.findWithQuery(fragmenttags.class, "select * from fragmenttags");
        System.out.println("*************frag tags*************************************");
        for (int x = 0; x < myl.size(); x++) {

            System.out.println(myl.get(x).getFragname() + " : " + myl.get(x).getTagname());

        }
    }

    public void setAllFragTag() {

        String allFragmentTag = makeFragmentName(viewPager.getId(), 1);
        List<fragmenttags> mylf = fragmenttags.findWithQuery(fragmenttags.class, "select * from fragmenttags where fragname=?", "all");
        if (mylf.size() > 0) {


        } else {
            fragmenttags ft = new fragmenttags();
            ft.setFragname("all");
            ft.setTagname(allFragmentTag);
            ft.save();

        }


    }

    public void setVlFragTag() {

        String allFragmentTag = makeFragmentName(viewPager.getId(), 3);
        List<fragmenttags> mylf = fragmenttags.findWithQuery(fragmenttags.class, "select * from fragmenttags where fragname=?", "vl");
        if (mylf.size() > 0) {


        } else {
            fragmenttags ft = new fragmenttags();
            ft.setFragname("vl");
            ft.setTagname(allFragmentTag);
            ft.save();

        }


    }

    public void setEidFragTag() {

        String allFragmentTag = makeFragmentName(viewPager.getId(), 2);
        List<fragmenttags> mylf = fragmenttags.findWithQuery(fragmenttags.class, "select * from fragmenttags where fragname=?", "eid");
        if (mylf.size() > 0) {


        } else {
            fragmenttags ft = new fragmenttags();
            ft.setFragname("eid");
            ft.setTagname(allFragmentTag);
            ft.save();

        }


    }

    //add icons to tabs menu
    private void setupTabIcons() {
//        tabLayout.getTabAt(0).setIcon(R.mipmap.ic_launcher);
//        tabLayout.getTabAt(1).setIcon(R.mipmap.ic_launcher);
//        tabLayout.getTabAt(2).setIcon(R.mipmap.ic_launcher);
//        tabLayout.getTabAt(3).setIcon(R.mipmap.ic_launcher);
//        tabLayout.getTabAt(4).setIcon(R.mipmap.ic_launcher);

        for (int i = 0; i < tabTitle.length; i++) {
            /*TabLayout.Tab tabitem = tabLayout.newTab();
            tabitem.setCustomView(prepareTabView(i));
            tabLayout.addTab(tabitem);*/

            tabLayout.getTabAt(i).setCustomView(prepareTabView(i));
        }
    }

    private void setupViewPager(ViewPager viewPager) {
        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new FragmentAll(), "All");  //add the fragments to every tab
//        adapter.addFragment(new FragmentCd4(), "CD4");
        adapter.addFragment(new FragmentEidAll(), "EID");
        adapter.addFragment(new FragmentVlAll(), "VL");
        adapter.addFragment(new ReportsFragment(), "EID REPORTS");
        adapter.addFragment(new ReportsFragmentVL(), "VL REPORTS");


//        adapter.addFragment(new FragmentInvalid(), "INVALID");
        viewPager.setAdapter(adapter);

        setAllFragTag();
        setEidFragTag();
        setVlFragTag();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
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

            if ((diff / 1000) > 180) {
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        switch (id) {
            case R.id.action_settings:
                Intent myint = new Intent(getApplicationContext(), Settings.class);
                startActivity(myint);
                return true;

            case R.id.help_line:
//                String PhoneNo = "+254713559850";
//                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + PhoneNo));
//                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
//                    // TODO: Consider calling
//                    //    ActivityCompat#requestPermissions
//                    // here to request the missing permissions, and then overriding
//                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//                    //                                          int[] grantResults)
//                    // to handle the case where the user grants the permission. See the documentation
//                    // for ActivityCompat#requestPermissions for more details.
////                    return ;
//                }
//                startActivity(intent);
                MydialogBuilder("Choose how to communicate with our helpline", "mLab HelpLine");
                return true;

            case R.id.logout:

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

                return true;

            case R.id.action_search:
                handleMenuSearch();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public int getVlCount() {
        int value = 0;
        try {

//            ContentResolver contentResolver = getActivity().getContentResolver();
//            Cursor smsInboxCursor = contentResolver.query(Uri.parse("content://sms/inbox"), null, "address='40147'", null, null);
//            int indexBody = smsInboxCursor.getColumnIndex("body");
//            int indexAddress = smsInboxCursor.getColumnIndex("address");
            List<Messages> bdy = Messages.findWithQuery(Messages.class, "Select * from Messages where m_body like'%FFViral%' group by m_body", null);

            if (bdy.isEmpty()) {
                value = 0;

            } else {

                value = bdy.size();
            }


        } catch (Exception e) {

        }

        return value;
    }

    public int getAllCount() {
        int value = 0;
        try {

//            ContentResolver contentResolver = getActivity().getContentResolver();
//            Cursor smsInboxCursor = contentResolver.query(Uri.parse("content://sms/inbox"), null, "address='40147'", null, null);
//            int indexBody = smsInboxCursor.getColumnIndex("body");
//            int indexAddress = smsInboxCursor.getColumnIndex("address");
            List<Messages> bdy = Messages.findWithQuery(Messages.class, "Select * from Messages group by m_body", null);

//            List<Messages> bdy = Messages.findWithQuery(Messages.class, "Select * from Messages where m_body like'%FFViral%' group by m_body", null);
            if (bdy.isEmpty()) {
                value = 0;

            } else {

                value = bdy.size();


            }


        } catch (Exception e) {

        }

        return value;
    }

    public int getFfeidCount() {
        int value = 0;
        try {

//            ContentResolver contentResolver = getActivity().getContentResolver();
//            Cursor smsInboxCursor = contentResolver.query(Uri.parse("content://sms/inbox"), null, "address='40147'", null, null);
//            int indexBody = smsInboxCursor.getColumnIndex("body");
//            int indexAddress = smsInboxCursor.getColumnIndex("address");
            List<Messages> bdy = Messages.findWithQuery(Messages.class, "Select * from Messages where m_body like'%FFEID%' group by m_body", null);

            if (bdy.isEmpty()) {
                value = 0;

            } else {

                value = bdy.size();
            }


        } catch (Exception e) {

        }

        return value;
    }

    public int geteidNegativeCount() {
        int value = 0;
        try {

//            ContentResolver contentResolver = getActivity().getContentResolver();
//            Cursor smsInboxCursor = contentResolver.query(Uri.parse("content://sms/inbox"), null, "address='40147'", null, null);
//            int indexBody = smsInboxCursor.getColumnIndex("body");
//            int indexAddress = smsInboxCursor.getColumnIndex("address");
            List<Messages> bdy = Messages.findWithQuery(Messages.class, "Select * from Messages where m_body like'%FFEID%Negative%' group by m_body", null);

            if (bdy.isEmpty()) {
                value = 0;

            } else {

                value = bdy.size();
            }


        } catch (Exception e) {

        }

        return value;
    }

    public int geteidPositiveCount() {
        int value = 0;
        try {


            List<Messages> bdy = Messages.findWithQuery(Messages.class, "Select * from Messages where m_body like'%FFEID%Positive%' group by m_body", null);

            if (bdy.isEmpty()) {
                value = 0;

            } else {

                value = bdy.size();
            }


        } catch (Exception e) {

        }

        return value;
    }

    public int geteidInvalidCount() {
        int value = 0;
        try {


            List<Messages> bdy = Messages.findWithQuery(Messages.class, "Select * from Messages where m_body like'%FFEID%' group by m_body", null);

            if (bdy.isEmpty()) {
                value = 0;

            } else {
                for (int x = 0; x < bdy.size(); x++) {

                    String messbdy = bdy.get(x).getmBody();

                    if ((messbdy.contains("Collect New Sample") || messbdy.contains("Collect new sexample") || messbdy.contains("Invalid") || messbdy.contains("Failed"))) {
                        value += 1;
                    }
                }

            }


        } catch (Exception e) {

        }

        return value;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        mSearchAction = menu.findItem(R.id.action_search);
        return super.onPrepareOptionsMenu(menu);
    }

    public void closeSearch(boolean x) {
        ActionBar action = getSupportActionBar();
        if (x) {
            action.setDisplayShowCustomEnabled(false);
            action.setDisplayShowTitleEnabled(true);

        } else {

            action.setDisplayShowCustomEnabled(false); //disable a custom view inside the actionbar
            action.setDisplayShowTitleEnabled(true); //show the title in the action bar


            //add the search icon in the action bar
            mSearchAction.setIcon(getResources().getDrawable(R.mipmap.search));

            getWindow().setSoftInputMode(
                    WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
            );

            isSearchOpened = false;

        }
    }

    protected void handleMenuSearch() {
        ActionBar action = getSupportActionBar(); //get the actionbar

        if (isSearchOpened) { //test if the search is open

            action.setDisplayShowCustomEnabled(false); //disable a custom view inside the actionbar
            action.setDisplayShowTitleEnabled(true); //show the title in the action bar

            //hides the keyboard
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(edtSeach.getWindowToken(), 0);

            //add the search icon in the action bar
            mSearchAction.setIcon(getResources().getDrawable(R.mipmap.search));

            isSearchOpened = false;
        } else { //open the search entry

            action.setDisplayShowCustomEnabled(true); //enable it to display a
            // custom view in the action bar.
            action.setCustomView(R.layout.search_bar);//add the custom view
            action.setDisplayShowTitleEnabled(false); //hide the title

            edtSeach = (EditText) action.getCustomView().findViewById(R.id.edtSearch); //the text editor

            //this is a listener to do a search when the user clicks on search button
//            edtSeach.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//                @Override
//                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//                    if (actionId == EditorInfo.IME_ACTION_SEARCH) {
//                        doSearch();
//                        return true;
//                    }
//                    return false;
//                }
//            });

            edtSeach.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
//                    Toast.makeText(MainActivity.this, "searching", Toast.LENGTH_SHORT).show();
                    doSearch(s);

                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });


            edtSeach.requestFocus();

            //open the keyboard focused in the edtSearch
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(edtSeach, InputMethodManager.SHOW_IMPLICIT);


            //add the close icon
            mSearchAction.setIcon(getResources().getDrawable(R.mipmap.cancel));


            isSearchOpened = true;
        }
    }

    protected void handleMenuSearch2() {
        ActionBar action = getSupportActionBar(); //get the actionbar

        if (isSearchOpened) { //test if the search is open

            action.setDisplayShowCustomEnabled(false); //disable a custom view inside the actionbar
            action.setDisplayShowTitleEnabled(true); //show the title in the action bar

            //hides the keyboard
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(edtSeach.getWindowToken(), 0);

            //add the search icon in the action bar
            mSearchAction.setIcon(getResources().getDrawable(R.mipmap.search));

            isSearchOpened = false;
        } else { //open the search entry

            action.setDisplayShowCustomEnabled(true); //enable it to display a
            // custom view in the action bar.
            action.setCustomView(R.layout.search_bar);//add the custom view
            action.setDisplayShowTitleEnabled(false); //hide the title

            edtSeach = (EditText) action.getCustomView().findViewById(R.id.edtSearch); //the text editor

            //this is a listener to do a search when the user clicks on search button
//            edtSeach.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//                @Override
//                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//                    if (actionId == EditorInfo.IME_ACTION_SEARCH) {
//                        doSearch();
//                        return true;
//                    }
//                    return false;
//                }
//            });

            edtSeach.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
//                    Toast.makeText(MainActivity.this, "searching", Toast.LENGTH_SHORT).show();
                    doSearch(s);

                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });


//            edtSeach.requestFocus();

            //open the keyboard focused in the edtSearch
//            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//            imm.showSoftInput(edtSeach, InputMethodManager.SHOW_IMPLICIT);


            //add the close icon
//            mSearchAction.setIcon(getResources().getDrawable(R.mipmap.cancel));


            isSearchOpened = true;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        Toast.makeText(this, "back pressed", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        try {

            List myl2 = Messages.findWithQuery(Messages.class, "Select * from Messages limit 1");

            for (int x = 0; x < myl2.size(); x++) {

                Messages ms = (Messages) myl2.get(x);
                ms.getId();
                System.out.println("original string timestamp");
                System.out.println(ms.getmTimeStamp());
//                System.out.println("converted timestamp");
//                ms.setRead("read");
//                    Toast.makeText(getActivity(), "id: "+ms.getId(), Toast.LENGTH_SHORT).show();
//                ms.save();
//                Toast.makeText(this, "success reading messages", Toast.LENGTH_SHORT).show();
            }

//            List myl=Messages.findWithQuery(Messages.class,"Select * from Messages");
//            for(int x=0;x<myl.size();x++){
//
//                Messages ms=(Messages) myl.get(x);
//                ms.getId();
//                ms.setRead("read");
////                    Toast.makeText(getActivity(), "id: "+ms.getId(), Toast.LENGTH_SHORT).show();
//                ms.save();
////                Toast.makeText(this, "success reading messages", Toast.LENGTH_SHORT).show();
//            }
        } catch (Exception e) {
            Toast.makeText(this, "error reading messages " + e, Toast.LENGTH_SHORT).show();

        }

    }

    @Override
    protected void onPostResume() {
        super.onPostResume();

    }

    @Override
    protected void onStop() {
        super.onStop();
        if (pd != null) {
            pd.dismiss();
            pd = null;
        }


//        Toast.makeText(this, "onstop called", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (pd != null) {
            pd.dismiss();
            pd = null;
        }
//        Toast.makeText(this, "ondestroy called", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStart() {
        super.onStart();
//        Toast.makeText(this, "onstart created", Toast.LENGTH_SHORT).show();
    }

    public void MydialogBuilder(final String message, final String title) {
        final boolean[] exiting = {false};
        AlertDialog.Builder b = new AlertDialog.Builder(this);

        b.setMessage(message + "\n");
        b.setTitle(title);
        b.setCancelable(false);

        b.setNegativeButton("CALL", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {


                String PhoneNo = "+254721513172";

                mc.initiateCall(PhoneNo);


                dialog.cancel();
            }
        });

        b.setNeutralButton("MESSAGE", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                sm.sendMessageApi("type message here", msc.sendSmsShortcode);


            }
        });
        b.setPositiveButton("CANCEL", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                dialog.cancel();

            }
        });

        AlertDialog a = b.create();

        a.show();

        Button bq = a.getButton(DialogInterface.BUTTON_NEGATIVE);
        Button bn = a.getButton(DialogInterface.BUTTON_NEUTRAL);
        Button bP = a.getButton(DialogInterface.BUTTON_POSITIVE);
        bq.setTextColor(Color.GREEN);
        bn.setTextColor(Color.BLUE);
        bP.setTextColor(Color.RED);

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

            sm.sendMessageApi(messReport, msc.sendSmsShortcode);

        } catch (Exception e) {

        }
    }

    public void displayDbVcounts() {

        try {
            System.out.println("viral count***************************************************************************viral count");

            List<Messages> bdy = Messages.findWithQuery(Messages.class, "Select * from Messages group by m_body", null);
            for (int x = 0; x < bdy.size(); x++) {
                String messbdy = bdy.get(x).getmBody();
                String count = bdy.get(x).getViralCount();

                System.out.println(messbdy + "**count****" + count);


            }


            System.out.println("viral count***************************************************************************viral count");


        } catch (Exception e) {


        }
    }

    /********************************************************************************/

    //start sms retriever api functions

    //function triggered when there is an incoming message from receiver
    private void listenForIncomingMessage() {

        this.mCredentialsApiClient = (new GoogleApiClient.Builder((Context) this)).addApi(Auth.CREDENTIALS_API).build();
        this.startSMSListener();
        this.smsBroadcast.initOTPListener((SmsReceiver.MessageReceiveListener) this);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.google.android.gms.auth.api.phone.SMS_RETRIEVED");
        this.getApplicationContext().registerReceiver((BroadcastReceiver) this.smsBroadcast, intentFilter);


    }

    //    function triggered when the application is in background or closed
    private void initiateBackgroundService() {

        //background code after every 5 seconds


        Intent alarm = new Intent(MainActivity.this, SmsReceiver.class);
        boolean alarmRunning = (PendingIntent.getBroadcast(MainActivity.this, 0, alarm, PendingIntent.FLAG_NO_CREATE) != null);
        if (alarmRunning == false) {
            PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, alarm, 0);
            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime(), 5000, pendingIntent);
        }

        //background code

    }

    //    function triggered when the actual message is received from our receiver
    public void onMessageReceived(@NotNull String otp) {
        Intrinsics.checkParameterIsNotNull(otp, "otp");
        LocalBroadcastManager.getInstance((Context) this).unregisterReceiver((BroadcastReceiver) this.smsBroadcast);

        saveReceivedMessage(splittedString(otp));
//        populateListView();

//        Toast.makeText(this, "message " + splittedString(otp), Toast.LENGTH_LONG).show();
    }

    private void saveReceivedMessage(String str) {

        pm.processReceivedMessage(str);

    }

    public void onMessageTimeOut() {


    }

    private final void startSMSListener() {
        SmsRetriever.getClient((Activity) this).startSmsRetriever().addOnSuccessListener((OnSuccessListener) (new OnSuccessListener() {

            public void onSuccess(Object var1) {
                this.onSuccess((Void) var1);
            }

            public final void onSuccess(Void it) {
//                TextView otpTxtView = (TextView) findViewById(R.id.tv1);
//                Intrinsics.checkExpressionValueIsNotNull(otpTxtView, "otpTxtView");
//                otpTxtView.setText((CharSequence) "Waiting for message");

//                Toast.makeText(getApplicationContext(), "SMS Retriever starts", Toast.LENGTH_SHORT).show();
            }
        })).addOnFailureListener((OnFailureListener) (new OnFailureListener() {
            public final void onFailure(@NotNull Exception it) {
                Intrinsics.checkParameterIsNotNull(it, "it");
//                TextView otpTextView = (TextView) findViewById(R.id.tv1);
//                Intrinsics.checkExpressionValueIsNotNull(otpTextView, "otpTxtView");
//                otpTextView.setText((CharSequence) "Cannot Start SMS Retriever");

                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
            }
        }));
    }

    protected void onActivityResult(int requestCode, int resultCode, @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == this.RC_HINT && resultCode == -1) {

            if (data == null) {
                Intrinsics.throwNpe();
            }

            Parcelable credentials = data.getParcelableExtra("com.google.android.gms.credentials.Credential");
            Intrinsics.checkExpressionValueIsNotNull(credentials, "data!!.getParcelableExtra(Credential.EXTRA_KEY)");
            Credential credential = (Credential) credentials;
            String credString = "credential : " + credential;
            System.out.print(credString);
        }

    }

    private class AsyncTaskRunner extends AsyncTask<String, String, String> {


        @Override
        protected String doInBackground(String... params) {
            incomingMessage = false;
            method();
            return null;
        }

        @Override
        protected void onPreExecute() {

            super.onPreExecute();

//            Toast.makeText(MainActivity.this, "getting messages", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
//            Toast.makeText(MainActivity.this, "done getting messages", Toast.LENGTH_SHORT).show();
        }
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();
        HashMap<Integer, Fragment> mPageReferenceMap = new HashMap<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            Fragment fragment = (Fragment) super.instantiateItem(container, position);
            mPageReferenceMap.put(position, fragment);
            return fragment;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            super.destroyItem(container, position, object);
            mPageReferenceMap.remove(position);
        }

        public Fragment getFragment(int key) {
            return mPageReferenceMap.get(key);
        }


        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);//enables displaying the title
            //return null;//disables the displaying of title on tabs
        }

    }

    //end sms retriever api functions

/************************************************************************************/

}

package com.example.kenweezy.mytablayouts;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.SmsManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private Toolbar toolbar;

    private MenuItem mSearchAction;
    private boolean isSearchOpened = false;
    private EditText edtSeach;

    String statusBarColour;
    String toolBarColour;
    String tabLayoutColour;
    String backgroundColour;
    public int mval;
    boolean vlSelected;
    boolean eidSelected;
    boolean allSelected;
    boolean reportsSelected, reportseidselected, reportsvlselected;
    boolean feidnegselected, feidpositiveselected, feidInvalidSelected;


    int sbColour, tbColour, tlColour, bgColour;

    String[] tabTitle = {"ALL", "EID", "VL", "EID REPORTS", "VL REPORTS"};
    int[] unreadCount = {0, 0, 0, 0, 0};

    public static final String LOGGED_IN = "logged_in";

    public static final String SETTING_INFOS = "SETTING_Infos";

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);




        //  Toast.makeText(this, "oncreate method called", Toast.LENGTH_SHORT).show();

        feidnegselected = false;
        feidpositiveselected = false;
        feidInvalidSelected = false;
        try {
//            Messages ms = new Messages("kennedy", "sitati");
//            ms.save();
            List<Messages> myl = Messages.listAll(Messages.class);

            if (myl.size() == 0) {

                refreshSmsInboxTest();
            } else {

//                refreshSmsInboxTest();
//                for(int x=0;x<myl.size();x++){
//                    Toast.makeText(this, ""+myl.get(x).getmTimeStamp(), Toast.LENGTH_SHORT).show();
//
//                }


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

//        sendReadReport();


//        bottomNavigation.setNotificationBackgroundColorResource(R.color.colorPrimary);

//        vlSelected=false;
//        eidSelected=false;
//        allSelected=true;
//        reportsSelected=false;


//        vlSelected=false;
//        eidSelected=true;
//        allSelected=false;
        MyBottomNav();

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);


        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#f2f2f2"));
        tabLayout.setSelectedTabIndicatorHeight(5);


        newadapt = ((ViewPagerAdapter) viewPager.getAdapter());


        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                int index = position;
                bottomNavigation.setNotificationBackgroundColorResource(R.color.colorPrimaryDark);
//                bottomNavigation.setBackgroundColor(Color.WHITE);
                bottomNavigation.setNotificationTextColorResource(R.color.textColorPrimary);
//                bottomNavigation.setNotificationTextColorResource(Color.WHITE);


                switch (position) {

                    case 0:
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
//                        myfall.test();
//                        Toast.makeText(getApplicationContext(), "fragment"+myfall, Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
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
                        break;

                    case 2:
                        vlSelected = true;
                        eidSelected = false;
                        allSelected = false;
                        reportseidselected = false;
                        reportsvlselected = false;
                        MyBottomNav();
                        if (getvlSuppressedCount() > 0) {
                            bottomNavigation.setNotification(Integer.toString(getvlSuppressedCount()), 0);

                        } else {
                            bottomNavigation.setNotification("", 0);

                        }

                        if (getvlUnsuppressedCount() > 0) {
                            bottomNavigation.setNotification(Integer.toString(getvlUnsuppressedCount()), 1);

                        } else {
                            bottomNavigation.setNotification("", 1);
                        }

                        if (getvlInvalidCount() > 0) {
                            bottomNavigation.setNotification(Integer.toString(getvlInvalidCount()), 2);

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
//                        Toast.makeText(getApplicationContext(), "fragment"+myfvl, Toast.LENGTH_SHORT).show();
                        break;

                    case 3:
                        vlSelected = false;
                        eidSelected = false;
                        allSelected = false;
                        reportseidselected = true;
                        reportsvlselected = false;
                        MyBottomNav();

                        freport = (ReportsFragment) newadapt.getFragment(index);
                        bottomNavigation.setNotification("", 0);
                        bottomNavigation.setNotification("", 1);
                        bottomNavigation.setNotification("", 2);
                        falls = false;
                        feids = false;
                        fvls = false;

                        if (mSearchAction.isVisible()) {
                            mSearchAction.setVisible(false);
                        }
                        closeSearch(true);
//                        Toast.makeText(getApplicationContext(), "fragment"+myfvl, Toast.LENGTH_SHORT).show();
                        break;

                    case 4:
                        vlSelected = false;
                        eidSelected = false;
                        allSelected = false;
                        reportseidselected = false;
                        reportsvlselected = true;
                        MyBottomNav();

                        freportvl = (ReportsFragmentVL) newadapt.getFragment(index);
                        bottomNavigation.setNotification("", 0);
                        bottomNavigation.setNotification("", 1);
                        bottomNavigation.setNotification("", 2);
                        falls = false;
                        feids = false;
                        fvls = false;

                        if (mSearchAction.isVisible()) {
                            mSearchAction.setVisible(false);
                        }
                        closeSearch(true);
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

//
//    public void listAllMessages(List<Messages> myl){
//        Toast.makeText(this, "total messages "+myl.size(), Toast.LENGTH_SHORT).show();
//
//        for(int x=0;x<myl.size();x++){
//
//            DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss.SSS");
//            DateFormat secformat=new SimpleDateFormat("mm");
//
//            Calendar calendar = Calendar.getInstance();
//
//            String ndate =  myl.get(x).getmTimeStamp();
//            Long timestamp = Long.parseLong(ndate);
//
//
//            long now = timestamp;
//            calendar.setTimeInMillis(now);
//
//            String mysec=formatter.format(calendar.getTime());
//            Toast.makeText(this, "Address: "+myl.get(x).getmAddress()+"Body: "+myl.get(x).getmBody()+" Time:"+mysec, Toast.LENGTH_SHORT).show();
//        }
//    }

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
        AHBottomNavigationItem rweekly = new AHBottomNavigationItem("Weekly", R.mipmap.reorder, R.color.colorAccent);
        AHBottomNavigationItem ryearly = new AHBottomNavigationItem("Yearly", R.mipmap.reorder, R.color.colorPrimary);

        AHBottomNavigationItem rvlmonthly = new AHBottomNavigationItem("Monthly VL", R.mipmap.reorder, R.color.colorPrimary);
        AHBottomNavigationItem rvlweekly = new AHBottomNavigationItem("Weekly VL", R.mipmap.reorder, R.color.colorAccent);
        AHBottomNavigationItem rvlyearly = new AHBottomNavigationItem("Yearly VL", R.mipmap.reorder, R.color.colorPrimary);

// Add items

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
            bottomNavigation.addItem(rweekly);
            bottomNavigation.addItem(rmonthly);
            bottomNavigation.addItem(ryearly);

            // Set background color
            if (bottomNavigation.isHidden()) {
                bottomNavigation.restoreBottomNavigation();
            }
            bottomNavigation.setDefaultBackgroundColor(Color.parseColor("#ffffff"));


        } else if (reportsvlselected) {

            bottomNavigation.removeAllItems();
            bottomNavigation.addItem(rvlweekly);
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


//
                            myfvl.replaceContent(new FragmentVlSuppressed());
//
                        } else if (bottomNavigation.getItem(0).getTitle(getApplicationContext()).toString() == "Weekly") {


//
                            freport.replaceContent(new FragmentReportsEidWeekly());
//
                        } else if (bottomNavigation.getItem(0).getTitle(getApplicationContext()).toString() == "Weekly VL") {


//
                            freportvl.replaceContent(new FragmentReportsWeekly());
//
                        } else {

                            myfeid.replaceContent(new FragmentEidNegative());

                            feidnegselected = true;
                            feidpositiveselected = false;
                        }

                        break;
                    case 1:

                        if (bottomNavigation.getItem(1).getTitle(getApplicationContext()).toString() == "UnSuppressed") {


                            myfvl.replaceContent(new FragmentVlUnsuppressed());
//
                        } else if (bottomNavigation.getItem(1).getTitle(getApplicationContext()).toString() == "Monthly") {


//
                            freport.replaceContent(new FragmentReportsEidMonthly());
//
                        } else if (bottomNavigation.getItem(1).getTitle(getApplicationContext()).toString() == "Monthly VL") {


//
                            freportvl.replaceContent(new FragmentReportsMonthly());
//
                        } else {
                            myfeid.replaceContent(new FragmentEidPositive());
                            feidpositiveselected = true;
                            feidnegselected = false;
                            feidInvalidSelected = false;


                        }
//                      Toast.makeText(MainActivity.this, ""+bottomNavigation.getItem(1).getTitle(getApplicationContext()), Toast.LENGTH_SHORT).show();
                        break;

                    case 2:

                        if (bottomNavigation.getItem(2).getTitle(getApplicationContext()).toString() == "Invalid") {
                            myfvl.replaceContent(new FragmentVlInvalid());
//
                        } else if (bottomNavigation.getItem(2).getTitle(getApplicationContext()).toString() == "Yearly") {


//
                            freport.replaceContent(new FragmentReportsEidYearly());
//
                        } else if (bottomNavigation.getItem(2).getTitle(getApplicationContext()).toString() == "Yearly VL") {


//
                            freportvl.replaceContent(new FragmentReportsYearly());
//
                        } else {

                            myfeid.replaceContent(new FragmentEidInvalid());
                            feidnegselected = false;
                            feidpositiveselected = false;
                            feidInvalidSelected = true;

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

    public void getFragId() {

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment_byID = fm.findFragmentById(R.id.viewpager);
        Toast.makeText(this, "" + fragment_byID, Toast.LENGTH_SHORT).show();
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

    public void call() {
//        String phone="0713559850";
//        Intent callIntent = new Intent(Intent.ACTION_CALL);
//        callIntent.setData(Uri.parse("tel:" + phone));
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//            return;
//        }
//        startActivity(callIntent);
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
                MydialogBuilder("Choose how to communicate with our helpline","mLab HelpLine");
                return true;

            case R.id.logout:

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

                return true;

            case R.id.action_search:
                handleMenuSearch();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public int getVlCount() {
        int value=0;
        try {

//            ContentResolver contentResolver = getActivity().getContentResolver();
//            Cursor smsInboxCursor = contentResolver.query(Uri.parse("content://sms/inbox"), null, "address='40147'", null, null);
//            int indexBody = smsInboxCursor.getColumnIndex("body");
//            int indexAddress = smsInboxCursor.getColumnIndex("address");
            List<Messages> bdy = Messages.findWithQuery(Messages.class, "Select * from Messages where m_body like'%FFViral%' group by m_body", null);

            if (bdy.isEmpty()) {
                value=0;

            }
            else{

                value=bdy.size();
            }




        }
        catch(Exception e){

        }

return value;
    }



   public int getAllCount() {
        int value=0;
        try {

//            ContentResolver contentResolver = getActivity().getContentResolver();
//            Cursor smsInboxCursor = contentResolver.query(Uri.parse("content://sms/inbox"), null, "address='40147'", null, null);
//            int indexBody = smsInboxCursor.getColumnIndex("body");
//            int indexAddress = smsInboxCursor.getColumnIndex("address");
            List<Messages> bdy = Messages.findWithQuery(Messages.class, "Select * from Messages group by m_body", null);

//            List<Messages> bdy = Messages.findWithQuery(Messages.class, "Select * from Messages where m_body like'%FFViral%' group by m_body", null);
            if (bdy.isEmpty()) {
                value=0;

            }
            else{

                value=bdy.size();


            }




        }
        catch(Exception e){

        }

        return value;
    }


   public int getFfeidCount() {
        int value=0;
        try {

//            ContentResolver contentResolver = getActivity().getContentResolver();
//            Cursor smsInboxCursor = contentResolver.query(Uri.parse("content://sms/inbox"), null, "address='40147'", null, null);
//            int indexBody = smsInboxCursor.getColumnIndex("body");
//            int indexAddress = smsInboxCursor.getColumnIndex("address");
            List<Messages> bdy = Messages.findWithQuery(Messages.class, "Select * from Messages where m_body like'%FFEID%' group by m_body", null);

            if (bdy.isEmpty()) {
                value=0;

            }
            else{

                value=bdy.size();
            }




        }
        catch(Exception e){

        }

        return value;
    }


    public int geteidNegativeCount() {
        int value=0;
        try {

//            ContentResolver contentResolver = getActivity().getContentResolver();
//            Cursor smsInboxCursor = contentResolver.query(Uri.parse("content://sms/inbox"), null, "address='40147'", null, null);
//            int indexBody = smsInboxCursor.getColumnIndex("body");
//            int indexAddress = smsInboxCursor.getColumnIndex("address");
            List<Messages> bdy = Messages.findWithQuery(Messages.class, "Select * from Messages where m_body like'%FFEID%Negative%' group by m_body", null);

            if (bdy.isEmpty()) {
                value=0;

            }
            else{

                value=bdy.size();
            }




        }
        catch(Exception e){

        }

        return value;
    }

    public int geteidPositiveCount() {
        int value=0;
        try {


            List<Messages> bdy = Messages.findWithQuery(Messages.class, "Select * from Messages where m_body like'%FFEID%Positive%' group by m_body", null);

            if (bdy.isEmpty()) {
                value=0;

            }
            else{

                value=bdy.size();
            }




        }
        catch(Exception e){

        }

        return value;
    }



    public int geteidInvalidCount() {
        int value=0;
        try {


            List<Messages> bdy = Messages.findWithQuery(Messages.class, "Select * from Messages where m_body like'%FFEID%' group by m_body", null);

            if (bdy.isEmpty()) {
                value=0;

            }
            else{
                for(int x=0;x<bdy.size();x++){

                    String messbdy=bdy.get(x).getmBody();

                    if((messbdy.contains("Collect new sample")||messbdy.contains("Invalid"))){
                        value+=1;
                    }
                }

            }




        }
        catch(Exception e){

        }

        return value;
    }


    public int getvlSuppressedCount() {
        int value=0;
        try {


            List<Messages> bdy = Messages.findWithQuery(Messages.class, "Select * from Messages where m_body like'%FFViral%' group by m_body", null);

//        if (bdy.isEmpty())
//            return 0;
//        myadapter.clear();


            for(int x=0;x<bdy.size();x++){

                String messbdy=bdy.get(x).getmBody();
                String ndate = bdy.get(x).getmTimeStamp();

                if(!(messbdy.contains("Collect new sample")||messbdy.contains("Invalid"))){



                    String[] mymessarray=messbdy.split(":");


                    String splitVal="";

                    if(messbdy.contains("Sex") && messbdy.contains("Age")){
                        splitVal=mymessarray[6];

                    }
                    else{

                        splitVal=mymessarray[3];
                    }


                    String[] splitvalarray=splitVal.split("\\s+");


                    if(splitvalarray[0].contains("<")){

                        value += 1;



                    }

                    else{


                        int myval=Integer.parseInt(splitvalarray[0]);
                        if(myval>1000){


                        }
                        else{

                            value += 1;




                        }


                    }




                }





            }




        }
        catch(Exception e){

        }

        return value;
    }




    public int getvlUnsuppressedCount() {
        int value=0;
        try {


            List<Messages> bdy = Messages.findWithQuery(Messages.class, "Select * from Messages where m_body like'%FFViral%' group by m_body", null);

//        if (bdy.isEmpty())
//            return 0;
//        myadapter.clear();


            for(int x=0;x<bdy.size();x++){

                String messbdy=bdy.get(x).getmBody();
                String ndate = bdy.get(x).getmTimeStamp();

                if(!(messbdy.contains("Collect new sample")||messbdy.contains("Invalid"))){

                    String[] mymessarray=messbdy.split(":");

                    String splitVal="";

                    if(messbdy.contains("Sex") && messbdy.contains("Age")){
                        splitVal=mymessarray[6];

                    }
                    else{

                        splitVal=mymessarray[3];
                    }


                    String[] splitvalarray=splitVal.split("\\s+");


                    if(splitvalarray[0].contains("<")){





                    }

                    else{


                        int myval=Integer.parseInt(splitvalarray[0]);
                        if(myval>1000){

                            value += 1;


                        }
                        else{



                        }


                    }


                }





            }




        }
        catch(Exception e){

        }

        return value;
    }



    public int getvlUnsuppressedCount23() {
        int value=0;
        try {

//            ContentResolver contentResolver = getActivity().getContentResolver();
//            Cursor smsInboxCursor = contentResolver.query(Uri.parse("content://sms/inbox"), null, "address='40147'", null, null);
//            int indexBody = smsInboxCursor.getColumnIndex("body");
//            int indexAddress = smsInboxCursor.getColumnIndex("address");
            List<Messages> bdy = Messages.findWithQuery(Messages.class, "Select * from Messages where m_body like'%FFViral%LDL%' and m_body not like'%Invalid%' and read=? group by m_body", "unread");

            if (bdy.isEmpty()) {
                value=0;

            }
            else{

                value=bdy.size();
            }




        }
        catch(Exception e){

        }

        return value;
    }



    public int getvlInvalidCount() {
        int value=0;
        try {

//            ContentResolver contentResolver = getActivity().getContentResolver();
//            Cursor smsInboxCursor = contentResolver.query(Uri.parse("content://sms/inbox"), null, "address='40147'", null, null);
//            int indexBody = smsInboxCursor.getColumnIndex("body");
//            int indexAddress = smsInboxCursor.getColumnIndex("address");
            List<Messages> bdy = Messages.findWithQuery(Messages.class, "Select * from Messages where m_body like'%FFViral%' group by m_body",null);

            if (bdy.isEmpty()) {
                value=0;

            }
            else{
                for(int x=0;x<bdy.size();x++){

                    String messbdy=bdy.get(x).getmBody();
                    if((messbdy.contains("Collect new sample")||messbdy.contains("Invalid"))){
                        value+=1;
                    }
                }


            }




        }
        catch(Exception e){

        }

        return value;
    }



    public void refreshSmsInboxTest() {
        try {
            ContentResolver contentResolver = getContentResolver();
            Cursor smsInboxCursor = contentResolver.query(Uri.parse("content://sms/inbox"), null, "address='40149'", null, null);
            int indexBody = smsInboxCursor.getColumnIndex("body");
            int indexAddress = smsInboxCursor.getColumnIndex("address");
            int indexDate = smsInboxCursor.getColumnIndex("date");



            if (indexBody < 0 || !smsInboxCursor.moveToFirst())
                return;

            do {
                String str = smsInboxCursor.getString(indexBody);
                String addr = smsInboxCursor.getString(indexAddress);
                String datee = smsInboxCursor.getString(indexDate);
                Long mydate=Long.parseLong(datee);

                DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss.SSS");
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(mydate);
                String mytimestamp=formatter.format(calendar.getTime());

                GetViralCounts gvc=new GetViralCounts();
                String vcounts=Integer.toString(gvc.getViralCount(str));

                Messages ms=new Messages("false",addr,str,mytimestamp,"unread","null",vcounts);
                ms.save();

            } while (smsInboxCursor.moveToNext());
//            Toast.makeText(getActivity(), "length "+counter, Toast.LENGTH_SHORT).show();
        }
        catch(Exception e){

        }


    }



    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        mSearchAction = menu.findItem(R.id.action_search);
        return super.onPrepareOptionsMenu(menu);
    }

   public void closeSearch(boolean x){
       ActionBar action = getSupportActionBar();
       if(x){
           action.setDisplayShowCustomEnabled(false);
           action.setDisplayShowTitleEnabled(true);

       }
       else{

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

    protected void handleMenuSearch(){
        ActionBar action = getSupportActionBar(); //get the actionbar

        if(isSearchOpened){ //test if the search is open

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

            edtSeach = (EditText)action.getCustomView().findViewById(R.id.edtSearch); //the text editor

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

    protected void handleMenuSearch2(){
        ActionBar action = getSupportActionBar(); //get the actionbar

        if(isSearchOpened){ //test if the search is open

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

            edtSeach = (EditText)action.getCustomView().findViewById(R.id.edtSearch); //the text editor

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
        try{

            List myl2=Messages.findWithQuery(Messages.class,"Select * from Messages limit 1");

            for(int x=0;x<myl2.size();x++){

                Messages ms=(Messages) myl2.get(x);
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
        }
        catch(Exception e){
            Toast.makeText(this, "error reading messages "+e, Toast.LENGTH_SHORT).show();

        }

    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
//        Toast.makeText(this, "onresume called", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStop() {
        super.onStop();
//        Toast.makeText(this, "onstop called", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        Toast.makeText(this, "ondestroy called", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStart() {
        super.onStart();
//        Toast.makeText(this, "onstart created", Toast.LENGTH_SHORT).show();
    }


    public void MydialogBuilder(final String message,final String title){
        final boolean[] exiting = {false};
        AlertDialog.Builder b = new AlertDialog.Builder(this);

        b.setMessage(message+"\n");
        b.setTitle(title);
        b.setCancelable(false);

        b.setNegativeButton("CALL", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {


                String PhoneNo = "+254791184100";
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + PhoneNo));
                if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
//                    return ;
                }
                startActivity(intent);



                dialog.cancel();
            }
        });

        b.setNeutralButton("MESSAGE", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                Intent smsIntent = new Intent(Intent.ACTION_VIEW);
                smsIntent.setType("vnd.android-dir/mms-sms");
                smsIntent.putExtra("address", "40148");
                smsIntent.putExtra("sms_body","your message here...");
                startActivity(smsIntent);

            }
        });
        b.setPositiveButton("CANCEL", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                dialog.cancel();

            }
        });

        AlertDialog a=b.create();

        a.show();

        Button bq = a.getButton(DialogInterface.BUTTON_NEGATIVE);
        Button bn = a.getButton(DialogInterface.BUTTON_NEUTRAL);
        Button bP = a.getButton(DialogInterface.BUTTON_POSITIVE);
        bq.setTextColor(Color.GREEN);
        bn.setTextColor(Color.BLUE);
        bP.setTextColor(Color.RED);

    }


    public void sendReadReport(){

        try{

            int read=0;
            int todayReceived=0;
            int todayRead=0;
            String unread="";
            String tstamp="";
            int allmesages=0;
            String dt="";
            String mth="";
            String yr="";

            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
//            System.out.println("testing "+timestamp);
            String x=timestamp.toString();
            String[] spliting=x.split("\\s+");
            System.out.println("testing new "+spliting[0]);
            String mydate=spliting[0];
            String[] splitedMydate=mydate.split("-");
            dt=splitedMydate[2];
            mth=splitedMydate[1];
            yr=splitedMydate[0];


            List<Messages> myl2=Messages.findWithQuery(Messages.class,"Select * from Messages group by m_body",null);
            for(int y=0;y<myl2.size();y++){
                allmesages=myl2.size();
               tstamp=myl2.get(y).getmTimeStamp();

                String[] splitedDate=tstamp.split("/");
                String yeararr=splitedDate[2];//returns the string like 2017 11:10:30.366
                String[] myyeararr=yeararr.split("\\s+");//split the white space to get only the year e.g 2017


                String mymnth=splitedDate[1];
                String myyear=myyeararr[0];
                String myday=splitedDate[0];

                if(mymnth.contentEquals(mth)&& myyear.contentEquals(yr)&& myday.contentEquals(dt)){

                    todayReceived+=1;
                }



            }

            List<Messages> myl3=Messages.findWithQuery(Messages.class,"Select * from Messages where read=? group by m_body", "read");
            for(int y=0;y<myl3.size();y++){
                read=myl3.size();

                tstamp=myl3.get(y).getDateRead();


                String[] spliting2=tstamp.split("\\s+");
                System.out.println("testing new "+spliting2[0]);
                String mydate1=spliting2[0];

                String[] splitedMydate1=mydate1.split("-");
                dt=splitedMydate1[2];
                mth=splitedMydate1[1];
                yr=splitedMydate1[0];



                Timestamp timestampnow = new Timestamp(System.currentTimeMillis());
//            System.out.println("testing "+timestamp);
                String xnow=timestampnow.toString();
                String[] splitingnow=xnow.split("\\s+");
                System.out.println("testing new "+splitingnow[0]);
                String mydatenow=spliting[0];
                String[] splitedMydatenow=mydatenow.split("-");
               String dt1=splitedMydatenow[2];
               String mth1=splitedMydatenow[1];
               String yr1=splitedMydatenow[0];







                if(mth1.contentEquals(mth)&& yr1.contentEquals(yr)&&dt1.contentEquals(dt)){

                    todayRead+=1;
                }

            }
            String messReport="mlab"+"*"+mydate+"*"+todayReceived+"*"+todayRead+"*"+allmesages+"*"+read;
//            RegistrationConf("todayread is: "+todayRead+" today received is "+todayReceived+" total read is "+read+" all messages "+allmesages+" date is "+x,"message test");
            SmsManager sm = SmsManager.getDefault();
            sm.sendTextMessage("40147", null,messReport, null, null);


        }
        catch(Exception e){

        }
    }






    public void RegistrationConf(final String message,final String title){

        AlertDialog.Builder b = new AlertDialog.Builder(this);

        b.setMessage(message+"\n");
        b.setTitle(title);
        b.setCancelable(false);

        b.setNegativeButton("CONTINUE TO LOGIN", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent i = new Intent(getApplicationContext(), Mylogin.class);
                // Closing all the Activities
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
                finish();
                dialog.cancel();
            }
        });

        b.setPositiveButton("EXIT APP", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                dialog.cancel();
//                finish();

            }
        });

        AlertDialog a=b.create();

        a.show();

        Button bq = a.getButton(DialogInterface.BUTTON_NEGATIVE);
        Button bn = a.getButton(DialogInterface.BUTTON_NEUTRAL);
        Button bP = a.getButton(DialogInterface.BUTTON_POSITIVE);
        bq.setTextColor(Color.GREEN);
        bn.setTextColor(Color.BLUE);
        bP.setTextColor(Color.RED);

    }


    public void displayDbVcounts(){

        try{
            System.out.println("viral count***************************************************************************viral count");

            List<Messages> bdy = Messages.findWithQuery(Messages.class, "Select * from Messages group by m_body", null);
            for(int x=0;x<bdy.size();x++){
                String messbdy=bdy.get(x).getmBody();
                String count=bdy.get(x).getViralCount();

                System.out.println(messbdy+"**count****"+count);



            }


            System.out.println("viral count***************************************************************************viral count");


        }

        catch(Exception e){


        }
    }

}

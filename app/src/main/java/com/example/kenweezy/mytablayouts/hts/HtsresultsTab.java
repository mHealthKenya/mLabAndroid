package com.example.kenweezy.mytablayouts.hts;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kenweezy.mytablayouts.AccessServer.AccessServer;
import com.example.kenweezy.mytablayouts.Checkinternet.CheckInternet;
import com.example.kenweezy.mytablayouts.R;
//import com.example.kenweezy.mytablayouts.SSLTrustCertificate.SSLTrust;
import com.example.kenweezy.mytablayouts.UsersTable;
import com.example.kenweezy.mytablayouts.encryption.Base64Encoder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HtsresultsTab extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    ViewPagerAdapter adapter;
    FloatingActionButton fabref;

    CheckInternet chk;
    Base64Encoder encoder;
    AccessServer acs;

    String[] tabTitle = {"Positive", "Negative"};
    int[] Counts = {0,0};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hts_results_tab);

        //SSLTrust.nuke();

        initialise();
        refreshResultsClicked();
        setToolBar();
        changeStatusBarColor("#3F51B5");
        DisplayContent();
        adapter.SetOnSelectView(tabLayout,0);
        tabLayout.addOnTabSelectedListener(OnTabSelectedListener);
    }

    private void initialise(){

        fabref = (FloatingActionButton) findViewById(R.id.fabhtsres);
        chk=new CheckInternet(HtsresultsTab.this);
        encoder=new Base64Encoder();
        acs=new AccessServer(HtsresultsTab.this);
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

                    acs.getHtsResultsFromDb(userPhoneNumber);

                }
                else{

                    Toast.makeText(HtsresultsTab.this, "check your internet connection", Toast.LENGTH_SHORT).show();

//                    sm.sendMessageApi(encoder.encryptString(userPhoneNumber), Config.mainShortcode);
//
//                    listenForIncomingMessage();

                }



//                Toast.makeText(MainActivity.this, "refreshing results", Toast.LENGTH_SHORT).show();
            }
        });
    }





    private void changeStatusBarColor(String color){
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.parseColor(color));
        }
    }


    public void setToolBar(){

        try{

            Toolbar toolbar = (Toolbar) findViewById(R.id.htstabtoolbar);
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle("HTS Results");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);

        }
        catch(Exception e){


        }
    }


    public void pagerListener(){
        try{

            viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                    try{

                    }
                    catch(Exception e){
                        Toast.makeText(HtsresultsTab.this, "error updating fragment "+e, Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onPageSelected(int position) {

                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
        }
        catch(Exception e){


        }
    }


    private void setupTabIcons() {

        for (int i = 0; i < tabTitle.length; i++) {

            tabLayout.getTabAt(i).setCustomView(prepareTabView(i));
        }
    }



    private TabLayout.OnTabSelectedListener OnTabSelectedListener = new TabLayout.OnTabSelectedListener(){
        @Override
        public void onTabSelected(TabLayout.Tab tab) {
            int c = tab.getPosition();
            adapter.SetOnSelectView(tabLayout,c);
        }

        @Override
        public void onTabUnselected(TabLayout.Tab tab) {
            int c = tab.getPosition();
            adapter.SetUnSelectView(tabLayout,c);
        }

        @Override
        public void onTabReselected(TabLayout.Tab tab) {

        }
    };



    private View prepareTabView(int pos) {
        View view = getLayoutInflater().inflate(R.layout.custom_tab, null);
        TextView tv_title = (TextView) view.findViewById(R.id.tv_title);
        TextView tv_count = (TextView) view.findViewById(R.id.tv_count);
        tv_title.setText(tabTitle[pos]);
        if (Counts[pos] > 0) {
            tv_count.setVisibility(View.VISIBLE);
            tv_count.setText("" + Counts[pos]);
        } else
            tv_count.setVisibility(View.GONE);


        return view;
    }


    public void MyViewPager(){

        try{

            viewPager = (ViewPager) findViewById(R.id.htstabviewpager);
            setupViewPager(viewPager);


            tabLayout = (TabLayout) findViewById(R.id.htstabstabs);
            tabLayout.setupWithViewPager(viewPager);
            tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#f2f2f2"));
            tabLayout.setSelectedTabIndicatorHeight(8);
        }
        catch(Exception e){


        }
    }



    private void setupViewPager(ViewPager viewPager) {
        adapter = new ViewPagerAdapter(getSupportFragmentManager());


        adapter.addFragment(new HtsPositiveFragment(), "Positive");
        adapter.addFragment(new HtsNegativeFragment(), "Negative");
//        adapter.addFragment(new HtsNegativeFragment(), "Inconclusive");



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


        public void SetOnSelectView(TabLayout tabLayout,int position)
        {
            TabLayout.Tab tab = tabLayout.getTabAt(position);
            View selected = tab.getCustomView();
            TextView iv_text = (TextView) selected.findViewById(R.id.tv_title);
            iv_text.setTextColor(getResources().getColor(R.color.textcolorblack));
        }


        public void SetUnSelectView(TabLayout tabLayout,int position)
        {
            TabLayout.Tab tab = tabLayout.getTabAt(position);
            View selected = tab.getCustomView();
            TextView iv_text = (TextView) selected.findViewById(R.id.tv_title);
            iv_text.setTextColor(getResources().getColor(R.color.textColorPrimary));
        }


    }


    public void DisplayContent(){

        try{

            MyViewPager();
            pagerListener();
            setupTabIcons();

        }
        catch(Exception e){

            Toast.makeText(this, "error displaying content "+e, Toast.LENGTH_SHORT).show();


        }
    }

}

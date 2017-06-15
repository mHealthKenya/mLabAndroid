package com.example.kenweezy.mytablayouts;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by KENWEEZY on 2017-01-12.
 */

public class FragmentAll1 extends Fragment {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private static FragmentAll1 inst;

    String[] tabTitle={"EID","VL"};
    int[] unreadCount={0,0};

    public FragmentAll1(){

    }

    public static FragmentAll1 instance() {
        return (new FragmentAll1());
    }


    @Override
    public void onStart() {
        super.onStart();
        inst = this;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rtv=inflater.inflate(R.layout.fragmentall,container,false);

        viewPager=(ViewPager) rtv.findViewById(R.id.fragall_viewpager);
        setupViewPager(viewPager);
        tabLayout=(TabLayout) rtv.findViewById(R.id.fragall_tabs);
        tabLayout.setupWithViewPager(viewPager);


        TabsSettings ts=new TabsSettings();
        ts.SetSettings(tabLayout);
        ts.setTabTextColour(tabLayout);
        GetSmsCount gsc=new GetSmsCount(getActivity());
        unreadCount[0]=gsc.getFfeidCount();
        unreadCount[1]=gsc.getVlCount();





//        View myv=tabLayout.getTabAt(1).getCustomView();
//        BadgeView badge = new BadgeView(getActivity(), badget_t);
//        badge.setText("1");
//        badge.show();

        setupTabIcons();
        return rtv;
    }

    private void setupTabIcons() {
//        tabLayout.getTabAt(0).setIcon(R.mipmap.ic_launcher);
//        tabLayout.getTabAt(1).setIcon(R.mipmap.ic_launcher);
//        tabLayout.getTabAt(2).setIcon(R.mipmap.ic_launcher);
//        tabLayout.getTabAt(3).setIcon(R.mipmap.ic_launcher);
        for(int i=0;i<tabTitle.length;i++)
        {
            /*TabLayout.Tab tabitem = tabLayout.newTab();
            tabitem.setCustomView(prepareTabView(i));
            tabLayout.addTab(tabitem);*/

            tabLayout.getTabAt(i).setCustomView(prepareTabView(i));
        }
    }

    private View prepareTabView(int pos) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.custom_tab,null);
        TextView tv_title = (TextView) view.findViewById(R.id.tv_title);
        TextView tv_count = (TextView) view.findViewById(R.id.tv_count);
        tv_title.setText(tabTitle[pos]);
        if(unreadCount[pos]>0)
        {
            tv_count.setVisibility(View.VISIBLE);
            tv_count.setText(""+unreadCount[pos]);
        }
        else
            tv_count.setVisibility(View.GONE);


        return view;
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
          //add the fragments to every tab
        adapter.addFragment(new FragmentAll(), "EID");
        adapter.addFragment(new FragmentAllVl(), "VL");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

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
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);//enables displaying the title
            //return null;//disables the displaying of title on tabs
        }
    }


}

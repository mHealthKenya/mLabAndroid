package com.example.kenweezy.mytablayouts;

import android.graphics.Color;
import android.support.design.widget.TabLayout;

/**
 * Created by KENWEEZY on 2017-01-15.
 */

public class TabsSettings {

    public TabsSettings(){}

    public void SetSettings(TabLayout tl){

        tl.setSelectedTabIndicatorColor(Color.parseColor("#f2f2f2"));
        tl.setSelectedTabIndicatorHeight(3);
        tl.setBackgroundColor(Color.parseColor("#8080ff"));//#8080ff
//        tl.getChildAt(1).setBackgroundColor(Color.parseColor("#ff0000"));



    }

    public void setTabTextColour(TabLayout tl){

        tl.setTabTextColors(Color.parseColor("#f2f2f2"),Color.parseColor("#b3b3b3"));
    }
}

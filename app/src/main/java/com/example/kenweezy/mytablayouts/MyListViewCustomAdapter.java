package com.example.kenweezy.mytablayouts;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

/**
 * Created by KENWEEZY on 2017-03-09.
 */

public class MyListViewCustomAdapter extends ArrayAdapter<String> {


    public MyListViewCustomAdapter(@NonNull Context context,String[] mydata) {
        super(context,R.layout.listviewtestingrows,mydata);
    }

    @Override
    public View getView(int position,View convertView,ViewGroup parent) {
        LayoutInflater myInflater=LayoutInflater.from(getContext());
        View customView=myInflater.inflate(R.layout.listviewtestingrows,parent,false);

//        String singleItem=getItem(position);
//
//        TextView myt=(TextView) customView.findViewById(R.id.lvtv);
//        TextView mytbdy=(TextView) customView.findViewById(R.id.lvtvbdy);
//
//        myt.setText(singleItem);
//        mytbdy.setText("Body");
//        int[] positions={1,2,3};
//        for(int x=0;x<positions.length;x++){
//
////            if(position==positions[x]){
////
////                myt.setTextColor(Color.parseColor("#ff0000"));
////            }
//            if(myt.getText().toString().contentEquals("four")){
//                myt.setTextColor(Color.parseColor("#00ff00"));
//            }
//        }

        return customView;
    }
}

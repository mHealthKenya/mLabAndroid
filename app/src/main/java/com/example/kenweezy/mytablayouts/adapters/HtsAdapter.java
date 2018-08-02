package com.example.kenweezy.mytablayouts.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.kenweezy.mytablayouts.R;
import com.example.kenweezy.mytablayouts.models.Htsmodel;

import java.util.ArrayList;

public class HtsAdapter extends RecyclerView.Adapter<HtsAdapter.MyviewHolder>{

    public ArrayList<Htsmodel> mylist,filterList;
    Context ctx;
    Boolean isLender;


    public HtsAdapter(Context ctx, ArrayList<Htsmodel> mylist, Boolean islender) {
        this.mylist = mylist;
        this.ctx = ctx;
        this.isLender=islender;
        this.filterList=mylist;
    }

    @Override
    public HtsAdapter.MyviewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.hts_row, parent, false);

        return new MyviewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyviewHolder holder, int position) {
        try{

            Htsmodel itm = mylist.get(position);


            holder.name.setText(itm.getClientCode());




        }
        catch(Exception e){


        }



    }


    @Override
    public int getItemCount() {
        return mylist.size();
    }



    public class MyviewHolder extends RecyclerView.ViewHolder{


        public TextView id,name,email,phone,county,location,business,amount,security,feetopay,feepaid,status
                ,date_added;
        public LinearLayout lllend;

        public CardView lcd;


        public MyviewHolder(View itemView) {
            super(itemView);

//            id =(TextView) itemView.findViewById(R.id.borrowings_id);
            name =(TextView) itemView.findViewById(R.id.clientcode);




//
            lcd=(CardView) itemView.findViewById(R.id.hts_card_view);
        }
    }
}



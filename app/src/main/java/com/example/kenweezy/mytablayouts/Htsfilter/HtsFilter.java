package com.example.kenweezy.mytablayouts.Htsfilter;

import android.widget.Filter;

import com.example.kenweezy.mytablayouts.adapters.HtsAdapter;
import com.example.kenweezy.mytablayouts.models.Htsmodel;

import java.util.ArrayList;

public class HtsFilter extends Filter {

    HtsAdapter adapter;
    ArrayList<Htsmodel> filterList;




    public HtsFilter(ArrayList<Htsmodel> filterList, HtsAdapter adapter)
    {
        this.adapter=adapter;
        this.filterList=filterList;



    }

    //FILTERING OCURS
    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        FilterResults results=new FilterResults();

        //CHECK CONSTRAINT VALIDITY
        if(constraint != null && constraint.length() > 0)
        {
            //CHANGE TO UPPER
            constraint=constraint.toString().toUpperCase();
            //STORE OUR FILTERED PLAYERS
            ArrayList<Htsmodel> filteredResults=new ArrayList<>();

            for (int i=0;i<filterList.size();i++)
            {



                //CHECK
                if((filterList.get(i).getClientCode().toUpperCase().contains(constraint)||filterList.get(i).getAge().toUpperCase().contains(constraint)||filterList.get(i).getGender().toUpperCase().contains(constraint)||filterList.get(i).getReleased().toUpperCase().contains(constraint)||filterList.get(i).getResult().toUpperCase().contains(constraint)||filterList.get(i).getSubmitted().toUpperCase().contains(constraint)))
                {
                    //ADD PLAYER TO FILTERED PLAYERS
                    filteredResults.add(filterList.get(i));
                }

            }

            results.count=filteredResults.size();
            results.values=filteredResults;
        }else
        {
            results.count=filterList.size();
            results.values=filterList;

        }


        return results;
    }

    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {
//
//        adapter.mylist= (ArrayList<BorrowingsModel>) results.values;
        adapter.mylist=(ArrayList<Htsmodel>) results.values;
        //REFRESH
        adapter.notifyDataSetChanged();
    }
}

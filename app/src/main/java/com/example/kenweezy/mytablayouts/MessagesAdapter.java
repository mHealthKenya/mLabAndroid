package com.example.kenweezy.mytablayouts;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by KENWEEZY on 2017-03-10.
 */

public class MessagesAdapter extends BaseAdapter implements Filterable{

    private Context mycont;
    private List<Mydata> mylist;
    CustomFilter filter;
    private List<Mydata> filterList;


   public MessagesAdapter(Context cont,List<Mydata> mlist){

       this.mycont=cont;
       this.mylist=mlist;
       this.filterList=mlist;


   }
    @Override
    public int getCount() {
        return mylist.size();
    }

    @Override
    public Object getItem(int position) {
        return mylist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = View.inflate(mycont, R.layout.listviewtestingrows, null);

        try {
            String[] months = {"Jan", "Feb", "Mar", "Apr", "May", "June", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};

            TextView tvtitle = (TextView) v.findViewById(R.id.mstitle);
            TextView tvbdy = (TextView) v.findViewById(R.id.msgbdy);
            TextView tvdate = (TextView) v.findViewById(R.id.msgdte);
            ImageView imgv = (ImageView) v.findViewById(R.id.imgv);

            String mybdyString=mylist.get(position).getMsgbody();
//            String[] splitBdy=mybdyString.split("Result",5);
            String subBodyString="";

            if(mybdyString.contains("Age")){
                int lastIndex=mybdyString.indexOf("Age",25);
                subBodyString=mybdyString.substring(0,lastIndex);

            }
            else{
                int lastIndex=mybdyString.indexOf("Result",20);
                subBodyString=mybdyString.substring(0,lastIndex);

            }


//            System.out.println("/*******the split string is******/");
//            for(int d=0;d<splitBdy.length;d++){
//                System.out.println(splitBdy[d]);
//            }


            tvtitle.setText(mylist.get(position).getRead());
//            tvbdy.setText(mylist.get(position).getMsgbody());
            tvbdy.setText(subBodyString+" .........");
            String mydate = mylist.get(position).getDate();

            System.out.println("the date is " + mydate);
            String[] splitDate = mydate.split("/");
            String splitedDate = splitDate[1];
            String mysplitday = splitDate[0];
            String myNewDate = "";
            switch (splitedDate) {

                case "01":
                    myNewDate = months[0];
                    break;
                case "02":
                    myNewDate = months[1];
                    break;
                case "03":
                    myNewDate = months[2];
                    break;
                case "04":
                    myNewDate = months[3];
                    break;
                case "05":
                    myNewDate = months[4];
                    break;
                case "06":
                    myNewDate = months[5];
                    break;
                case "07":
                    myNewDate = months[6];
                    break;
                case "08":
                    myNewDate = months[7];
                    break;
                case "09":
                    myNewDate = months[8];
                    break;
                case "10":
                    myNewDate = months[9];
                    break;
                case "11":
                    myNewDate = months[10];
                    break;
                case "12":
                    myNewDate = months[11];
                    break;
                default:


            }

            tvdate.setText(mysplitday + " " + myNewDate);

            v.setTag(mylist);

            if (tvtitle.getText().toString().contentEquals("read")) {

                tvbdy.setTextColor(Color.parseColor("#696969"));
//            tvbdy.setText(Html.fromHtml("<h1><b>"+mylist.get(position).getMsgbody()+"</b></h1>"));
                imgv.setVisibility(View.GONE);

            }

        }
        catch(Exception e){


        }

        return v;

    }

    @Override
    public Filter getFilter() {

        if(filter==null){

            filter=new CustomFilter();

        }
        return filter;
    }

    class CustomFilter extends Filter{


        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            FilterResults results=new FilterResults();
            if(constraint!=null && constraint.length()>0){

                constraint=constraint.toString().toUpperCase();
                ArrayList<Mydata> filters=new ArrayList<Mydata>();

                for(int i=0;i<filterList.size();i++){

                    if(filterList.get(i).getMsgbody().toUpperCase().contains(constraint)){

                        Mydata md=new Mydata(filterList.get(i).getMsgbody(),filterList.get(i).getDate(),filterList.get(i).getRead());
                        filters.add(md);
                    }
                }

                results.count=filters.size();
                results.values=filters;

            }

            else{

                results.count=filterList.size();
                results.values=filterList;

            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

            mylist= (List<Mydata>) results.values;
            notifyDataSetChanged();

        }
    }
}

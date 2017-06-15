package com.example.kenweezy.mytablayouts;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by KENWEEZY on 2017-03-09.
 */

public class ListviewTesting extends AppCompatActivity {

    private ListView lv;
    private MessagesAdapter myadapter;
    private List<Mydata> mymesslist;
    int counter=0;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listviewtesting);
        mymesslist=new ArrayList<>();
        List<Messages> bdy = Messages.findWithQuery(Messages.class, "Select * from Messages", null);

        if (bdy.isEmpty())
            return;
//        myadapter.clear();


        for(int x=0;x<bdy.size();x++){

            counter += 1;
            String messbdy=bdy.get(x).getmBody();
            String ndate = bdy.get(x).getmTimeStamp();
            String read=bdy.get(x).getRead();

            mymesslist.add(new Mydata(messbdy,ndate,read));


        }
        myadapter=new MessagesAdapter(getApplicationContext(),mymesslist);

        lv=(ListView) findViewById(R.id.mylist);

        lv.setAdapter(myadapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                TextView tvread=(TextView) view.findViewById(R.id.mstitle);
                tvread.setText("read");

                try{

                    String msgbdy=mymesslist.get(position).getMsgbody();

//                    Toast.makeText(getActivity(), ""+date, Toast.LENGTH_SHORT).show();

                    System.out.println("/*****///// "+msgbdy);
                    List myl=Messages.findWithQuery(Messages.class,"Select * from Messages where m_body=?",msgbdy);
                    for(int x=0;x<myl.size();x++){

                        Messages ms=(Messages) myl.get(x);
                        ms.getId();
                        ms.setRead("read");
//                    Toast.makeText(getActivity(), "id: "+ms.getId(), Toast.LENGTH_SHORT).show();
                        ms.save();
                    }


                   mymesslist.clear();
                    List<Messages> bdy = Messages.findWithQuery(Messages.class, "Select * from Messages", null);

                    if (bdy.isEmpty())
                        return;
//        myadapter.clear();


                    for(int x=0;x<bdy.size();x++){

                        counter += 1;
                        String messbdy=bdy.get(x).getmBody();
                        String ndate = bdy.get(x).getmTimeStamp();
                        String read=bdy.get(x).getRead();

                        mymesslist.add(new Mydata(messbdy,ndate,read));


                    }

                    myadapter.notifyDataSetChanged();



                }

                catch(Exception e){}


            }
        });

    }


    public void refreshMessages(){

        try{

            mymesslist=new ArrayList<>();
            List<Messages> bdy = Messages.findWithQuery(Messages.class, "Select * from Messages", null);

            if (bdy.isEmpty())
                return;
//        myadapter.clear();


            for(int x=0;x<bdy.size();x++){

                counter += 1;
                String messbdy=bdy.get(x).getmBody();
                String ndate = bdy.get(x).getmTimeStamp();
                String read=bdy.get(x).getRead();

                mymesslist.add(new Mydata(messbdy,ndate,read));
                System.out.println(messbdy+" /***/ "+read);


            }
            myadapter.notifyDataSetChanged();

        }
        catch(Exception e){


        }
    }
}

package com.example.kenweezy.mytablayouts.AddClient;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.kenweezy.mytablayouts.Loadmessages.LoadMessages;
import com.example.kenweezy.mytablayouts.Messages;
import com.example.kenweezy.mytablayouts.MessagesAdapter;
import com.example.kenweezy.mytablayouts.Mydata;
import com.example.kenweezy.mytablayouts.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

public class Individualresults extends AppCompatActivity {

    private Toolbar toolbar;
    EditText mflcode,puid,frmweek,toweek;
    String mflcodeS,puidS,frmweekS,toweekS;
    ListView lv;

    private MessagesAdapter myadapter;
    private List<Mydata> mymesslist;
    ArrayAdapter arrayAdapter;
    ArrayList<String> smsMessagesList = new ArrayList<String>();

    LoadMessages lm;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.client_individual_results);
        setToolbar();
        initialise();
    }

    public void initialise(){

        try{
            lm=new LoadMessages(Individualresults.this);
            mflcode=(EditText) findViewById(R.id.client_mflcode);
            puid=(EditText) findViewById(R.id.client_puid);
            frmweek=(EditText) findViewById(R.id.client_frmweek);
            toweek=(EditText) findViewById(R.id.client_toweek);

            lv=(ListView) findViewById(R.id.lvclientindividualresults);

            mflcodeS="";
            puidS="";
            frmweekS="";
            toweekS="";

        }
        catch(Exception e){


        }
    }

    public void setToolbar(){

        try{

            toolbar = (Toolbar) findViewById(R.id.toolbarclientindividualresults);
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle("Client Results");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
        }
        catch(Exception e){


        }
    }


    public void SearchIndividualResults(View v){

        try{
//            mflcodeS,puidS,frmweekS,toweekS;
            mflcodeS=mflcode.getText().toString();
            puidS=puid.getText().toString();
            frmweekS=frmweek.getText().toString();
            toweekS=toweek.getText().toString();

            if(mflcodeS.trim().isEmpty()){
                Toast.makeText(this, "mfl code is required", Toast.LENGTH_SHORT).show();
            }
            else if(puidS.trim().isEmpty()){
                Toast.makeText(this, "puid is required", Toast.LENGTH_SHORT).show();
            }
//            else if(frmweekS.trim().isEmpty()){
//                Toast.makeText(this, "from date is required", Toast.LENGTH_SHORT).show();
//            }
//            else if(toweekS.trim().isEmpty()){
//                Toast.makeText(this, "to date is required", Toast.LENGTH_SHORT).show();
//            }
            else{

                Toast.makeText(this, "searching", Toast.LENGTH_SHORT).show();
                getResults();
            }

        }
        catch(Exception e){


        }
    }



    public void getResults(){

        try{

            mymesslist=new ArrayList<>();
            List<Messages> bdy = Messages.findWithQuery(Messages.class, "Select * from Messages group by m_body", null);
            if(bdy.size()<0){

                lm.getMessages();

            }

            for(int x=0;x<bdy.size();x++){


                String messbdy=bdy.get(x).getmBody();
                String messId=bdy.get(x).getMessageId();
                String ndate = bdy.get(x).getmTimeStamp();
                String read=bdy.get(x).getRead();
                String mychk=bdy.get(x).getChkd();
                String mvcnt=bdy.get(x).getViralCount();
                int vcount=Integer.parseInt(mvcnt);
                boolean mychkB;
                if(mychk.contentEquals("true")){
                    mychkB=true;
                }
                else{
                    mychkB=false;
                }

                String[] checkSplitdate=ndate.split("/");


                if(checkSplitdate.length>1){

                }
                else{
                    DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss.SSS");
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTimeInMillis(Long.parseLong(ndate));
                    ndate = formatter.format(calendar.getTime());

                }



                mymesslist.add(new Mydata(mychkB,messbdy,ndate,read,vcount,messId));


            }
            Collections.sort(mymesslist,Mydata.VlcountComparator);

            myadapter=new MessagesAdapter(Individualresults.this,mymesslist);
            lv.setAdapter(myadapter);
        }
        catch(Exception e){


        }
    }
}

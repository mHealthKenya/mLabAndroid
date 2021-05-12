package com.example.kenweezy.mytablayouts.AddClient;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kenweezy.mytablayouts.Config.Config;
import com.example.kenweezy.mytablayouts.DateTimePicker.DateTimePicker;
import com.example.kenweezy.mytablayouts.Loadmessages.LoadMessages;
import com.example.kenweezy.mytablayouts.Messages;
import com.example.kenweezy.mytablayouts.MessagesAdapter;
import com.example.kenweezy.mytablayouts.Mydata;
import com.example.kenweezy.mytablayouts.Myshortcodes;
import com.example.kenweezy.mytablayouts.R;
import com.example.kenweezy.mytablayouts.encryption.Base64Encoder;
import com.example.kenweezy.mytablayouts.messagedialog.MessageDialog;
import com.example.kenweezy.mytablayouts.sendmessages.SendMessage;

import java.sql.Timestamp;
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
    DateTimePicker dtp;
    SendMessage sm;

    MessageDialog mdialog;
    Base64Encoder encoder;

    Myshortcodes msc=new Myshortcodes();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.client_individual_results);
        setToolbar();
        initialise();

        setDateFromPicker();
        setDateToPicker();

        changeStatusBarColor();
        setClickListener();
    }

    private void changeStatusBarColor(){
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.parseColor(Config.statusBarColor));
        }
    }

    public void setDateFromPicker(){

        try{
            frmweek.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    dtp.setDatePicker(frmweek);

                }
            });


        }
        catch(Exception e){


        }
    }

    public void setDateToPicker(){

        try{

            toweek.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    dtp.setDatePicker(toweek);

                }
            });


        }
        catch(Exception e){


        }
    }

    public void initialise(){

        try{

            encoder=new Base64Encoder();
            sm=new SendMessage(Individualresults.this);
            mdialog=new MessageDialog(Individualresults.this);
            dtp=new DateTimePicker(Individualresults.this);
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
            else if(frmweekS.trim().isEmpty()){
                Toast.makeText(this, "from date is required", Toast.LENGTH_SHORT).show();
            }
            else if(toweekS.trim().isEmpty()){
                Toast.makeText(this, "to date is required", Toast.LENGTH_SHORT).show();
            }
            else{

                Toast.makeText(this, "searching", Toast.LENGTH_SHORT).show();
                getResults(puidS);
            }

        }
        catch(Exception e){


        }
    }



    public void getResults(String patientId){

        try{

            mymesslist=new ArrayList<>();
            List<Messages> bdy = Messages.findWithQuery(Messages.class, "Select * from Messages where patientid=? group by m_body", patientId);
            if(bdy.size()<0){

//                lm.getMessages();

            }
            else{

                //process available results


                for(int x=0;x<bdy.size();x++){


                    String messbdy=bdy.get(x).getmBody();
                    String messId=bdy.get(x).getMessageId();
                    String ndate = bdy.get(x).getmTimeStamp();
                    String read=bdy.get(x).getRead();
                    String mychk=bdy.get(x).getChkd();
                    String mvcnt=bdy.get(x).getViralCount();
                    int vcount=Integer.parseInt(mvcnt);
                    String patientid= bdy.get(x).getPatientid();

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


                //process available results


            }




        }
        catch(Exception e){


        }
    }






    public void setClickListener(){

        try{



            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                    TextView tvread=(TextView) view.findViewById(R.id.mstitle);
                    tvread.setText("read");
                    boolean sending=false;
                    boolean txtChkd;

                    try{

                        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
//            System.out.println("testing "+timestamp);
                        String mytime=timestamp.toString();

                        String msgbdy=mymesslist.get(position).getMsgbody();
                        String msgId=mymesslist.get(position).getMsgId();
                        String msgdate=mymesslist.get(position).getDate();

//                    Toast.makeText(getActivity(), ""+date, Toast.LENGTH_SHORT).show();


//                        MydialogBuilder(msgbdy,msgdate);
                        mdialog.displayMessage(msgbdy,msgdate);

//                    System.out.println("/*****///// "+msgbdy);
                        List myl=Messages.findWithQuery(Messages.class,"Select * from Messages where m_body=?",msgbdy);
                        for(int x=0;x<myl.size();x++){

                            Messages ms=(Messages) myl.get(x);
                            if(ms.getRead().contentEquals("read")){
                                sending=false;

                            }
                            else{

                                sending=true;
                                ms.getId();
                                ms.setRead("read");
                                ms.setDateRead(mytime);
//                    Toast.makeText(getActivity(), "id: "+ms.getId(), Toast.LENGTH_SHORT).show();
                                ms.save();
                            }
                        }


                        if(sending){

                            String sendMessage="read*"+msgId;

                            sm.sendMessageApi(encoder.encryptString(sendMessage),msc.sendSmsShortcode);

                        }





                        mymesslist.clear();
                        List<Messages> bdy = Messages.findWithQuery(Messages.class, "Select * from Messages group by m_body", null);

                        if (bdy.isEmpty())
                            return;
//        myadapter.clear();



                        Collections.sort(mymesslist,Mydata.VlcountComparator);

                        Mydata model = mymesslist.get(position);

                        mymesslist.set(position, model);

                        myadapter.notifyDataSetChanged();



                    }

                    catch(Exception e){}




                }
            });



        }
        catch(Exception e){


        }
    }











}

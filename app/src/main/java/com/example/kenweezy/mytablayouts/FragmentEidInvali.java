package com.example.kenweezy.mytablayouts;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by KENWEEZY on 2017-01-12.
 */

public class FragmentEidInvali extends Fragment {

    View v;


    private static FragmentEidInvali inst;
    ArrayList<String> smsMessagesList = new ArrayList<String>();
    ListView smsListView;
    ArrayAdapter arrayAdapter;
    int counter=0;
    String smsMessage = "";
    public static FragmentEidInvali instance() {
        return (new FragmentEidInvali());
    }

    @Override
    public void onStart() {
        super.onStart();
        inst = this;
    }

    ArrayAdapter<String> myadapter;
    ListView lv;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v=inflater.inflate(R.layout.fragmenteidsuppressed, container, false);
        lv=(ListView) v.findViewById(R.id.lveidsuppressed);
        myadapter=new ArrayAdapter<String>(getActivity(),R.layout.listview_row,R.id.mytext,smsMessagesList);
        lv.setAdapter(myadapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                try {

                    final String[] smsMessages = smsMessagesList.get(position).split("\n");
                    final String address = smsMessages[0];

                    new AlertDialog.Builder(view.getContext())

                            // .setNeutralButton("Share",null)
                            // .setPositiveButton("Print",null)
                            .setNeutralButton("Print", new DialogInterface.OnClickListener()

                            {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent sendIntent = new Intent();
                                    sendIntent.setAction(Intent.ACTION_SEND);
                                    sendIntent.putExtra(Intent.EXTRA_TEXT, address);
                                    sendIntent.setType("text/plain");
                                    startActivity(Intent.createChooser(sendIntent, getResources().getText(R.string.share)));


                                }
                            })
                            .setMessage(address)
                            .setNegativeButton("Close", null).show();






                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


//        MyBottomNav();


        refreshSmsInbox();






        return v;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


//        setHasOptionsMenu(true);
    }







//    @Override
//    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        menu.clear();
//        inflater.inflate(R.menu.fragmenu,menu);
//        super.onCreateOptionsMenu(menu, inflater);
//    }
//
//    public void refreshSmsInbox() {
//        try {
//            ContentResolver contentResolver = getActivity().getContentResolver();
//            Cursor smsInboxCursor = contentResolver.query(Uri.parse("content://sms/inbox"), null, "address='40149'", null, null);
//            int indexBody = smsInboxCursor.getColumnIndex("body");
//            int indexAddress = smsInboxCursor.getColumnIndex("address");
//
//            if (indexBody < 0 || !smsInboxCursor.moveToFirst())
//                return;
//            myadapter.clear();
//
//
//            do {
//                String str = smsInboxCursor.getString(indexBody);
//                String mystrbdy = smsInboxCursor.getString(indexBody);
//                String stw = new String(mystrbdy);
//                String msgaddr = smsInboxCursor.getString(indexAddress);
//
//                if (stw.contains("FFEID") && stw.contains("Negative")) {
//                    counter += 1;
//
//                    myadapter.add(mystrbdy);
//
//                }
//
//
//            } while (smsInboxCursor.moveToNext());
//        }
//        catch(Exception e){
//
//        }
//
//
//    }
//

    public void refreshSmsInbox() {
        try {
//            ContentResolver contentResolver = getActivity().getContentResolver();
//            Cursor smsInboxCursor = contentResolver.query(Uri.parse("content://sms/inbox"), null, "address='40149'", null, null);
//            int indexBody = smsInboxCursor.getColumnIndex("body");
//            int indexAddress = smsInboxCursor.getColumnIndex("address");
            List<Messages> bdy = Messages.findWithQuery(Messages.class, "Select * from Messages where m_body like'%FFEID%Invalid%'", null);

            if (bdy.isEmpty())
                return;
            myadapter.clear();


            for(int x=0;x<bdy.size();x++){

                counter += 1;

                myadapter.add(bdy.get(x).getmBody());


            }

        }
        catch(Exception e){

        }


    }

    public void updateList(final String smsMessage) {
        try {
            myadapter.insert(smsMessage, 0);
            myadapter.notifyDataSetChanged();
        }
        catch(Exception e){

        }
    }

    public void doSearching(CharSequence s){
        //refreshSmsInbox();
        myadapter.getFilter().filter(s.toString());



    }
}

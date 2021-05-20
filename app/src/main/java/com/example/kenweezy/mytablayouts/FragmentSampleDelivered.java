package com.example.kenweezy.mytablayouts;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.kenweezy.mytablayouts.eidvl.remotelogin.remoteOptions.ButtonOptions.CheckRejectedSamples;
import com.example.kenweezy.mytablayouts.eidvl.remotelogin.remoteOptions.ButtonOptions.EidSamples;
import com.google.android.material.snackbar.Snackbar;

/**
 * Created by KenMusembi
 * throughout many a day in may 2021
 * this fragment holds the logic for showing sample delivery status.
 */
public class FragmentSampleDelivered extends Fragment {

    private Button checkRejectedSamplesBtn;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FragmentSampleDelivered() {
        // Required empty public constructor
        super(R.layout.fragment_sample_delivered);
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment fragmentSampleDelivered.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentSampleDelivered newInstance(String param1, String param2) {
        FragmentSampleDelivered fragment = new FragmentSampleDelivered();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sample_delivered, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //we simply change the screen on button click
        checkRejectedSamplesBtn = (Button)view.findViewById(R.id.checkRejectedBtn);

        checkRejectedSamplesBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {

                Intent myint=new Intent(((Activity) getContext()).getApplicationContext(), CheckRejectedSamples.class);

                startActivity(myint);
            }
        });
    }
}
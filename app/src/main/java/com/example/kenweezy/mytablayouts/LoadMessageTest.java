package com.example.kenweezy.mytablayouts;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.kenweezy.mytablayouts.Loadmessages.LoadMessages;
import com.facebook.stetho.Stetho;

public class LoadMessageTest extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loadmessgetest);

        LoadMessages lm;
        lm=new LoadMessages(LoadMessageTest.this);
        lm.getMessages();
        Stetho.initializeWithDefaults(LoadMessageTest.this);
    }
}

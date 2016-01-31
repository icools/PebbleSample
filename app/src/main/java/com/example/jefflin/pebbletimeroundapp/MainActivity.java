package com.example.jefflin.pebbletimeroundapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.getpebble.android.kit.PebbleKit;
import com.getpebble.android.kit.util.PebbleDictionary;

import java.util.UUID;

public class MainActivity extends BasePebbleActivity {

    TextView mTextPebbleTitle ;
    TextView mTextPebbleDescrption ;
    TextView mTextIsMessageSupported ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViews();
        setListeners();
        sendMessage();
        updatePebbleStatus();
    }

    private void updatePebbleStatus() {
        boolean bConnected = isPebbleConnected();
        boolean bSupportedMessage = isSupportMessage() ;
        mTextPebbleTitle.setText("Pebble now is connected:" + bConnected); ;
        mTextIsMessageSupported.setText("Is supported message:" + bSupportedMessage);
    }

    private void setListeners() {

    }

    void findViews(){
        mTextPebbleTitle = (TextView) findViewById(R.id.textTitle);
        mTextPebbleDescrption = (TextView) findViewById(R.id.textViewDescrption);
        mTextIsMessageSupported = (TextView) findViewById(R.id.textMessageSupported);
    }
}
//https://developer.getpebble.com/tutorials/android-tutorial/part2/
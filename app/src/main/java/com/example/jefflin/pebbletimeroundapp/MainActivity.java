package com.example.jefflin.pebbletimeroundapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.getpebble.android.kit.PebbleKit;
import com.getpebble.android.kit.util.PebbleDictionary;

import java.util.UUID;

public class MainActivity extends BasePebbleActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        isPebbleConnected();
        registerPebble();

        if(isPebbleConnected()){
            Log.i(TAG_PEBBLE, "Pebble is connected");
        }

        if(isSupportMessage()){
            Log.i(TAG_PEBBLE, "Pebble is support message!");
        }

        sendMessage();

    }
}

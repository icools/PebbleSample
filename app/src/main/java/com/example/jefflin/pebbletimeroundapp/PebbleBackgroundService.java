package com.example.jefflin.pebbletimeroundapp;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.getpebble.android.kit.PebbleKit;
import com.getpebble.android.kit.util.PebbleDictionary;

import java.util.UUID;

public class PebbleBackgroundService extends Service {
    private static final String TAG_PEBBLE = "Pebble" ;

    final static UUID PEBBLE_APP_UUID = UUID.fromString("3844571e-f6cc-4670-b573-5d97813330bf");

    @Override
    public void onCreate() {
        super.onCreate();

//        registerErrorHandle();
//        registerPebble();
//        reciverDataLogging();
//        receiverData(getApplicationContext());
    }

    void reciverDataLogging(){
        PebbleKit.PebbleDataLogReceiver dataloggingReceiver = new PebbleKit.PebbleDataLogReceiver(PEBBLE_APP_UUID) {

            @Override
            public void receiveData(Context context, UUID logUuid, Long timestamp, Long tag, int data) {
                // Check this is my app log
                //if(tag.intValue() == DATA_LOG_MYAPPTAG)
                {
                    // Get the compass value
                    int heading = data;
                    Log.i(TAG_PEBBLE,"Heading:" + heading);
                }
            }

            @Override
            public void onFinishSession(Context context, UUID logUuid, Long timestamp, Long tag) {
                super.onFinishSession(context, logUuid, timestamp, tag);
            }

        };
        // Register DataLogging Receiver
        PebbleKit.registerDataLogReceiver(this, dataloggingReceiver);

    }

    void registerErrorHandle(){
        PebbleKit.registerReceivedAckHandler(getApplicationContext(), new PebbleKit.PebbleAckReceiver(PEBBLE_APP_UUID) {

            @Override
            public void receiveAck(Context context, int transactionId) {
                Log.i(TAG_PEBBLE, "Received ack for transaction " + transactionId);
            }

        });

        PebbleKit.registerReceivedNackHandler(getApplicationContext(), new PebbleKit.PebbleNackReceiver(PEBBLE_APP_UUID) {

            @Override
            public void receiveNack(Context context, int transactionId) {
                Log.i(TAG_PEBBLE, "Received nack for transaction " + transactionId);
            }

        });

    }

    void receiverData(Context context){
        PebbleKit.registerReceivedDataHandler(this, new PebbleKit.PebbleDataReceiver(PEBBLE_APP_UUID) {

            @Override
            public void receiveData(final Context context, final int transactionId, final PebbleDictionary data) {
                Log.i(TAG_PEBBLE, "Received value=" + data.getInteger(0).intValue() + " for key: 0");
                Intent intent = new Intent("NaviKingAction");
                intent.putExtra("event","1");
                context.sendBroadcast(intent);
                PebbleKit.sendAckToPebble(getApplicationContext(), transactionId);
            }

        });

    }

    void sendMessage() {
        PebbleDictionary data = new PebbleDictionary();

        // Add a key of 0, and a uint8_t (byte) of value 42.
        data.addUint8(0, (byte) 42);

        // Add a key of 1, and a string value.
        data.addString(1, "A string");

        PebbleKit.sendDataToPebble(getApplicationContext(), PEBBLE_APP_UUID, data);
        PebbleKit.sendDataToPebbleWithTransactionId(getApplicationContext(), PEBBLE_APP_UUID, data, 42);
    }

    boolean isPebbleConnected(){
        return PebbleKit.isWatchConnected(getApplicationContext());
    }

    boolean isSupportMessage(){
        return (PebbleKit.areAppMessagesSupported(getApplicationContext()));
    }

    void registerPebble(){
        PebbleKit.registerPebbleConnectedReceiver(getApplicationContext(), new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {
                Log.i(TAG_PEBBLE, "Pebble connected!");
                startPebbleApp();
            }
        });

        PebbleKit.registerPebbleDisconnectedReceiver(getApplicationContext(), new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {
                Log.i(TAG_PEBBLE, "Pebble disconnected!");
            }
        });
    }

    void startPebbleApp(){
        PebbleKit.startAppOnPebble(getApplicationContext(), PEBBLE_APP_UUID);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}

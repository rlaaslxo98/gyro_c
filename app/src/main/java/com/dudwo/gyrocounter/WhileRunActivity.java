package com.dudwo.gyrocounter;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class WhileRunActivity extends AppCompatActivity {

    private int action = 0;
    private int min = 10;
    private int max = 90;
    private boolean minFlag = false;
    private int count = 0;
    private TextView sensorview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_while_run);
        sensorview = (TextView) findViewById(R.id.count);

        LocalBroadcastManager.getInstance(this).registerReceiver(
                connectionUpdates, new IntentFilter("BLUETOOTH"));

        SensorThread.setDaemon(true);
        SensorThread.start();
    }

    BroadcastReceiver connectionUpdates = new BroadcastReceiver() {
        @Override
        public void onReceive(Context arg0, Intent intent) {
            action = intent.getIntExtra("value", 1);
            //TODO here
            //Log.d("WHILERUN", String.valueOf(action));
            if(action<min){
                minFlag = true;
            }
            if(minFlag==true && action>max){
                count++;
                minFlag = false;
                Log.d("COUNT", String.valueOf(count));
            }


        }
    };

    Thread SensorThread = new Thread(new Runnable() {
        @Override
        public void run() {
            while (true) {
                try {
                    SensorThread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //sensorview.setText(String.valueOf(action));
                        sensorview.setText(String.valueOf(count));
                    }
                });
            }
        }
    });
}

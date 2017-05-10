package com.dudwo.gyrocounter;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MeasureValue extends BaseActivity {
    private int action = 0;
    private TextView sensorview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_measure_value);
        sensorview = (TextView) findViewById(R.id.measure);

        LocalBroadcastManager.getInstance(this).registerReceiver(
                connectionUpdates, new IntentFilter("BLUETOOTH"));

        SensorThread.setDaemon(true);
        SensorThread.start();
    }


    BroadcastReceiver connectionUpdates = new BroadcastReceiver() {
        @Override
        public void onReceive(Context arg0, Intent intent) {
            action = intent.getIntExtra("value", 1);
            //Log.d("WHILERUN", String.valueOf(action));

            //TODO here
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
                        sensorview.setText(String.valueOf(action));
                    }
                });
            }
        }
    });
}

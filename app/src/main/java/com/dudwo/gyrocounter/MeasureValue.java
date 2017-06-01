package com.dudwo.gyrocounter;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MeasureValue extends BaseActivity implements View.OnClickListener{
    private int action = 0;
    private EditText sensorview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_measure_value);
        sensorview = (EditText) findViewById(R.id.edit_deg);
        findViewById(R.id.gotomain_measure).setOnClickListener(this);

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

    @Override
    public void onClick(View v) {
        int i = v.getId();

        if(i==R.id.gotomain_measure){
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
}

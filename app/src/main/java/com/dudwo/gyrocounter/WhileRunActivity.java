package com.dudwo.gyrocounter;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class WhileRunActivity extends AppCompatActivity {

    private int action = 0;
    private int min;
    private int max;
    private String work;
    private String type;
    private boolean minFlag = false;
    private int count = 0;
    private int time;
    private int org_time;
    private TextView sensorview;
    private EditText timerview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_while_run);
        Intent intent = getIntent();

        sensorview = (TextView) findViewById(R.id.count);
        timerview = (EditText) findViewById(R.id.editText9);
        time = intent.getExtras().getInt("time");
        org_time = time;
        max = intent.getExtras().getInt("max");
        min = intent.getExtras().getInt("min");
        work = (String)intent.getExtras().get("work");
        type = (String)intent.getExtras().get("type");
        Log.d("WHILE", String.valueOf(max)+"~"+String.valueOf(min));


        LocalBroadcastManager.getInstance(this).registerReceiver(
                connectionUpdates, new IntentFilter("BLUETOOTH"));

        SensorThread.setDaemon(true);
        TImerThread.setDaemon(true);
        SensorThread.start();
        TImerThread.start();
/*
        Handler mHandler = new Handler();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                Intent i = new Intent(WhileRunActivity.this, FinishRunActivity.class); // xxx가 현재 activity, yyy가 이동할 activity
                startActivity(i);
                finish();
            }
        }, time*100); // 1000ms
*/
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
            if(minFlag==true && action>max && action < 180 ){
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
                        sensorview.setText(String.valueOf(count));
                    }
                });
            }
        }
    });

    Thread TImerThread = new Thread(new Runnable() {

        @Override
        public void run() {
            while (true) {
                try {
                    TImerThread.sleep(1000);
                    time--;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        timerview.setText(String.valueOf(time));
                        handler.sendEmptyMessage(time);
                    }
                });
            }
        }
    });

    final Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg){
            if(msg.what==0){
                Log.d("What Number : ", "What is 1");
                Intent newintent = new Intent(WhileRunActivity.this,FinishRunActivity.class);
                newintent.putExtra("time", org_time);
                newintent.putExtra("count", count);
                newintent.putExtra("max", max);
                newintent.putExtra("min", min);
                newintent.putExtra("work",work);
                newintent.putExtra("type",type);
                startActivity(newintent);
                finish();
            }
        }
    };

}

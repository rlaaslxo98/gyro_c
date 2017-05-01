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
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;


public class RunActivity extends AppCompatActivity implements View.OnClickListener {
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_run);

        findViewById(R.id.start).setOnClickListener(this);

        button  = (Button) findViewById(R.id.popup);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                startActivity(new Intent(RunActivity.this, SetRunActivity.class));
            }
        });


        LocalBroadcastManager.getInstance(this).registerReceiver(
                connectionUpdates , new IntentFilter("BLUETOOTH"));
    }


    BroadcastReceiver connectionUpdates = new BroadcastReceiver() {
        @Override
        public void onReceive(Context arg0, Intent intent) {
            int action = intent.getIntExtra("value", 1);
            Log.d("RUN", String.valueOf(action));

                     //TODO here
        }
    };

    public void onClick(View v) {
        int i = v.getId();

        if (i == R.id.start) {
            Intent intent = new Intent(this, CountRunActivity.class);
            startActivity(intent);
            finish();
        }
    }




}
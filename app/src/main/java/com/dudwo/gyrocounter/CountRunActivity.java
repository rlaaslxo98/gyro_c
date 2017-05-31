package com.dudwo.gyrocounter;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ViewFlipper;

public class CountRunActivity extends AppCompatActivity implements View.OnClickListener {

    ViewFlipper viewFlipper;
    private int time;
    private int min;
    private int max;
    private String work;
    private String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_count_run);
        Intent intent = getIntent();
        time = intent.getExtras().getInt("time");
        max = intent.getExtras().getInt("max");
        min = intent.getExtras().getInt("min");
        work = (String)intent.getExtras().get("work");
        type = (String)intent.getExtras().get("type");

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent newintent = new Intent(CountRunActivity.this,WhileRunActivity.class);
                newintent.putExtra("time",time);
                newintent.putExtra("work", work);
                newintent.putExtra("type", type);
                newintent.putExtra("max", max);
                newintent.putExtra("min", min);
                startActivity(newintent);

                finish();
            }
        }, 3000);

        viewFlipper = (ViewFlipper) this.findViewById(R.id.viewFlipper);
        viewFlipper.setOnClickListener(this);
        viewFlipper.startFlipping();
        viewFlipper.setFlipInterval(1000);
    }

    @Override
    public void onClick(View v) {
    }
}

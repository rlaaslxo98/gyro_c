package com.dudwo.gyrocounter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FinishRunActivity extends AppCompatActivity implements View.OnClickListener {

    private int count;
    private int time;
    private int min;
    private int max;
    private String work;
    private String type;
    private EditText finish_count;
    private Result result;

    private FirebaseAuth firebaseAuth;
    FirebaseUser user;

    FirebaseDatabase db;
    DatabaseReference dbref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish_run);
        findViewById(R.id.gotomain).setOnClickListener(this);

        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();

        Intent intent = getIntent();

        time = intent.getExtras().getInt("time");
        count = intent.getExtras().getInt("count");
        time = intent.getExtras().getInt("time");
        max = intent.getExtras().getInt("max");
        min = intent.getExtras().getInt("min");
        work = (String)intent.getExtras().get("work");
        type = (String)intent.getExtras().get("type");

        finish_count = (EditText)findViewById(R.id.edit_deg);
        finish_count.setText(String.valueOf(count));

    }

    @Override
    public void onClick(View v) {
        int i = v.getId();

        if (i == R.id.gotomain) {
            db = FirebaseDatabase.getInstance();
            dbref = db.getReference();
            String id = user.getUid();

            result = new Result(id, work, type, count, time);
            dbref.child("result").push().setValue(result);

            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();

        }
    }
}

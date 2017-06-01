package com.dudwo.gyrocounter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;

public class result_db  extends Activity implements View.OnClickListener{
    private static final String TAG = "NewPostActivity";

    private FirebaseAuth firebaseAuth;
    FirebaseUser user;

    FirebaseDatabase db;
    DatabaseReference dbref;

    private Iterator<DataSnapshot> category;
    private ArrayList<Result> ResultList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_db);

        ListView listview;
        final ResultViewAdapter adapter;

        adapter = new ResultViewAdapter();

        listview = (ListView)findViewById(R.id.listview_result);
        listview.setAdapter(adapter);


        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        final String uid = user.getUid();
        db = FirebaseDatabase.getInstance();
        dbref = db.getReference("result");

        dbref.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // Get user value

                        category = dataSnapshot.getChildren().iterator();

                        while (category.hasNext()) {
                            DataSnapshot DS = category.next();
                            String cate = "";
                            String type = "";
                            int count, time;
                            if ((DS.child("id").getValue()).equals(uid)) {
                                cate = (String) DS.child("categoty").getValue();
                                type = (String) DS.child("type").getValue();
                                count = Integer.parseInt(DS.child("count").getValue().toString());
                                time = Integer.parseInt(DS.child("time").getValue().toString());

                                adapter.addItem(uid, cate,type,count,time);
                                adapter.notifyDataSetChanged();
                                Log.d("VALUE", String.valueOf(DS.getValue()));
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.d(TAG, "getUser:onCancelled", databaseError.toException());
                    }
                });

    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
    }
}

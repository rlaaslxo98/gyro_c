package com.dudwo.gyrocounter;

import android.app.Activity;
import android.content.Intent;
import android.os.SystemClock;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.SimpleExpandableListAdapter;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static android.R.attr.data;

public class SetRunActivity extends Activity implements View.OnClickListener{
    private static final String TAG = "NewPostActivity";

    private FirebaseAuth firebaseAuth;
    FirebaseUser user;

    FirebaseDatabase db;
    DatabaseReference dbref;

    private Iterator<DataSnapshot> category;
    private ArrayList<Work> WorkList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_run);

        //findViewById(R.id.run_btn).setOnClickListener(this);
        //!!final ListView listview = (ListView) findViewById(R.id.listview) ;

        ListView listview;
        final ListViewAdapter adapter;

        adapter = new ListViewAdapter();

        listview = (ListView)findViewById(R.id.listview);
        listview.setAdapter(adapter);


        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        final String uid = user.getUid();
        db = FirebaseDatabase.getInstance();
        dbref = db.getReference("work");

        //!!WorkList = new ArrayList<Work>();
        //!!final ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_single_choice, WorkList) ;
        //!!listview.setAdapter(adapter) ;

        dbref.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // Get user value

                        category = dataSnapshot.getChildren().iterator();
                        //category = dataSnapshot.child("category").getChildren().iterator();
                        //type = dataSnapshot.child("category").child("type").getChildren().iterator();
                        // ...

                        while (category.hasNext()) {
                            DataSnapshot DS = category.next();
                            String cate = "";
                            String type = "";
                            int max, min;
                            if ((DS.child("id").getValue()).equals(uid)) {
                                cate = (String) DS.child("categoty").getValue();
                                type = (String) DS.child("type").getValue();
                                max = Integer.parseInt(DS.child("max").getValue().toString());
                                min = Integer.parseInt(DS.child("min").getValue().toString());

                                //!!Work work = new Work(uid,cate,type,max,min);
                                //!!WorkList.add(work);
                                //!!adapter.notifyDataSetChanged();
                                adapter.addItem(cate,type,max,min);
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

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id) {
                Work item = (Work)parent.getItemAtPosition(position);
                String work = item.getCategoty();
                String type = item.getType();
                int max = item.getMax();
                int min = item.getMin();

                // Create the result Intent and include the MAC address
                Intent intent = new Intent();
                intent.putExtra("work", work);
                intent.putExtra("type", type);
                intent.putExtra("max", max);
                intent.putExtra("min", min);

                // Set result and finish this Activity
                setResult(Activity.RESULT_OK, intent);
                Log.d("SETRUN", String.valueOf(max)+"~"+String.valueOf(min));
                finish();
            }
        });
    }



    @Override
    public void onClick(View v) {
        int i = v.getId();
/*
        if (i == R.id.run_btn) {
            Intent intent = new Intent(this, RunActivity.class);
            startActivity(intent);
            finish();
        }
*/
    }
}

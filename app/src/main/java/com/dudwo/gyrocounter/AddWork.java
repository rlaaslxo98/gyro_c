package com.dudwo.gyrocounter;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class AddWork extends Fragment implements View.OnClickListener {

    View rootView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.activity_add_work, container, false);

        rootView.findViewById(R.id.add_work_button).setOnClickListener(this);
        // Views

        /** Main Layout **/
        mCategory = (EditText) rootView.findViewById(R.id.category);
        mType = (EditText) rootView.findViewById(R.id.type);
        mMax = (EditText) rootView.findViewById(R.id.max);
        mMin = (EditText) rootView.findViewById(R.id.min);

        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();

        return rootView;
    }


    private FirebaseAuth firebaseAuth;
    FirebaseUser user;

    FirebaseDatabase db;
    DatabaseReference dbref;
    // Layout
    private EditText mCategory;
    private EditText mType;
    private EditText mMax;
    private EditText mMin;
    private Work work;



    public void onClick(View v) {
        int i = v.getId();

        if (i == R.id.add_work_button) {
            db = FirebaseDatabase.getInstance();
            dbref = db.getReference();
            String id = user.getUid();
            String category = mCategory.getText().toString();
            String type = mType.getText().toString();
            int max = Integer.parseInt(mMax.getText().toString());
            int min = Integer.parseInt(mMin.getText().toString());

            work = new Work(id, category, type, max, min);
            dbref.child("work").push().setValue(work);

            Intent intent = new Intent(getActivity(), MainActivity.class);
            startActivity(intent);
            getActivity().finish();
        }
    }
}


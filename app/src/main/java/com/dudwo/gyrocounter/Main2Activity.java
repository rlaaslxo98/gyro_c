package com.dudwo.gyrocounter;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.LocalBroadcastManager;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Main2Activity extends BaseActivity implements View.OnClickListener{

    public static int flag;
    android.app.Fragment fragment;
    private static final int REQUEST_CONNECT_DEVICE = 1;
    private static final int REQUEST_ENABLE_BT = 2;
    private static final int REQUEST_DB = 3;
    private static final String TAG = "Bluetooth Service";

    private BluetoothService btService = null;

    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseUser user = firebaseAuth.getCurrentUser();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        findViewById(R.id.addwork).setOnClickListener(this);
        findViewById(R.id.run).setOnClickListener(this);
        findViewById(R.id.history).setOnClickListener(this);
        findViewById(R.id.help).setOnClickListener(this);

        if(flag==1){
            fragment = new AddWork();
            android.app.FragmentManager fragmentManager=getFragmentManager();
            android.app.FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container,fragment);
            fragmentTransaction.commit();
        }
        if(flag==2){
            fragment = new RunActivity();
            android.app.FragmentManager fragmentManager=getFragmentManager();
            android.app.FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container,fragment);
            fragmentTransaction.commit();
        }


        if (btService == null) {
            btService = new BluetoothService(this, mHandler);
        }
    }

    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Intent intent = new Intent("BLUETOOTH");
            intent.putExtra("value", Integer.parseInt(String.valueOf(msg.what)));
            LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);
            //Log.d("HANDLER",String.valueOf(msg.what));
        }
    };

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        EventBus.getInstance().post(ActivityResultEvent.create(requestCode, resultCode, data));
        switch (requestCode) {
            case REQUEST_CONNECT_DEVICE:
                // When DeviceListActivity returns with a device to connect
                if (resultCode == Activity.RESULT_OK) {
                    btService.getDeviceInfo(data);
                }
                break;

            case REQUEST_ENABLE_BT:
                // When the request to enable Bluetooth returns
                if (resultCode == Activity.RESULT_OK) {
                    // 확인 눌렀을 때
                    // Next Step
                    btService.scanDevice();
                } else {
                    // 취소 눌렀을 때
                    Log.d(TAG, "Bluetooth is not enabled");
                }
                break;
        }
    }

    private void signOut() {
        firebaseAuth.signOut();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.bar_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        if (id == R.id.action_blue){
            if (btService.getDeviceState()){
                //블루투스가 지원가능한 기기일때
                btService.enableBluetooth();
                Log.d("AAAA","AAAA");
                btService.write("a".getBytes());
            }else {
                finish();
            }

        }
        else if(id == R.id.action_logout){
            signOut();
            Intent intent = new Intent(this, EmailPasswordActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.anim01, R.anim.anim02);
            finish();
        }

        else if(id == R.id.action_measure){
            Intent intent = new Intent(this, MeasureValue.class);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }





    @Override
    public void onClick(View v) {
        int i = v.getId();

        if (i == R.id.addwork){
            fragment = new AddWork();
            android.app.FragmentManager fragmentManager=getFragmentManager();
            android.app.FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container,fragment);
            fragmentTransaction.commit();
        }
        if (i == R.id.run){
            fragment = new RunActivity();
            android.app.FragmentManager fragmentManager=getFragmentManager();
            android.app.FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container,fragment);
            fragmentTransaction.commit();
        }
        if (i == R.id.help){
            Intent intent = new Intent(this, Help.class);
            startActivity(intent);
        }
        if (i == R.id.history){
            Intent intent = new Intent(this, result_db.class);
            startActivity(intent);
        }

    }
}

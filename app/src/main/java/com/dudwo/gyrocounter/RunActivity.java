package com.dudwo.gyrocounter;

import android.app.Activity;
import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.squareup.otto.Subscribe;


public class RunActivity extends Fragment implements View.OnClickListener {

    private static final int REQUEST_DB = 3;

    View rootView;
    EditText time;
    TextView run_max;
    TextView run_min;
    EditText select_work;
    private String work;
    private String type;
    private int max;
    private int min;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.activity_run,container,false);

        rootView.findViewById(R.id.gotomain).setOnClickListener(this);
        rootView.findViewById(R.id.popup).setOnClickListener(this);
        run_max = (TextView)rootView.findViewById(R.id.run_max);
        run_min = (TextView)rootView.findViewById(R.id.run_min);
        select_work = (EditText)rootView.findViewById(R.id.sel_work);
        time = (EditText)rootView.findViewById(R.id.time);

        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(
                connectionUpdates , new IntentFilter("BLUETOOTH"));

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        EventBus.getInstance().register(this);
    }

    @Override
    public void onDestroyView() {
        EventBus.getInstance().unregister(this);
        super.onDestroyView();
    }

    BroadcastReceiver connectionUpdates = new BroadcastReceiver() {
        @Override
        public void onReceive(Context arg0, Intent intent) {
            int action = intent.getIntExtra("value", 1);
            Log.d("RUN", String.valueOf(action));

            //TODO here
        }
    };
    @SuppressWarnings("unused")
    @Subscribe
    public void onActivityResultEvent(@NonNull ActivityResultEvent event) {
        onActivityResult(event.getRequestCode(), event.getResultCode(), event.getData());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_DB:
                // When DeviceListActivity returns with a device to connect
                if (resultCode == Activity.RESULT_OK) {
                    work = (String)data.getExtras().get("work");
                    type = (String)data.getExtras().get("type");
                    max = data.getExtras().getInt("max");
                    min = data.getExtras().getInt("min");
                    Log.d("RUN", String.valueOf(max)+"~"+String.valueOf(min));
                }
                run_max.setText(String.valueOf(max));
                run_min.setText(String.valueOf(min));
                select_work.setText(work+":"+type);

                break;
        }
    }

    public void onClick(View v) {
        int i = v.getId();
        int intent_time=0;

        if (i == R.id.popup) {
            Intent intent = new Intent(getActivity(), SetRunActivity.class);
            ActivityCompat.startActivityForResult(getActivity(), intent, REQUEST_DB, null);
            //getActivity().startActivityForResult(intent,REQUEST_DB);
        }

        if (i == R.id.gotomain){
            Intent intent = new Intent(getActivity(), CountRunActivity.class);
            intent_time = Integer.parseInt(time.getText().toString());
            Log.d("TIME",String.valueOf(intent_time));
            intent.putExtra("time",intent_time);
            intent.putExtra("work", work);
            intent.putExtra("type", type);
            intent.putExtra("max", max);
            intent.putExtra("min", min);
            startActivity(intent);
            getActivity().finish();
        }
    }

}
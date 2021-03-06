package com.example.bogda.geekhubandroidgrouplist.recyclerView;

import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.bogda.geekhubandroidgrouplist.R;
import com.example.bogda.geekhubandroidgrouplist.receivers.ChargeReceiver;
import com.example.bogda.geekhubandroidgrouplist.receivers.HeadPhoneReceiver;

public class RecyclerViewActivity extends AppCompatActivity {
    HeadPhoneReceiver headphoneReceiver;
    ChargeReceiver chargeReceiver;
    IntentFilter headphonesFilter = new IntentFilter(Intent.ACTION_HEADSET_PLUG);
    IntentFilter chargeConnectedFilter = new IntentFilter(Intent.ACTION_POWER_CONNECTED);
    IntentFilter chargeDisconnectedFilter = new IntentFilter(Intent.ACTION_POWER_DISCONNECTED);

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().add(R.id.container_recycle_view, new RecyclerViewFragment()).commit();
        }
        getSupportActionBar().setTitle("Group list");
    }

    @Override
    protected void onResume() {
        super.onResume();
        headphoneReceiver = new HeadPhoneReceiver();
        chargeReceiver = new ChargeReceiver();
        registerReceiver(headphoneReceiver, headphonesFilter);
        registerReceiver(chargeReceiver, chargeConnectedFilter);
        registerReceiver(chargeReceiver, chargeDisconnectedFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        try {
            unregisterReceiver(headphoneReceiver);
            unregisterReceiver(chargeReceiver);
        }
        catch (Exception e){
            return;
        }
    }

}


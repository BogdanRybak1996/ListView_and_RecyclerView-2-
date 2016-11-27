package com.example.bogda.geekhubandroidgrouplist.listView;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.bogda.geekhubandroidgrouplist.R;
import com.example.bogda.geekhubandroidgrouplist.Receivers.ChargeReceiver;
import com.example.bogda.geekhubandroidgrouplist.Receivers.HeadPhoneReceiver;

public class ListViewActivity extends AppCompatActivity {
    HeadPhoneReceiver headphoneReceiver;
    ChargeReceiver chargeReceiver;
    IntentFilter headphonesFilter = new IntentFilter(Intent.ACTION_HEADSET_PLUG);
    IntentFilter chargeConnectedFilter = new IntentFilter(Intent.ACTION_POWER_CONNECTED);
    IntentFilter chargeDisconnectedFilter = new IntentFilter(Intent.ACTION_POWER_DISCONNECTED);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().add(R.id.container_list_view, new ListViewFragment()).commit();
        }
        getSupportActionBar().setTitle("Group list");
    }

    @Override
    protected void onResume() {
        super.onResume();
        chargeReceiver = new ChargeReceiver();
        headphoneReceiver = new HeadPhoneReceiver();
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

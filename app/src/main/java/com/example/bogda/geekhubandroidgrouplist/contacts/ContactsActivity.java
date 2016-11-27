package com.example.bogda.geekhubandroidgrouplist.contacts;

import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.bogda.geekhubandroidgrouplist.R;
import com.example.bogda.geekhubandroidgrouplist.Receivers.ChargeReceiver;
import com.example.bogda.geekhubandroidgrouplist.Receivers.HeadPhoneReceiver;
import com.example.bogda.geekhubandroidgrouplist.listView.ListViewFragment;

public class ContactsActivity extends AppCompatActivity {

    HeadPhoneReceiver headphoneReceiver;
    ChargeReceiver chargeReceiver;
    IntentFilter headphonesFilter = new IntentFilter(Intent.ACTION_HEADSET_PLUG);
    IntentFilter chargeConnectedFilter = new IntentFilter(Intent.ACTION_POWER_CONNECTED);
    IntentFilter chargeDisconnectedFilter = new IntentFilter(Intent.ACTION_POWER_DISCONNECTED);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().add(R.id.container_contacts, new ContactsFragment()).commit();
        }
        getSupportActionBar().setTitle("Contacts");
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

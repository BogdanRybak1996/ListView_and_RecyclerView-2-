package com.example.bogda.geekhubandroidgrouplist.main;

import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.bogda.geekhubandroidgrouplist.receivers.ChargeReceiver;
import com.example.bogda.geekhubandroidgrouplist.receivers.HeadPhoneReceiver;
import com.example.bogda.geekhubandroidgrouplist.contacts.ContactsActivity;
import com.example.bogda.geekhubandroidgrouplist.listView.ListViewActivity;
import com.example.bogda.geekhubandroidgrouplist.R;
import com.example.bogda.geekhubandroidgrouplist.photo.PhotoActivity;
import com.example.bogda.geekhubandroidgrouplist.recyclerView.RecyclerViewActivity;

public class MainActivity extends AppCompatActivity {
    HeadPhoneReceiver headphoneReceiver;
    ChargeReceiver chargeReceiver;
    IntentFilter headphonesFilter = new IntentFilter(Intent.ACTION_HEADSET_PLUG);
    IntentFilter chargeConnectedFilter = new IntentFilter(Intent.ACTION_POWER_CONNECTED);
    IntentFilter chargeDisconnectedFilter = new IntentFilter(Intent.ACTION_POWER_DISCONNECTED);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button listViewButton = (Button) findViewById(R.id.list_view_button);
        Button recycleViewButton = (Button) findViewById(R.id.recycle_view_button);
        Button makePhotoButton = (Button) findViewById(R.id.photo_button);
        Button contactsButton = (Button) findViewById(R.id.contacts_button);
        listViewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ListViewActivity.class);
                startActivity(intent);
            }
        });
        recycleViewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RecyclerViewActivity.class);
                startActivity(intent);
            }
        });
        makePhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), PhotoActivity.class);
                startActivity(intent);
            }
        });
        contactsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ContactsActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        headphoneReceiver = new HeadPhoneReceiver();
        chargeReceiver = new ChargeReceiver();
        registerReceiver(headphoneReceiver,headphonesFilter);
        registerReceiver(chargeReceiver,chargeConnectedFilter);
        registerReceiver(chargeReceiver,chargeDisconnectedFilter);
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

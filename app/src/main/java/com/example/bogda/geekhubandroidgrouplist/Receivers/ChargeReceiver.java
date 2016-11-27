package com.example.bogda.geekhubandroidgrouplist.Receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by bohdan on 10.11.16.
 */

public class ChargeReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        switch (action){
            case "android.intent.action.ACTION_POWER_CONNECTED": {
                Toast.makeText(context, "Charging ON", Toast.LENGTH_SHORT).show();
                break;
            }
            case "android.intent.action.ACTION_POWER_DISCONNECTED": {
                Toast.makeText(context, "Charging OFF", Toast.LENGTH_SHORT).show();
                break;
            }
        }
    }
}

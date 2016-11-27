package com.example.bogda.geekhubandroidgrouplist.Receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;


public class HeadPhoneReceiver extends BroadcastReceiver {
    private boolean firstReceive = true;
    @Override
    public void onReceive(Context context, Intent intent) {
        if(!firstReceive) {
            int state = intent.getIntExtra("state", -1);
            switch (state) {
                case 1:
                    Toast.makeText(context, "Headphones plugged", Toast.LENGTH_SHORT).show();
                    break;
                case 0:
                    Toast.makeText(context, "Headphones unplugged", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
        else{
            firstReceive = false;
        }
    }
}

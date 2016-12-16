package com.example.bogda.geekhubandroidgrouplist.service;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by bogda on 16.12.2016.
 */

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
        RealmConfiguration conf = new RealmConfiguration.Builder()
                .name("users_realm.realm")
                .schemaVersion(1)
                .build();
        Realm.setDefaultConfiguration(conf);
    }
}

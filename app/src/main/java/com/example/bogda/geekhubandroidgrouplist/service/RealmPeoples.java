package com.example.bogda.geekhubandroidgrouplist.service;

import android.support.annotation.Nullable;

import com.example.bogda.geekhubandroidgrouplist.data.People;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by bogda on 16.12.2016.
 */

public class RealmPeoples {
    public static void savePeoples(ArrayList<People> peoples) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.insertOrUpdate(peoples);
        realm.commitTransaction();
        realm.close();
    }

    @Nullable
    public static ArrayList<People> getAllPeoples() {
        Realm realm = Realm.getDefaultInstance();
        ArrayList<People> peoples;
        RealmResults<People> results = realm.where(People.class).findAll();
        if (results.size() == 0) {
            return null;
        }
        peoples = new ArrayList<>(results);
        realm.close();
        return peoples;
    }

}

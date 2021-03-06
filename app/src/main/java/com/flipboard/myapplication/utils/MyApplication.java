package com.flipboard.myapplication.utils;

import android.app.Application;

import com.google.firebase.messaging.FirebaseMessaging;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
        FirebaseMessaging.getInstance().subscribeToTopic("newProduct");
        FirebaseMessaging.getInstance().subscribeToTopic("newPdf");
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder().deleteRealmIfMigrationNeeded().build();
        Realm.setDefaultConfiguration(realmConfiguration);
    }
}
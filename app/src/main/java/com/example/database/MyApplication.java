package com.example.database;

import android.app.Application;
import android.content.Context;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class MyApplication extends Application {

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        Realm.init(this);
        RealmConfiguration configuration = new RealmConfiguration.Builder()
                .name("myRealmDataBase")
                .deleteRealmIfMigrationNeeded()
                .addModule(new RealmModule())
                .schemaVersion(1)
                .build();
        Realm.setDefaultConfiguration(configuration);
    }
}

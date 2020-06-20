package com.example.database;

import androidx.annotation.NonNull;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class RealmDatabaseItem extends RealmObject {
    @PrimaryKey
    String name;

    private String mDefaultItem;

    String getDefaultItem() {
        return mDefaultItem;
    }

    @NonNull
    @Override
    public String toString() {
        return name + "\n";
    }
}

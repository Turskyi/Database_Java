package com.example.database;

import android.os.Build;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import java.util.Objects;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class RealmParentItem extends RealmObject {
    @PrimaryKey String name;
    RealmList<RealmDatabaseItem> items;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @NonNull
    @Override
    public String toString() {
        StringBuilder itemsText = new StringBuilder();
        for (int i = 0; i < items.size(); i++) {
            itemsText.append(Objects.requireNonNull(items.get(i)).toString());
        }
        return "Parent: " + name + "\n Items:\n" + itemsText;
    }
}

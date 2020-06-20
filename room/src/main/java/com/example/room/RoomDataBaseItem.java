package com.example.room;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class RoomDataBaseItem {
    @PrimaryKey(autoGenerate = true)
            int id;
    String name;
    String description;
    int amount;

    @Override
    public String toString() {
        return name + ", " + description + ", " + amount + "\n";
    }
}

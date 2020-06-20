package com.example.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(version = 1, entities = {RoomDataBaseItem.class})
abstract class RoomItemsDatabase extends RoomDatabase {
    public abstract RoomItemDAO getRoomItemDao();
}

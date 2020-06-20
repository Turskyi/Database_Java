package com.example.room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface RoomItemDAO  {

    @Insert
    void addItem(RoomDataBaseItem item);

    @Update
    void updateItem(RoomDataBaseItem item);

    @Delete
    void  deleteItem(RoomDataBaseItem item);

    @Query("SELECT * FROM RoomDataBaseItem")
    List<RoomDataBaseItem> getAllItems();

    @Query("SELECT * FROM RoomDataBaseItem WHERE amount BETWEEN :min AND :max")
    List<RoomDataBaseItem> selectItemsByAmount(int min, int max);
}

package com.example.jishorough2;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface EntryDao {
    @Query("SELECT * FROM entries ORDER BY timestamp DESC")
    LiveData<List<Entry>> getAll();

    @Query("SELECT * FROM entries WHERE list_name LIKE :listName ORDER BY timestamp ASC")
    LiveData<List<Entry>> getList(String listName);

    @Insert
    void insert(Entry e);

    @Delete
    void delete(Entry e);

    @Query("SELECT DISTINCT list_name FROM entries ORDER BY list_name")
    LiveData<List<String>> getListNames();
}

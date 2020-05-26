package com.example.jishorough2;

import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity(tableName = "entry_lists")
public class EntryLists {
    @PrimaryKey @ColumnInfo(name = "uid")
    private String userID;
    @ColumnInfo(name = "lists")
    private Map<String, List<Entry>> mLists;

    public EntryLists(String userID) {

        this.userID = userID;
        mLists = new HashMap<String, List<Entry>>();
        mLists.put("Favorites", new ArrayList<Entry>());
    }

    public EntryLists(Map<String, List<Entry>> lists, String userID) {
        mLists = lists;
        this.userID = userID;
    }

    public Map<String, List<Entry>> getLists() {
        return mLists;
    }

    public void setLists(Map<String, List<Entry>> Lists) {
        this.mLists = Lists;
    }

    public void addToList(String listName, Entry entry){
        entry.setTimestamp(Instant.now());
        mLists.get(listName).add(entry);
    }
    public void removeFromList(String listName, Entry entry){
        mLists.get(listName).remove(entry);
    }
    public void addList(String listName, @Nullable List<Entry> list){
        if(list == null){
            list = new ArrayList<Entry>();
        }
        mLists.put(listName, list);

    }
    public void removeList(String listName){
        mLists.remove(listName);
    }
}

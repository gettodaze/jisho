package com.example.jishorough2;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

public class EntryRepository {

    private EntryDao mEntryDao;


    EntryRepository(Application application){
        AppDatabase db = AppDatabase.getDBInstance(application);
        mEntryDao = db.entryDao();
    }


    void insert(Entry e){
        AppDatabase.databaseWriteExecutor.execute(() -> {
            mEntryDao.insert(e);
        });
    }
    void delete(Entry e){
        AppDatabase.databaseWriteExecutor.execute(() -> {
            mEntryDao.delete(e);
        });
    }

    public LiveData<List<Entry>> getEntries() {
        return mEntryDao.getAll();
    }

    public LiveData<List<String>> getListNames() {
        return mEntryDao.getListNames();
    }


    public LiveData<List<Entry>> getList(String listName) {
        return mEntryDao.getList(listName);
    }

}

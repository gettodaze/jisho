package com.example.jishorough2;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.HashMap;
import java.util.List;

public class EntryViewModel extends AndroidViewModel {

    public EntryRepository mRepository;
    private LiveData<List<Entry>> entries;
    private LiveData<List<String>> listNames;
    private MutableLiveData<String> curListName;
    private MutableLiveData<List<Entry>> curList;


    private HashMap<String, LiveData<List<Entry>>> lists;

    public EntryViewModel(Application application) {
        super(application);
        mRepository = new EntryRepository(application);
        entries = mRepository.getEntries();
        listNames = mRepository.getListNames();
        curListName = new MutableLiveData<>();
        curList = new MutableLiveData<>();
    }

    public void insert(Entry e){
        mRepository.insert(e);
    }

    public void delete(Entry e){
        mRepository.delete(e);
    }

    public LiveData<List<Entry>> getList(String listName) {
        return mRepository.getList(listName);
    }


    public LiveData<List<Entry>> getEntries() {
        return entries;
    }

    public LiveData<List<String>> getListNames() {
        return listNames;
    }

    public LiveData<String> getCurListName() {
        return curListName;
    }

    public MutableLiveData<List<Entry>> getCurList() {
        return curList;
    }

    public void setCurListName(String curListName) {
        this.curListName.setValue(curListName);
        this.curList.setValue(mRepository.getList(curListName).getValue());
    }
}

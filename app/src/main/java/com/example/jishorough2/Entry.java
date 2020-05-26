package com.example.jishorough2;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.Instant;
import java.util.ArrayList;

@Entity(tableName = "entries")
public class Entry {

    // list of [word, reading] pairs
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name="uid")
    private String uid;
    @ColumnInfo(name="word_readings")
    private ArrayList<String[]> wordReadings;
    // definition
    @ColumnInfo(name="definition")
    private String definition;
    // timestamp in millis since last epoch
    @ColumnInfo(name="timestamp")
    private Instant timestamp;
    // string note
    @ColumnInfo(name="note")
    private String note;
    @ColumnInfo(name="list_name")
    private String listName;

    private static String DEFAULT_UID = "default";

    public Entry(JSONArray wordReadings, String definition) throws JSONException {
        this(Entry.wordReadingsFromJSONArr(wordReadings), definition);
    }
    public Entry(ArrayList<String[]> wordReadings, String definition) {
        this.wordReadings = wordReadings;
        this.definition = definition;
        this.timestamp = Instant.now();
        this.note = "";
        this.listName = "";
        this.uid = Entry.DEFAULT_UID;
    }
    public ArrayList<String[]> getWordReadings() {
        return wordReadings;
    }

    public void setWordReadings(ArrayList<String[]> wordReadings) {
        this.wordReadings = wordReadings;
    }

    public ArrayList<String> getWords() {
        ArrayList<String> words = new ArrayList<>();
        for(String[] wordReading : this.wordReadings){
            words.add(wordReading[0]);
        }
        return words;
    }

    public ArrayList<String> getReadings() {
        ArrayList<String> readings = new ArrayList<>();
        for(String[] wordReading : this.wordReadings){
            readings.add(wordReading[1]);
        }
        return readings;
    }

    public String getDefinition() {
        return definition;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getListName() {
        return listName;
    }

    public void setListName(String listName) {
        this.listName = listName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String wordReadingsString() {
        String[] wordReadingsFormatted = new String[this.wordReadings.size()];
        for(int i = 0; i < this.wordReadings.size(); i++){
            String[] wordReading = this.wordReadings.get(i);
            wordReadingsFormatted[i] = wordReading[0] + "(" + wordReading[1] + ")";
        }
        return String.join("; ", wordReadingsFormatted);

    }

    @NonNull
    @Override
    public String toString() {
        String wordReadingsFormattedString = wordReadingsString();
        return wordReadingsFormattedString + ": " + this.definition;
    }

    public static ArrayList<String[]> wordReadingsFromJSONArr(JSONArray ja) throws JSONException{
        ArrayList<String[]> wordReadings = new ArrayList<>();
        for(int i = 0; i<ja.length(); i++){
            String [] wordReading;
            JSONObject jo = ja.getJSONObject(i);
            wordReading = new String[2];
            wordReading[0] = "";
            wordReading[1] = "";
            try{
                wordReading[0] = jo.getString("word");
            }
            catch(JSONException e) {

            }
            try{
                wordReading[1] = jo.getString("reading");
            }
            catch(JSONException e) {

            }

            wordReadings.add(wordReading);
        }
        return wordReadings;
    }

}



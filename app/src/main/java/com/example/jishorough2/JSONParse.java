package com.example.jishorough2;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;

import java.lang.reflect.Type;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class JSONParse {
    public static ArrayList<String> JSONArrayToStingList(JSONArray ja) throws JSONException {
        ArrayList<String> list = new ArrayList<String>();
        for(int i=0; i<ja.length(); i++){
           list.add(ja.getString(i));
        }
        return list;
    }
    @TypeConverter
    public static ArrayList<String[]> nestedListFromString(String value) {
        Type listType = new TypeToken<ArrayList<String[]>>() {
        }.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String fromNestedList(ArrayList<String[]> list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }

    @TypeConverter
    public static long millisecondsFromInstant(Instant instant){
        return instant.toEpochMilli();
    }

    @TypeConverter
    public static Instant instantFromMilliseconds(long milliseconds){
        return Instant.ofEpochMilli(milliseconds);
    }

}

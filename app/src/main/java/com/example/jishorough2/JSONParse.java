package com.example.jishorough2;

import org.json.JSONArray;
import org.json.JSONException;

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
}

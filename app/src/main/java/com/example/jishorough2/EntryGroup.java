package com.example.jishorough2;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.provider.Contacts;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class EntryGroup {
    private ArrayList<Entry> entries;

    public EntryGroup(ArrayList<Entry> entries) throws IllegalArgumentException {

        EntryGroup.checkEntries(entries);
        this.entries = entries;
    }

    public ArrayList<Entry> getEntries() {
        return entries;
    }

    public boolean addEntry(Entry e) {
        if(!e.getWordReadings().equals(this.getWordReadings())){
            return false;
        }
        this.entries.add(e);
        return true;
    }

    public ArrayList<String[]> getWordReadings() {
        return this.entries.get(0).getWordReadings();
    }

    public String wordReadingsString() {
        return this.entries.get(0).wordReadingsString();
    }

    public static boolean compareWordReadings(Entry e1, Entry e2){
        boolean equals = e1.wordReadingsString().equals(e2.wordReadingsString());
        return equals;
    }

    public static void checkEntries(ArrayList<Entry> entries) throws IllegalArgumentException{
        if(entries.size() < 1){
            throw new IllegalArgumentException("No entries exist for entry group.");
        }
        Entry og = entries.get(0);
        for(Entry e: entries) {
            if (!compareWordReadings(e, og)) {
                throw new IllegalArgumentException("Not all entries have the same wordReadings");
            }
        }
    }


    public LinearLayout toButtons(final Context context, int num_defs){
        LinearLayout LL = new LinearLayout(context);
        LL.setOrientation(LinearLayout.VERTICAL);
        Button b = UIUtils.textButton(context, wordReadingsString());
        b.setBackgroundColor(Color.RED);
        b.setTextColor(Color.WHITE);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                Toast toast = Toast.makeText(context, "Clicked on "+wordReadingsString(), Toast.LENGTH_SHORT);
                toast.show();
            }
        });
        LL.addView(b);

        int i = 0;
        for(Entry e : getEntries()) {
            if (i <= num_defs) {
                final String definition = e.getDefinition();
                Button c = UIUtils.textButton(context, definition);
                c.setBackgroundColor(Color.BLACK);
                c.setTextColor(Color.WHITE);
                c.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view){
                        Toast toast = Toast.makeText(context, "Clicked on button. "+definition, Toast.LENGTH_SHORT);
                        toast.show();
                    }
                });
                LL.addView(c);
            }
        }
        return LL;
    }


    @Override
    public String toString() {
        return "EntryGroup{" +
                "word=" + this.getWordReadings() +
                "entries=" + entries +
                '}';
    }

    public static ArrayList<EntryGroup> createGroups(ArrayList<Entry> entries){
        HashMap<String, ArrayList<Entry>> map = new HashMap<>();
        for(Entry e: entries){
            String wordReadingsString = e.wordReadingsString();
            if(!map.containsKey(wordReadingsString)){
                map.put(wordReadingsString, new ArrayList<Entry>());
            }
            Objects.requireNonNull(map.get(wordReadingsString)).add(e);
        }
        ArrayList<EntryGroup> groups = new ArrayList<EntryGroup>();
        for(ArrayList<Entry> el : map.values()) {
            groups.add(new EntryGroup(el));

        }
        return groups;
    }
}

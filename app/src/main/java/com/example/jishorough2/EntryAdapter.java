package com.example.jishorough2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class EntryAdapter extends RecyclerView.Adapter<EntryAdapter.EntryViewHolder>{
    
    private List<Entry> entries;
    public class EntryViewHolder extends RecyclerView.ViewHolder {

        public View itemView;
        public EntryViewHolder(View v ) {
            super(v);
            itemView = v;
        }

    }

    EntryAdapter(List<Entry> entries){
        this.entries = entries;
    }

    @NonNull
    @Override
    public EntryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_entry, parent, false);
        return new EntryViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull EntryViewHolder holder, int position) {
        EntryView ev_word = holder.itemView.findViewById(R.id.entryview_listword);
        EntryView ev_def = holder.itemView.findViewById(R.id.entryview_listdef);
        Entry e = entries.get(position);
        ev_word.setEntry(e);
        ev_def.setEntry(e);
        ev_word.setText(e.wordReadingsString());
        ev_def.setText(e.getDefinition());

    }


    public void setEntries(List<Entry> entries){
        this.entries = entries;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return entries.size();
    }


}

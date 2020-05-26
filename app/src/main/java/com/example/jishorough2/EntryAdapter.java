package com.example.jishorough2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.List;

public class EntryAdapter extends RecyclerView.Adapter<EntryAdapter.EntryViewHolder>{
    
    private List<Entry> entries;
    public class EntryViewHolder extends RecyclerView.ViewHolder {

        public View itemView;
        public TextView tvWord;
        public TextView tvDef;
        public EntryViewHolder(View v ) {
            super(v);
            itemView = v;
            tvDef = itemView.findViewById(R.id.textview_listdef);
            tvWord = itemView.findViewById(R.id.textview_listword);
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
        Entry e = entries.get(position);
        holder.tvWord.setText(e.wordReadingsString());
        holder.tvDef.setText(e.getDefinition());

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

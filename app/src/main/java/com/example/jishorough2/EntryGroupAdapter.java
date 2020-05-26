package com.example.jishorough2;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class EntryGroupAdapter extends RecyclerView.Adapter<EntryGroupAdapter.EntryGroupViewHolder> {

    private List<EntryGroup> entries;

    public class EntryGroupViewHolder extends RecyclerView.ViewHolder {

        public View itemView;
        public EntryGroupViewHolder(View v ) {
            super(v);
            itemView = v;
        }

    }

    EntryGroupAdapter(List<EntryGroup> entries){
        this.entries = entries;

    }


    @NonNull
    @Override
    public EntryGroupViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_entrygroup, parent, false);
        return new EntryGroupViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull EntryGroupViewHolder holder, int position) {
        LinearLayout LL = holder.itemView.findViewById(R.id.llDefinitions);
        int i = 0;
        for(Entry e : entries.get(position).getEntries()) {
            if (i <= 3) {
                final String definition = e.getDefinition();
                EntryView defView = (EntryView) LayoutInflater.from(
                        holder.itemView.getContext()).inflate(R.layout.item_definition, LL, false);
                defView.setText(definition);
                defView.setEntry(e);
                LL.addView(defView);
                i++;
            }
        }
        EntryView tvWord = holder.itemView.findViewById(R.id.tvWord);
        tvWord.setText(entries.get(position).wordReadingsString());
        tvWord.setEntryGroup(entries.get(position));
    }

    @Override
    public int getItemCount() {
        return entries.size();
    }

    public EntryGroup getItemAt(int position){
        return entries.get(position);
    }


}

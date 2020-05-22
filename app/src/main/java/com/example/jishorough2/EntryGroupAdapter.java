package com.example.jishorough2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class EntryGroupAdapter extends RecyclerView.Adapter<EntryGroupAdapter.EntryViewHolder> {

    private List<EntryGroup> entries;

    public class EntryViewHolder extends RecyclerView.ViewHolder {

        public View itemView;
        public EntryViewHolder(View v ) {
            super(v);
            itemView = v;
        }

    }

    EntryGroupAdapter(List<EntryGroup> entries){
        this.entries = entries;
    }

    @NonNull
    @Override
    public EntryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_entrygroup, parent, false);
        return new EntryViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull EntryViewHolder holder, int position) {
        LinearLayout LL = holder.itemView.findViewById(R.id.llDefinitions);
        int i = 0;
        for(Entry e : entries.get(position).getEntries()) {
            if (i <= 3) {
                final String definition = e.getDefinition();
                TextView defView = (TextView) LayoutInflater.from(
                        holder.itemView.getContext()).inflate(R.layout.item_definition, LL, false);
                defView.setText(definition);
                LL.addView(defView);
                i++;
            }
        }
        TextView tvWord = holder.itemView.findViewById(R.id.tvWord);
        tvWord.setText(entries.get(position).wordReadingsString());

    }

    @Override
    public int getItemCount() {
        return entries.size();
    }
}

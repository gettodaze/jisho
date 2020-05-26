package com.example.jishorough2;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ListsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListsFragment extends Fragment {
    private RecyclerView rvList;
    private EntryViewModel mEntryViewModel;
    private EntryAdapter adapter;

    public ListsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ListsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ListsFragment newInstance() {
        ListsFragment fragment = new ListsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_lists, container, false);

        rvList = view.findViewById(R.id.rvList);
        adapter = new EntryAdapter(new ArrayList<Entry>());
        rvList.setAdapter(adapter);
        rvList.setLayoutManager(new LinearLayoutManager(getContext()));
        mEntryViewModel = new ViewModelProvider(requireActivity()).get(EntryViewModel.class);
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                Entry e = adapter.getEntryAtPosition(viewHolder.getAdapterPosition());
                Toast.makeText(getContext(), e.getTimestamp().toString(), Toast.LENGTH_SHORT).show();
                mEntryViewModel.delete(e);
            }
        }).attachToRecyclerView(rvList);
        subscribe();
        return view;
    }

    private void subscribe(){
        mEntryViewModel.getEntries().observe(requireActivity(), new Observer<List<Entry>>() {
            @Override
            public void onChanged(List<Entry> entries) {
                if(entries != null){
                   adapter.setEntries(entries);
                }
            }
        });
    }
}

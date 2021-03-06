package com.example.jishorough2;

import android.content.Context;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static android.util.Log.d;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the
 * interface.
 */
public class SearchFragment extends Fragment {
    private EditText etSearch;
    private RecyclerView rvDefinitions;
    private Button btnSearch;
    private AnimationDrawable adLoading;
    private ImageView ivLoading;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public SearchFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static SearchFragment newInstance() {
        SearchFragment fragment = new SearchFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }



    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);



    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        etSearch = view.findViewById(R.id.etSearch);
        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    search(etSearch.getText().toString());
                    return true;
                }
                return false;
            }
        });
        rvDefinitions = view.findViewById(R.id.rvDefinitions);
        rvDefinitions.setAdapter(new EntryGroupAdapter(new ArrayList<EntryGroup>()));
        rvDefinitions.setLayoutManager(new LinearLayoutManager(getContext()));

        ivLoading = view.findViewById(R.id.ivLoading);
        adLoading = (AnimationDrawable) ivLoading.getDrawable();
        ivLoading.setVisibility(View.GONE);
        btnSearch = view.findViewById(R.id.btnSearch);
        btnSearch.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        d("SearchFragment", "clicked button");
                        search(etSearch.getText().toString());
                    }
                }
        );
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


    private ArrayList<Entry> jishoAPIHandler(String response) throws JSONException {
        JSONObject responseJSON;
        try {
            responseJSON = new JSONObject(response);
        } catch (JSONException e) {
            throw new UnexpectedJSONException("Response not formatted in parseable JSON", e);
        }
        JSONArray data;
        try {
            data = responseJSON.getJSONArray("data");
        } catch (JSONException e) {
            throw new UnexpectedJSONException("Response did not include a data field",e);
        }

        ArrayList<Entry> entries = new ArrayList<>();
        for(int i=0; i<data.length(); i++){
            JSONObject jsonEntry = data.getJSONObject(i);
            JSONArray wordReadings = jsonEntry.getJSONArray("japanese");
            JSONArray senses = jsonEntry.getJSONArray("senses");
            for (int j = 0; j < senses.length(); j++) {
                JSONObject sense = senses.getJSONObject(j);
                ArrayList<String> eng_definitions = JSONParse.JSONArrayToStingList(
                        sense.getJSONArray("english_definitions"));
                String def_string = String.join(", ", eng_definitions);
                entries.add(new Entry(wordReadings, def_string));
            }

        }
        return entries;

    }

    private Button textButton(String text){
        Button b = new Button(getContext());
        b.setText(text);
        return b;
    }


    private void search(final String query) {
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        String url = "https://jisho.org/api/v1/search/words?keyword="+query;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        d("SearchFragment - Search", "Response received! ");
                        rvDefinitions.removeAllViews();
                        //
                        ArrayList<Entry> entries = null;
                        try {entries = jishoAPIHandler(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        if( entries != null){
                            // set new entries ass view
                            ArrayList<EntryGroup> egs = EntryGroup.createGroups(entries);
                            rvDefinitions.setAdapter(new EntryGroupAdapter(egs));
                        }
                        else{
                            rvDefinitions.addView(textButton("No results for "+query));
                        }
                        adLoading.stop();
                        ivLoading.setVisibility(View.GONE);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        d("SearchFragment - Search", "Error!"+error.toString());
                        adLoading.stop();
                        ivLoading.setVisibility(View.GONE);
                    }
                });
        d("SearchFragment - Search", "Adding "+url+" to queue.");
        queue.add(stringRequest);
        ivLoading.setVisibility(View.VISIBLE);
        adLoading.start();
    }





}

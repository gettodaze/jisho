package com.example.jishorough2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
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

import java.lang.reflect.Array;
import java.util.ArrayList;

import static android.util.Log.d;

public class MainActivity extends AppCompatActivity {

    private EditText searchbox;
    private TextView resultbox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        searchbox = findViewById(R.id.searchbox);
        resultbox = findViewById(R.id.resultbox);

    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == 66) {
            d("MainActivity", "Searched for: " + searchbox.getText());
            search(searchbox.getText().toString());
        }

        return super.onKeyUp(keyCode, event);
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


        private void search(String query) {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://jisho.org/api/v1/search/words?keyword="+query;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        d("MainActivity", "Responding...");

                        ArrayList<Entry> entries = null;
                        try {
                            entries = jishoAPIHandler(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        // Display the first 500 characters of the response string.
                        String[] entryStrings = new String[entries.size()];
                        int i = 0;
                        for(Entry e : entries){
                            entryStrings[i] = e.toString();
                            i += 1;
                        }
                        resultbox.setText(String.join("\n", entryStrings));
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        resultbox.setText("That didn't work!");
                    }
                });
        queue.add(stringRequest);
    }



}

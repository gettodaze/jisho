package com.example.jishorough2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;

import static android.util.Log.d;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private EditText searchbox;
    private LinearLayout resultbox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        searchbox = findViewById(R.id.searchbox);
        resultbox = findViewById(R.id.resultBox);

        toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                toolbar,
                R.string.openNavDrawer,
                R.string.closeNavDrawer
        );

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

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

    private Button textButton(String text){
        Button b = new Button(MainActivity.this);
        b.setText(text);
        return b;
    }


    private void search(final String query) {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://jisho.org/api/v1/search/words?keyword="+query;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        d("MainActivity", "Responding...");
                        resultbox.removeAllViews();
                        resultbox.addView(textButton("Loading..."));
                        //
                        ArrayList<Entry> entries = null;
                        try {entries = jishoAPIHandler(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        resultbox.removeAllViews();
                        if( entries != null){
                            // set new entries ass view
                            for(Entry e : entries) {
                                resultbox.addView(textButton(e.toString()));
                            }
                        }
                        else{
                            resultbox.addView(textButton("No results for "+query));
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        TextView errorbox = new TextView((MainActivity.this));
                        errorbox.setText("That didn't work!");
                        resultbox.addView(errorbox);
                    }
                });
        queue.add(stringRequest);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}

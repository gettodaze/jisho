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

import org.json.JSONException;
import org.json.JSONObject;

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

    private JSONObject jishoAPI(String query){

    }

    private void search(String query) {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://jisho.org/api/v1/search/words?keyword="+query;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        d("MainActivity", "Responding...");
                        JSONObject responseJSON = null;
                        try {
                            responseJSON = new JSONObject(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            resultbox.setText("JSON could not be read!");
                        }
                        try {
                            data = responseJSON.getJSONArray("data");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        data.
                        // Display the first 500 characters of the response string.
                        resultbox.setText(response.substring(0,500));
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

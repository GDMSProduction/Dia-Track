package com.Diatrack.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MealSearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.Diatrack.R.layout.activity_meal_search);
        try {
            GetSearch();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    void GetSearch() throws IOException {
// ...
        //final TextView TestAPI = (TextView)findViewById(R.id.TestAPI);
// Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        final TextView TestAPI = (TextView)findViewById(com.Diatrack.R.id.TestAPI);
        String url ="https://trackapi.nutritionix.com/v2/search/instant ";


// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,

                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        TestAPI.setText(response);
                    }
                },
                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                })
        {//no semicolon or coma
                        @Override
                        public Map<String, String> getHeaders() throws AuthFailureError {
                            Map<String, String> params = new HashMap<String, String>();
                            params.put("x-app-id:", "90a86e7a");
                            params.put("x-app-key:","e7b6c459a87cc03a794eeac1237d8ce1");
                            params.put("x-remote-user-id:","0");
                            return params;
                        }
                    };


// Add the request to the RequestQueue.
        queue.add(stringRequest);
    }


}

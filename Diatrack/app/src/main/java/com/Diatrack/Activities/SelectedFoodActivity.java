package com.Diatrack.Activities;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.Diatrack.Classes.Nutrition;
import com.Diatrack.R;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static com.Diatrack.R.id.foodName;
import static com.Diatrack.R.id.txt_Calories;
import static com.Diatrack.R.id.txt_Carbs;
import static com.Diatrack.R.id.txt_Fats;
import static com.Diatrack.R.id.txt_Protein;
import static com.Diatrack.R.id.txt_quantity;

public class SelectedFoodActivity extends AppCompatActivity {

    float quantity = 1;
    int id =0;
    String name;
    TextView quantityLabel;
    TextView Foodlabel;
    TextView FoodCalories;
    TextView FoodProtein;
    TextView FoodFat;
    TextView FoodCarbs;
    String url ="https://trackapi.nutritionix.com/v2/search/item?nix_item_id=";
    String delims = "[,:]+";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected__food);
        Bundle bundle = getIntent().getExtras();
        id = bundle.getInt("Id");
        name = bundle.getString("Name");
        try {
            sendingPostRequest();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Button save = findViewById(R.id.btn_save);
        quantityLabel =  findViewById(txt_quantity);
        Foodlabel = findViewById(foodName);
        FoodCalories = findViewById(txt_Calories);
        FoodCarbs = findViewById(txt_Carbs);
        FoodProtein = findViewById(txt_Protein);
        FoodFat = findViewById(txt_Fats);

        Foodlabel.setText(name);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SelectedFoodActivity.this, MealDoneActivity.class));

            }
        });

        FloatingActionButton up = findViewById(R.id.btn_up);
        FloatingActionButton down = findViewById(R.id.btn_down);

        up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addone();
            }
        });
        down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (quantity >0 )
                subtractone();
            }
        });
    }
    void GetNutritionWithID() throws IOException {
// ...

// Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);

        url ="https://trackapi.nutritionix.com/v2/search/item?nix_item_id=58b12c4719be9e5442539a95";
        //url = url + id;
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,

                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            String[] tokens = response.split(delims);
                            Gson gson = new Gson();
                            Nutrition nutritions = gson.fromJson(response, Nutrition.class);
                           // for (int i = 0; i < response.length(); i = i + 2)
                           // {
                           //     if (tokens[i] == "nf_calories")
                           //     {
                           //         FoodCalories.setText(tokens[i + 1]);
                           //         i--;
                           //     }
                           //     else if (tokens[i] == "nf_protein")
                           //     {
                           //         FoodProtein.setText(tokens[i + 1]);
                           //         i--;
                           //     }
                           //     else if (tokens[i] == "nf_total_fat")
                           //     {
                           //         FoodFat.setText(tokens[i + 1]);
                           //         i--;
                           //     }
                           //     else if (tokens[i] == "nf_total_carbohydrates")
                           //     {
                           //         FoodCarbs.setText(tokens[i + 1]);
                           //         i--;
                           //     }
                           // }
                           // FoodProtein.setText(nutritions.GetProtein());
                           // FoodFat.setText(nutritions.GetFat());
                           // FoodCarbs.setText(nutritions.GetCarbs());
                        }
                    },
                    new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            //TestAPI.setText(error.getMessage());
                            //TestAPI.setText("Error, API is unavailable");
                        }
                    })
            {//no semicolon or coma
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("x-app-id", "5f43c2c7");
                    params.put("x-app-key","56752728f1bf936321dc126613dd2bac");
                    params.put("x-remote-user-id","0");
                    return params;
                }
            };

// Add the request to the RequestQueue.
            queue.add(stringRequest);
        }


    void GetNutritionWithoutID() throws IOException {
// ...

// Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);

        url ="https://trackapi.nutritionix.com/v2/natural/nutrients";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        String[] tokens = response.split(delims);
                        Gson gson = new Gson();
                        Nutrition nutritions = gson.fromJson(response, Nutrition.class);
                        for (int i = 0; i < response.length(); i = i + 2)
                        {
                            if (tokens[i] == "nf_calories")
                            {
                                FoodCalories.setText(tokens[i + 1]);
                                i--;
                            }
                            else if (tokens[i] == "nf_protein")
                            {
                                FoodProtein.setText(tokens[i + 1]);
                                i--;
                            }
                            else if (tokens[i] == "nf_total_fat")
                            {
                                FoodFat.setText(tokens[i + 1]);
                                i--;
                            }
                            else if (tokens[i] == "nf_total_carbohydrates")
                            {
                                FoodCarbs.setText(tokens[i + 1]);
                                i--;
                            }
                        }
                         FoodProtein.setText(nutritions.GetProtein());
                         FoodFat.setText(nutritions.GetFat());
                         FoodCarbs.setText(nutritions.GetCarbs());
                    }
                },
                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //TestAPI.setText(error.getMessage());
                        //TestAPI.setText("Error, API is unavailable");
                    }
                })
        {//no semicolon or coma
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("x-app-id", "5f43c2c7");
                params.put("x-app-key","56752728f1bf936321dc126613dd2bac");
                params.put("x-remote-user-id","0");
                return params;
            }
        };

// Add the request to the RequestQueue.
        queue.add(stringRequest);

    }
    // HTTP Post request
     void sendingPostRequest() throws Exception {

        String url = "https://trackapi.nutritionix.com/v2/natural/nutrients";
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        // Setting basic post request
        con.setRequestMethod("POST");
        con.setRequestProperty("x-app-id", "5f43c2c7");
        con.setRequestProperty("x-app-key","56752728f1bf936321dc126613dd2bac");
        con.setRequestProperty("x-remote-user-id","0");
        con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
        con.setRequestProperty("Content-Type","application/json");

        String postJsonData = "{query:\"1 cup chicken noodle soup\"}";

        // Send post request
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(postJsonData);
        wr.flush();
        wr.close();

        int responseCode = con.getResponseCode();
        FoodFat.setText("nSending 'POST' request to URL : " + url);
        FoodProtein.setText("Post Data : " + postJsonData);
        FoodCarbs.setText("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String output;
        StringBuffer response = new StringBuffer();

        while ((output = in.readLine()) != null) {
            response.append(output);
        }
        in.close();

        //printing result from response
        FoodCalories.setText(response.toString());
    }



    public void addone(){
        quantity += 0.25;
        quantityLabel.setText(quantity+"");
    }

    public void subtractone(){
        quantity -= 0.25;
        quantityLabel.setText(quantity+"");
    }
    }


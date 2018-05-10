package com.Diatrack.Activities;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.Diatrack.Classes.FoodSearch;
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

import java.io.IOException;
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
    int id;
    String name;
    TextView quantityLabel;
    TextView Foodlabel;
    TextView FoodCalories;
    TextView FoodProtein;
    TextView FoodFat;
    TextView FoodCarbs;
    String url ="https://trackapi.nutritionix.com/v2/search/item?nix_item_id=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected__food);
        Bundle bundle = getIntent().getExtras();
        id = bundle.getInt("Id");
        name = bundle.getString("Name");

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
    void GetNutrition() throws IOException {
// ...

// Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,

                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Gson gson = new Gson();
                        Nutrition[] nutritions = gson.fromJson(response, Nutrition[].class);
                        
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
    private void foodNutrition() throws IOException
    {
        url ="https://trackapi.nutritionix.com/v2/search/item?nix_item_id=";
        TextView ntext = (TextView) findViewById(com.Diatrack.R.id.instantSearch);
        if (ntext.length() >= 3) {
            url = url + id;
            try {
                   GetNutrition();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public void ConfigureAndInstall()
    {
        Button Meal = (Button) findViewById(com.Diatrack.R.id.bt_meal);

        Meal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // startActivity(new Intent(MealActivity.this, MealDoneActivity.class));
                try {
                    foodNutrition();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });}

    public void addone(){
        quantity += 0.25;
        quantityLabel.setText(quantity+"");
    }

    public void subtractone(){
        quantity -= 0.25;
        quantityLabel.setText(quantity+"");
    }
    }


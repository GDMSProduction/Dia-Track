package com.Diatrack.Activities;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.Diatrack.Classes.Food;
import com.Diatrack.Classes.FoodNutritionSearch;
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
import java.util.HashMap;
import java.util.Map;

public class SelectedFoodActivity extends AppCompatActivity {

    float quantity = 1;
    int id =0;
    String name;
    TextView quantityLabel;
    TextView FoodLabel;
    TextView FoodCalories;
    TextView FoodProtein;
    TextView FoodFat;
    TextView FoodCarbs;
    String url ="https://trackapi.nutritionix.com/v2/natural/nutrients";
    String delims = "[,:]+";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected__food);
        Bundle bundle = getIntent().getExtras();
        id = bundle.getInt("Id");
        name = bundle.getString("Name");
        try {
           GetNutritionSearch();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Button save = findViewById(R.id.btn_save);
        quantityLabel =  findViewById(R.id.txt_quantity);
        FoodLabel = findViewById(R.id.foodName);
        FoodCalories = findViewById(R.id.txt_calories);
        FoodCarbs = findViewById(R.id.txt_carbs);
        FoodProtein = findViewById(R.id.txt_Protein);
        FoodFat = findViewById(R.id.txt_Fats);

        FoodLabel.setText("Loading");
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

    void GetNutritionSearch() throws IOException {

        RequestQueue queue = Volley.newRequestQueue(this);

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,

                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Gson gson = new Gson();
                        FoodNutritionSearch foodSearches = gson.fromJson(response, FoodNutritionSearch.class);
                        if (foodSearches.foods.length > 0) {
                         Food firstFood = foodSearches.foods[0];
                         updateViewInformation(firstFood);
                        }
                    }
                },
                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(),"API Error",Toast.LENGTH_SHORT).show();
                    }
                })
        {//no semicolon or coma
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("x-app-id", "5f43c2c7");
                params.put("x-app-key","56752728f1bf936321dc126613dd2bac");

                return params;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("query",name);

                return params;
            }

            @Override
            public String getBodyContentType() {
               //return super.getBodyContentType();
                return "application/x-www-form-urlencoded";
            }
        };

// Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    public void updateViewInformation(Food foodNutrition) {
        FoodLabel.setText(foodNutrition.food_name);
        FoodCarbs.setText(foodNutrition.nf_total_carbohydrate+"");
        FoodCalories.setText(foodNutrition.nf_calories+"");
        FoodProtein.setText(foodNutrition.nf_protein+"");
        FoodFat.setText(foodNutrition.nf_total_fat+"");
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


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
    TextView FoodUnits;
    TextView FoodQuantity;
    double totalCarbs;
    double totalFats;
    double totalCalories;
    double totalProtein;
    String url ="https://trackapi.nutritionix.com/v2/natural/nutrients";
    String delims = "[,:]+";
    Food firstFood;
double calories;
double carbs;
double protein;
double fats;
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
        FoodUnits = findViewById(R.id.tv_units);
        FoodQuantity = findViewById(R.id.tv_quantity);

        FoodLabel.setText("Loading");

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //totalCarbs = Double.parseDouble(FoodCarbs.getText().toString());
                //totalCalories = Double.parseDouble(FoodCalories.getText().toString());
                //totalFats = Double.parseDouble(FoodFat.getText().toString());
                //totalProtein = Double.parseDouble(FoodProtein.getText().toString());
                Intent intent = new Intent(SelectedFoodActivity.this, MealActivity.class);
                Bundle dataMap = new Bundle();
                dataMap.putDouble("totalcarbs", totalCarbs);
                dataMap.putDouble("totalcalories", totalCalories);
                dataMap.putDouble("totalfats", totalFats);
                dataMap.putDouble("totalprotein", totalProtein);
                intent.putExtras(dataMap);
                startActivity(intent);


            }
        });

        FloatingActionButton up = findViewById(R.id.btn_up);
        FloatingActionButton down = findViewById(R.id.btn_down);
        up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addone(firstFood);
            }
        });
        down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (quantity >0 )
                subtractone(firstFood);
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
                         firstFood = foodSearches.foods[0];
                         updateViewInformation(firstFood);
                         UpdateFood(firstFood);
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
        FoodQuantity.setText(foodNutrition.serving_qty+" ");
        FoodUnits.setText(foodNutrition.serving_unit+"");
        totalCarbs = foodNutrition.nf_total_carbohydrate;
    }
public void UpdateFood(Food foodNutrition)
{
    carbs =foodNutrition.nf_total_carbohydrate;
    totalCarbs = totalCarbs + foodNutrition.nf_total_carbohydrate;
    calories = foodNutrition.nf_calories;
    totalCalories = totalCalories + foodNutrition.nf_calories;
    protein = foodNutrition.nf_protein;
    totalProtein = totalProtein + foodNutrition.nf_protein;
    fats =foodNutrition.nf_total_fat;
    totalFats = totalFats + foodNutrition.nf_total_fat;
}
    public void addone(Food foodNutrition){
        quantity += 0.25;
        quantityLabel.setText(quantity+"");
        foodNutrition.nf_total_carbohydrate = foodNutrition.nf_total_carbohydrate + (carbs * .25);
        foodNutrition.nf_calories = foodNutrition.nf_calories +(calories * .25);
        foodNutrition.nf_protein =  foodNutrition.nf_protein +(protein * .25);
        foodNutrition.nf_total_fat = foodNutrition.nf_total_fat + (fats * .25);
        updateViewInformation(foodNutrition);
    }

    public void subtractone(Food foodNutrition){
        quantity -= 0.25;
        quantityLabel.setText(quantity+"");
        foodNutrition.nf_total_carbohydrate = foodNutrition.nf_total_carbohydrate - (carbs * .25);
        foodNutrition.nf_calories = foodNutrition.nf_calories -(calories * .25);
        foodNutrition.nf_protein =  foodNutrition.nf_protein -(protein * .25);
        foodNutrition.nf_total_fat = foodNutrition.nf_total_fat - (fats * .25);
        totalCarbs =foodNutrition.nf_total_carbohydrate;
        updateViewInformation(foodNutrition);
    }
    }


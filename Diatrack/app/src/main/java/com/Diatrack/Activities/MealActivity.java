package com.Diatrack.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.Diatrack.Classes.FoodNutritionSearch;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.Diatrack.Classes.FoodSearch;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;

public class MealActivity extends AppCompatActivity {

    String url ="https://api.nutritionix.com/v2/autocomplete?q=";
    Timer timer = new Timer();
    MealItemsAdapter adapter;
    public String mealName = "";
    private FoodSearch selectedItem;
SelectedFoodActivity selectedFoodActivity = new SelectedFoodActivity();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.Diatrack.R.layout.activity_meal);
        ConfigureAndInstall();

    }

    public void ConfigureAndInstall()
    {
        Button Meal = findViewById(com.Diatrack.R.id.bt_meal);

        Meal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    foodSearch();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        CreateList();
        Button done = findViewById(com.Diatrack.R.id.bt_done);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MealActivity.this, SuggestedUnits.class));
              // try {
              //     //PostLog();
              // } catch (IOException e) {
              //     e.printStackTrace();
              // }
            }
        });
    }
//    void PostLog() throws IOException {
//
//        RequestQueue queue = Volley.newRequestQueue(this);
//
//        // Request a string response from the provided URL.
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
//
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        Gson gson = new Gson();
//                        FoodNutritionSearch foodSearches = gson.fromJson(response, FoodNutritionSearch.class);
//                        if (foodSearches.foods.length > 0) {
//
//                        }
//                    }
//                },
//                new Response.ErrorListener() {
//
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(getApplicationContext(),"API Error",Toast.LENGTH_SHORT).show();
//                    }
//                })
//        {//no semicolon or coma
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                Map<String, String> params = new HashMap<String, String>();
//                params.put("x-user-jwt", "0");
//
//                return params;
//            }
//
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> params = new HashMap<String, String>();
//                params.put("food object",selectedFoodActivity.FoodQuantity.toString());
//
//                return params;
//            }
//
//            @Override
//            public String getBodyContentType() {
//                //return super.getBodyContentType();
//                return "application/x-www-form-urlencoded";
//            }
//        };
//
//// Add the request to the RequestQueue.
//        queue.add(stringRequest);
//    }
    private void CreateList() {
        final ListView listView = findViewById(com.Diatrack.R.id.foodList);
        adapter = new MealItemsAdapter();
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                selectedItem = (FoodSearch) adapter.getItem(i);
                Intent intent = new Intent(MealActivity.this, SelectedFoodActivity.class);
                intent.putExtra("Id", selectedItem.GetId());
                intent.putExtra("Name", selectedItem.CreateFoodString());
                startActivity(intent);
            }
        });
    }


        void GetSearch() throws IOException {
// ...

// Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,

                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Gson gson = new Gson();
                        FoodSearch[] foodSearches = gson.fromJson(response, FoodSearch[].class);
                        ArrayList<FoodSearch> arrayList = new ArrayList<FoodSearch>(Arrays.asList(foodSearches));
                        adapter.updateResults(arrayList);
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
                params.put("x-remote-user-id","0");
                return params;
            }
        };

// Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

        private void foodSearch() throws IOException
        {
            https://trackapi.nutritionix.com/v2/search/item?nix_item_id=
            url ="https://api.nutritionix.com/v2/autocomplete?q=";
             TextView ntext = (TextView) findViewById(com.Diatrack.R.id.instantSearch);
            if (ntext.length() >= 3) {
                url = url + ntext.getText();
                try {
                    GetSearch();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(getApplicationContext(),"At least 3 characters",Toast.LENGTH_SHORT).show();
            }
    }

    class MealItemsAdapter extends BaseAdapter {

        private ArrayList<FoodSearch> foodItems;

        private MealItemsAdapter() {
            foodItems = new ArrayList<>();
        }

        @Override
        public int getCount() {
            return foodItems.size();
        }

        @Override
        public Object getItem(int i) {
            return foodItems.get(i);
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {

            if (view == null)
                view = getLayoutInflater().inflate(com.Diatrack.R.layout.meal_items_layout, null);

            TextView textView = view.findViewById(com.Diatrack.R.id.mealOptionName);
            String name = foodItems.get(i).CreateFoodString();
            textView.setText(name);

            return view;
        }

        public void updateResults(ArrayList<FoodSearch> results) {
            foodItems = results;
            //Triggers the list update
            notifyDataSetChanged();
        }

    }
}






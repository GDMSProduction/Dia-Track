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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.Diatrack.R.layout.activity_meal);
//        timer.schedule(new TimerTask() {
//            @Override
//            public void run() {
//                try {
//
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }, 0, 5000);

        ConfigureAndInstall();
        CreateList();
    }

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

        private void foodSearch() throws IOException
        {
            url ="https://api.nutritionix.com/v2/autocomplete?q=";
             TextView ntext = (TextView) findViewById(com.Diatrack.R.id.instantSearch);
            if (ntext.length() >= 3) {
                url = url + ntext.getText();
                try {
                    GetSearch();
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
                    foodSearch();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });}
}





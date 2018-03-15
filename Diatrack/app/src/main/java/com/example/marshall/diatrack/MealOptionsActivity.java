package com.example.marshall.diatrack;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MealOptionsActivity extends AppCompatActivity {

 int[] menuImages = {R.drawable.cup, R.drawable.chicken, R.drawable.chicken, R.drawable.cup};

 String[] menuNames = {"Breakfast", "Lunch", "Dinner", "Snack" };

 @Override
 protected void onCreate(Bundle savedInstanceState) {
     super.onCreate(savedInstanceState);
     setContentView(R.layout.activity_meal_options);
     CreateList();
     try {
         GetData();
     } catch (IOException e) {
         e.printStackTrace();
     }
 }

 private void CreateList()
 {
     ListView listView =(ListView)findViewById(R.id.listViewMealOptions);
     MealOptionsAdapter mealOptionsAdapter = new MealOptionsAdapter();
     listView.setAdapter(mealOptionsAdapter);
     listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
         @Override
         public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

             String item = (String) adapterView.getItemAtPosition(i);

             startActivity(new Intent(MealOptionsActivity.this, Meal.class));

         }
     });
 }


 class MealOptionsAdapter extends BaseAdapter
 {
     @Override
     public int getCount() {
         return menuImages.length;
     }

     @Override
     public Object getItem(int i) {
         return menuNames[i];
     }

     @Override
     public long getItemId(int i) {
         return 0;
     }

     @Override
     public View getView(int i, View view, ViewGroup viewGroup) {

         if (view == null)
            view = getLayoutInflater().inflate(R.layout.meal_options_layout, null);

         ImageView imageView = (ImageView)view.findViewById(R.id.mealOptionImage);
         TextView textView = (TextView)view.findViewById(R.id.mealOptionName);

         imageView.setImageResource(menuImages[i]);
         textView.setText(menuNames[i]);

         return view;
     }
 }
    void GetData() throws IOException {
// ...
        final TextView TestAPI = (TextView)findViewById(R.id.TestAPI);
// Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="https://trackapi.nutritionix.com";

// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        TestAPI.setText(response);
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                TestAPI.setText("Error not loading");
            }
        });

// Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

}


package com.example.marshall.diatrack;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import android.text.TextWatcher;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class Meal extends AppCompatActivity {

    ArrayList<FoodItem> foodItems;
    List<String> instasearch = new ArrayList<String>();
    String url ="https://api.nutritionix.com/v2/autocomplete?q=";
    Timer timer = new Timer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                try {
                    foodSearch();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }, 0, 2000);
    }

  //  private void CreateList() {
  //      ListView listView = (ListView) findViewById(R.id.listViewMealItems);
  //      MealItemsAdapter mealItemsAdapter = new MealItemsAdapter();
  //      listView.setAdapter(mealItemsAdapter);
  //      listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
  //          @Override
  //          public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//
  //          }
  //      });
  //  }

    private void GetFoodItems() {
        foodItems = new ArrayList<FoodItem>();

        FoodItem cake = new FoodItem("Cake", "30");

        for (int i = 0; i < 500; i++) {
            foodItems.add(cake);
        }
    }


    class FoodItem {

        String name;
        String carbs;
        Integer protein;

        public FoodItem(String _name, String _carbs) {
            name = _name;
            carbs = _carbs;
            protein = 15;
        }

        public String CreateFoodString() {
            return name + "         C:" + carbs + " P:" + protein.toString();
        }

    }

    class MealItemsAdapter extends BaseAdapter {
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
                view = getLayoutInflater().inflate(R.layout.meal_items_layout, null);

            TextView textView = (TextView) view.findViewById(R.id.mealOptionName);
            String name = foodItems.get(i).CreateFoodString();
            textView.setText(name);

            return view;
        }


    }
    void GetSearch() throws IOException {
// ...
        //final TextView TestAPI = (TextView)findViewById(R.id.TestAPI);
// Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        final TextView TestAPI = (TextView)findViewById(R.id.testSearch);



// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,

                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                       // instasearch.add(response);
                        TestAPI.setText(response);
                    }
                },
                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        TestAPI.setText(error.getMessage());
                    }
                })
        {//no semicolon or coma
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("x-app-id", "90a86e7a");
                params.put("x-app-key","a67f56a1d1889766b8a7e8ca3186a57d");
                params.put("x-remote-user-id","0");
                return params;
            }
        };

// Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

        private void foodSearch() throws IOException {
            url ="https://api.nutritionix.com/v2/autocomplete?q=";
             TextView ntext = (TextView) findViewById(R.id.instantSearch);
            //  ListView listView = (ListView) findViewById(R.id.listViewMealItems);
            if (ntext.length() >= 3) {
                url = url + ntext.getText();
                try {
                    GetSearch();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                ArrayAdapter adapter = new ArrayAdapter<String>(this,
                        android.R.layout.simple_list_item_1,
                        instasearch);
                //    listView.setAdapter(adapter);
            }
        }
}





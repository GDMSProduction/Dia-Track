package com.example.marshall.diatrack;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class Meal extends AppCompatActivity {

    ArrayList<FoodItem> foodItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal);
        GetFoodItems();
        CreateList();
    }

    private void CreateList()
    {
        ListView listView =(ListView)findViewById(R.id.listViewMealItems);
        MealItemsAdapter mealItemsAdapter = new MealItemsAdapter();
        listView.setAdapter(mealItemsAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                String item = (String) adapterView.getItemAtPosition(i);



            }
        });
    }

    private void GetFoodItems()
    {
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

        public FoodItem(String _name, String _carbs)
        {
            name = _name;
            carbs = _carbs;
            protein = 15;
        }

        public String CreateFoodString()
        {
            return name + "         C:" + carbs + " P:" + protein.toString();
        }

    }

    class MealItemsAdapter extends BaseAdapter
    {
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

            TextView textView = (TextView)view.findViewById(R.id.mealOptionName);
            String name = foodItems.get(i).CreateFoodString();
            textView.setText(name);

            return view;
        }


    }
}





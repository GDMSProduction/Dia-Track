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

public class MealOptionsActivity extends AppCompatActivity {

 int[] menuImages = {R.drawable.cup, R.drawable.chicken, R.drawable.chicken, R.drawable.cup};

 String[] menuNames = {"Breakfast", "Lunch", "Dinner", "Snack" };

 @Override
 protected void onCreate(Bundle savedInstanceState) {
     super.onCreate(savedInstanceState);
     setContentView(R.layout.activity_meal_options);
     CreateList();
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
}


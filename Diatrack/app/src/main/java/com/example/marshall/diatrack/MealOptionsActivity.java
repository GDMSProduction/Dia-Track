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

public class MealOptionsActivity extends AppCompatActivity {

 int[] IMAGES = {R.drawable.cup, R.drawable.chicken, R.drawable.chicken, R.drawable.cup};

 String[] Names = {"Breakfast", "Lunch", "Dinner", "Snack" };

 @Override
 protected void onCreate(Bundle savedInstanceState) {
     super.onCreate(savedInstanceState);
     setContentView(R.layout.activity_meal_options);
     ListView listView =(ListView)findViewById(R.id.listview);

     MealOptionsAdapter mealOptionsAdapter = new MealOptionsAdapter();
     listView.setAdapter(mealOptionsAdapter);
     listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
         @Override
         public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

             //Get The Item Name
           String item = (String) adapterView.getItemAtPosition(i);
             //Switch

             //Starta Food List passando a Variavel que tem o nome breakfast.
         }
     });
 }

 class MealOptionsAdapter extends BaseAdapter
 {

     @Override
     public int getCount() {
         return IMAGES.length;
     }

     @Override
     public Object getItem(int i) {
         return Names[i];
     }

     @Override
     public long getItemId(int i) {
         return 0;
     }

     @Override
     public View getView(int i, View view, ViewGroup viewGroup) {

         if (view == null)
            view = getLayoutInflater().inflate(R.layout.meal_options_layout, null);

         ImageView imageView = (ImageView)view.findViewById(R.id.imageView3);
         TextView textView = (TextView)view.findViewById(R.id.textView_Meal);

         imageView.setImageResource(IMAGES[i]);
         textView.setText(Names[i]);

         return view;
     }
 }
}


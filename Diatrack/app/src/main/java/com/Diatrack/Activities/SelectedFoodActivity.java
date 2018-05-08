package com.Diatrack.Activities;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.Diatrack.R;

import static com.Diatrack.R.id.foodName;
import static com.Diatrack.R.id.txt_quantity;

public class SelectedFoodActivity extends AppCompatActivity {

    float quantity = 1;
    int id;
    String name;
    TextView quantityLabel;
    TextView Foodlabel;

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


    public void addone(){
        quantity += 0.25;
        quantityLabel.setText(quantity+"");
    }

    public void subtractone(){
        quantity -= 0.25;
        quantityLabel.setText(quantity+"");
    }
    }


package com.Diatrack.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.Diatrack.R;

import static com.Diatrack.R.id.txt_quantity;

public class SelectedFoodActivity extends AppCompatActivity {
    float numtest = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected__food);
        Button save = (Button) findViewById(R.id.btn_save);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SelectedFoodActivity.this, MealDoneActivity.class));
            }
        });}


    public void button(View view){
        switch(view.getId())
        {
            case R.id.btn_up:
                numtest+=1;
                TextView t = (TextView) findViewById(txt_quantity);
               // t.setText(toString(numtest));
                break;
            case R.id.btn_down:
                 t = (TextView) findViewById(txt_quantity);
                break;
        }
    }
    public void addone(View v){
        numtest += 0.25;
        TextView quantity = (TextView) findViewById(txt_quantity);
        quantity.setText(numtest+"");
    }

    public void subtractone(View v){ ;
        numtest -= 0.25;
        TextView quantity = (TextView) findViewById(txt_quantity);
        quantity.setText(numtest+"");
    }
    }


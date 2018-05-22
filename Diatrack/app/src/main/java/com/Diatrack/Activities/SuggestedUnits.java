package com.Diatrack.Activities;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.Diatrack.Activities.LoginActivity;
import com.Diatrack.R;

public class SuggestedUnits extends AppCompatActivity {

    float quantity = 1;
    TextView quantityLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggested_units);

        Button save = (Button) findViewById(R.id.btn_save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SuggestedUnits.this, HomeActivity.class));
            }
        });
        quantityLabel =  findViewById(R.id.Units);
        FloatingActionButton up = findViewById(R.id.bt_up);
        FloatingActionButton down = findViewById(R.id.bt_down);

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

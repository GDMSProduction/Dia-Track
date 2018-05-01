package com.Diatrack.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MealDoneActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.Diatrack.R.layout.activity_meal_done);
        ConfigureAndInstall();
    }

    public void ConfigureAndInstall()
    {
        Button back = (Button) findViewById(com.Diatrack.R.id.bt_back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MealDoneActivity.this, HomeActivity.class));
            }
        });}
}

package com.Diatrack.Activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.Diatrack.Activities.LoginActivity;
import com.Diatrack.Classes.Day;
import com.Diatrack.Classes.UserProfileData;
import com.Diatrack.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class SuggestedUnits extends AppCompatActivity {

    float quantity = 1;
    TextView quantityLabel;
    SelectedFoodActivity foodActivity = new SelectedFoodActivity();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    DocumentReference docRef;
    int sens;
    float insulin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggested_units);
        final Date now = new Date();
     //   setSens();
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

        //double totalCarbs = foodActivity.totalCarbs;
        //float in = (float) (foodActivity.totalCarbs / sens);
        quantityLabel.setText("4");
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
    public void setSens()
    {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        docRef = db.collection("UserData").document(user.getUid());
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        sens = (int)document.get("insulinsens");
                    }
                }
            }
        });
    }
}

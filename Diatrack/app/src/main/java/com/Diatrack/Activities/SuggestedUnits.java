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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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
    TextView totalCarbs;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    DocumentReference docRef;
    Date now = new Date();
    long sens;
    double allInsulin = 0;
    double insulin = 0;
    Date time;
    double protein = 0;
    double fats = 0;
    double carbs = 0;
    double calories = 0;
    double[] glucose;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggested_units);
        quantityLabel =  findViewById(R.id.Units);
        totalCarbs = findViewById(R.id.TotalCarbs);
        Bundle extras = getIntent().getExtras();
        final double allCarbs = extras.getDouble("totalcarbs");
        final double allProtein = extras.getDouble("totalprotein");
        final double allFats = extras.getDouble("totalfats");
        final double allCalories = extras.getDouble("totalcalories");
        totalCarbs.setText(allCarbs+"");
        final Date now = new Date();
        docRef = db.collection("UserData").document(user.getUid());
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        document.getData();
                       long sens = (long) document.get("insulinsens");
                        quantity = (float) (allCarbs / sens);
                        quantityLabel.setText(quantity+"");
                    }
                }
            }
        });
        Button save = (Button) findViewById(R.id.btn_save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                docRef = db.collection("UserDailyIntake").document(user.getUid() + now.getDay());
                docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                document.getData();
                                if (document.get("insulin") != null)
                                {
                                    insulin = (double) document.get("insulin");
                                }
                                }
                                else if (document.get("protein")!= null)
                                {
                                    protein = (double) document.get("protein");
                                }
                                else if (document.get("fats")!= null)
                                {
                                    fats = (double) document.get("fats");
                                }
                                else if (document.get("carbs")!= null)
                                {
                                    carbs = (double) document.get("carbs");
                                }
                                else if (document.get("calories")!= null)
                                {
                                    calories = (double) document.get("calories");
                                }
                        }
                        if (true)
                        {
                            protein = protein + allProtein;
                            carbs = carbs + allCarbs;
                            fats = fats + allFats;
                            calories = calories + allCalories;
                            insulin = insulin + allInsulin;
                            final Map<String, Object> dailyintake = new HashMap<>();
                            dailyintake.put("calories", calories);
                            dailyintake.put("fats", fats);
                            dailyintake.put("carbs", carbs);
                            dailyintake.put("protein", protein);
                            dailyintake.put("insulin",insulin);
                            db.collection("UserDailyIntake").document(user.getUid() + now.getDay()).update(dailyintake).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    startActivity(new Intent(SuggestedUnits.this, HomeActivity.class));

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    System.out.println(e.getMessage());
                                }
                            });
                        }
                    }
                });
                //startActivity(new Intent(SuggestedUnits.this, HomeActivity.class));
            }
        });
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

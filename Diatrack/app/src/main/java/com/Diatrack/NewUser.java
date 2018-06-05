package com.Diatrack;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.Diatrack.Activities.HomeActivity;
import com.Diatrack.Activities.LoginActivity;
import com.android.volley.AuthFailureError;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import static com.Diatrack.R.id.num_Age;
import static com.Diatrack.R.id.num_InsulinSens;
import static com.Diatrack.R.id.num_MaxBlood;
import static com.Diatrack.R.id.num_MinBlood;
import static com.Diatrack.R.id.num_Target;
import static com.Diatrack.R.id.num_Weight;
import static com.Diatrack.R.id.num_Type;
import static com.Diatrack.R.id.txt_Weight;
import static com.Diatrack.R.id.txt_usergender;
import static com.Diatrack.R.id.txt_Height;


public class NewUser extends AppCompatActivity {
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    TextView uGender;
    TextView uAge;
    TextView uHeight;
    TextView uWeight;
    TextView uMaxBlood;
    TextView uMinBlood;
    TextView uTargetBlood;
    TextView uInsulinSens;
    TextView uType;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       uGender = findViewById(R.id.txt_usergender);
       uAge = findViewById(R.id.num_Age);
       uHeight = findViewById(R.id.txt_Height);
       uWeight = findViewById(R.id.num_Weight);
       uMaxBlood = findViewById(R.id.num_MaxBlood);
       uMinBlood = findViewById(R.id.num_MinBlood);
       uTargetBlood = findViewById(R.id.num_Target);
       uInsulinSens = findViewById(R.id.num_InsulinSens);
       uType = findViewById(R.id.num_Type);
        final Map<String, Object> userInfo = new HashMap<>();
       //userInfo.put("gender", uGender.getText());
       //userInfo.put("age", Integer.parseInt(uAge.getText().toString()));
       //userInfo.put("height", uHeight.getText());
       //userInfo.put("weight", Integer.parseInt(uWeight.getText().toString()));
       //userInfo.put("maxblood", Integer.parseInt(uMaxBlood.getText().toString()));
       //userInfo.put("minblood", Integer.parseInt(uMinBlood.getText().toString()));
       //userInfo.put("targetblood", Integer.parseInt(uTargetBlood.getText().toString()));
       //userInfo.put("insulinsens", Integer.parseInt(uInsulinSens.getText().toString()));
       //userInfo.put("type", Integer.parseInt(uType.getText().toString()));
        setContentView(R.layout.activity_new_user);
       // db.getReference();
        Button Save = findViewById(R.id.btn_SaveProfile);
        Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.collection("UserData").document(user.getUid()).set(userInfo).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        startActivity(new Intent(NewUser.this, HomeActivity.class));

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        System.out.println(e.getMessage());
                    }
                });

            }
        });

    }
}

package com.Diatrack.Activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.Diatrack.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


public class NewUser extends AppCompatActivity {
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    TextView uAge;
    TextView uHeight;
    TextView uWeight;
    TextView uMaxBlood;
    TextView uMinBlood;
    TextView uTargetBlood;
    TextView uInsulinSens;
    String[] itemsType;
    String[] itemsGender;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user);
       uAge = findViewById(R.id.num_Age);
       uHeight = findViewById(R.id.txt_Height);
       uWeight = findViewById(R.id.num_Weight);
       uMaxBlood = findViewById(R.id.num_MaxBlood);
       uMinBlood = findViewById(R.id.num_MinBlood);
       uTargetBlood = findViewById(R.id.num_Target);
       uInsulinSens = findViewById(R.id.num_InsulinSens);

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

       // db.getReference();
        spinnerCreater();
        Button save =  findViewById(R.id.btn_SaveProfile);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(NewUser.this, HomeActivity.class));
            }
        });
    }
    protected void spinnerCreater(){
        //get the spinner from the xml.
        Spinner dropdownType = findViewById(R.id.spinnerType);
        Spinner dropdownGender = findViewById(R.id.spinnerGender);
//create a list of items for the spinner.
        itemsType = new String[]{"Type 1", "Type 2", "Gestational", "Prediabetes"};
        itemsGender = new String[]{"Female", "Male", "Prefer not identify"};
//create an adapter to describe how the items are displayed, adapters are used in several places in android.
//There are multiple variations of this, but this is the basic variant.
        ArrayAdapter<String> adapterType = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, itemsType);
        ArrayAdapter<String> adapterGender = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, itemsGender);
//set the spinners adapter to the previously created one.
        dropdownType.setAdapter(adapterType);
        dropdownGender.setAdapter(adapterGender);
    }
}

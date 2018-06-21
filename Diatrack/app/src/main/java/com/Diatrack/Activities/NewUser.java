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
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import com.Diatrack.Activities.HomeActivity;
import com.Diatrack.Activities.LoginActivity;
import com.android.volley.AuthFailureError;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import static com.Diatrack.R.id.num_Age;
import static com.Diatrack.R.id.num_InsulinSens;
import static com.Diatrack.R.id.num_MaxBlood;
import static com.Diatrack.R.id.num_MinBlood;
import static com.Diatrack.R.id.num_Target;
import static com.Diatrack.R.id.num_Weight;

import static com.Diatrack.R.id.txt_Weightview;

import static com.Diatrack.R.id.txt_Height;


public class NewUser extends AppCompatActivity {
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    DocumentReference docRef = db.collection("UserData").document(user.getUid());
    TextView uGender;
    TextView uAge;
    TextView uHeight;
    TextView uWeight;
    TextView uMaxBlood;
    TextView uMinBlood;
    TextView uTargetBlood;
    TextView uInsulinSens;
    String[] itemsType;
    String[] itemsGender;
    String[] itemsHeight;


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

    }
    protected void spinnerCreater(){
        //get the spinner from the xml.
        final Spinner dropdownType = findViewById(R.id.spinnerType);
        final Spinner dropdownGender = findViewById(R.id.spinnerGender);
        final Spinner dropdownHeight = findViewById(R.id.spinnerHeight);
//create a list of items for the spinner.
        itemsType = new String[]{"Type 1", "Type 2", "Gestational", "Prediabetes"};
        itemsGender = new String[]{"Female", "Male", "Prefer not identify"};
        itemsHeight = new String[]{"4ft 0in", "4ft 1in","4ft 2in","4ft 3in","4ft 4in","4ft 5in","4ft 6in","4ft 7in","4ft 8in",
                "4ft 9in","4ft 10in","4ft 11in","5ft 0in","5ft 1in","5ft 2in","5ft 3in","5ft 4in","5ft 5in","5ft 6in","5ft 7in",
                "5ft 8in","5ft 9in","5ft 10in","5ft 11in","6ft 0in","6ft 1in","6ft 2in","6ft 3in","6ft 4in","6ft 5in","6ft 6in",
                "6ft 7in","6ft 8in","6ft 9in","6ft 10in","6ft 11in",};

//create an adapter to describe how the items are displayed, adapters are used in several places in android.
//There are multiple variations of this, but this is the basic variant.
        ArrayAdapter<String> adapterType = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, itemsType);
        ArrayAdapter<String> adapterGender = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, itemsGender);
        ArrayAdapter<String> adapterHeight = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, itemsHeight);
//set the spinners adapter to the previously created one.
        dropdownType.setAdapter(adapterType);
        dropdownGender.setAdapter(adapterGender);
        dropdownHeight.setAdapter(adapterHeight);

       // uGender = findViewById(R.id.txt_usergender);
        uAge = findViewById(R.id.num_Age);
        //uHeight = findViewById(R.id.num_Height);
        uWeight = findViewById(R.id.num_Weight);
        uMaxBlood = findViewById(R.id.num_MaxBlood);
        uMinBlood = findViewById(R.id.num_MinBlood);
        uTargetBlood = findViewById(R.id.num_Target);
        uInsulinSens = findViewById(R.id.num_InsulinSens);
       // uType = findViewById(R.id.num_Type);

        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        document.getData();
                        uAge.setText( document.get("age").toString());
                        uWeight.setText(document.get("weight").toString());
                       // uHeight.setText(document.get("height").toString());
                       // uGender.setText(document.get("gender").toString());
                        uInsulinSens.setText(document.get("insulinsens").toString());
                        uMaxBlood.setText(document.get("maxblood").toString());
                        uMinBlood.setText(document.get("minblood").toString());
                        uTargetBlood.setText(document.get("targetblood").toString());
                      //  uType.setText((Integer) document.get("type"));
                    }
                }
            }
        });

        Button Save = findViewById(R.id.btn_SaveProfile);
        Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Map<String, Object> userInfo = new HashMap<>();
                userInfo.put("gender", dropdownGender.getSelectedItem());
                userInfo.put("height", dropdownHeight.getSelectedItem());
                userInfo.put("type", dropdownType.getSelectedItem());
                if (uAge.getText() != null && !"".equals(uAge.getText().toString())) {
                    userInfo.put("age", Integer.parseInt(uAge.getText().toString()));
                }
                else{  Toast.makeText(getApplicationContext(),"Age is empty",Toast.LENGTH_LONG).show();}

                if (uWeight.getText() != null && !"".equals(uWeight.getText().toString())) {
                    userInfo.put("weight", Integer.parseInt(uWeight.getText().toString()));
                }
                else{  Toast.makeText(getApplicationContext(),"Weight is empty",Toast.LENGTH_LONG).show();}

                if (uMaxBlood.getText() != null && !"".equals(uMaxBlood.getText().toString())) {
                    userInfo.put("maxblood",Integer.parseInt(uMaxBlood.getText().toString()));
                }
                else{  Toast.makeText(getApplicationContext(),"Maximum Blood Sugar is empty",Toast.LENGTH_LONG).show();}

                if (uMinBlood.getText() != null && !"".equals(uMinBlood.getText().toString())) {
                    userInfo.put("minblood", Integer.parseInt(uMinBlood.getText().toString()));
                }
                else{  Toast.makeText(getApplicationContext(),"Minimum Blood Sugar is empty",Toast.LENGTH_LONG).show();}

                if (uTargetBlood.getText() != null && !"".equals(uTargetBlood.getText().toString())) {
                    userInfo.put("targetblood", Integer.parseInt(uTargetBlood.getText().toString()));
                }
                else{  Toast.makeText(getApplicationContext(),"Target Blood Sugar is empty",Toast.LENGTH_LONG).show();}

                if (uInsulinSens.getText() != null && !"".equals(uInsulinSens.getText().toString())) {
                    userInfo.put("insulinsens", Integer.parseInt(uInsulinSens.getText().toString()));
                }
                else{  Toast.makeText(getApplicationContext(),"Insulin Sensitivity is empty",Toast.LENGTH_LONG).show();}

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

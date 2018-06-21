package com.Diatrack.Activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class glycosenow extends AppCompatActivity {
    TextView glycose;
    double glycoselevel;
    Date now = new Date();
    List<Date> time = new ArrayList<>();
    List<Double> glycosel = new ArrayList<>();
    int length;
    Date currentTime = Calendar.getInstance().getTime();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    DocumentReference docRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_glycosenow);

        Button save = (Button) findViewById(R.id.btn_Done);
        glycose = findViewById(R.id.glycoselevel);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
        docRef = db.collection("UserDailyIntake").document(user.getUid() + now.getDay()+ now.getMonth() + now.getYear());
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (glycose.getText().toString() != null && !"".equals(glycose.getText().toString())){
                glycoselevel = Double.parseDouble(glycose.getText().toString());

                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        document.getData();
                            glycosel = (List<Double>) document.get("glycose");
                            length = glycosel.size();
                            glycosel.add(glycoselevel);
                            time = (List<Date>) document.get("time");
                            time.add(currentTime);
                    }
                    final Map<String, Object> dailyintake = new HashMap<>();
                    dailyintake.put("glycose", glycosel);
                    dailyintake.put("time", time);
                    db.collection("UserDailyIntake").document(user.getUid() + now.getDay()+ now.getMonth() + now.getYear()).set(dailyintake).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            startActivity(new Intent(glycosenow.this, HomeActivity.class));

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            System.out.println(e.getMessage());
                        }
                    });

            }
        }
            else{ Toast.makeText(getApplicationContext(),"Glycose is Empty",Toast.LENGTH_SHORT).show();}
            }
    });
}});
    }}
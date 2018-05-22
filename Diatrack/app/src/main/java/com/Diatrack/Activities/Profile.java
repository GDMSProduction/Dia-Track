package com.Diatrack.Activities;

import android.graphics.drawable.Drawable;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import static com.Diatrack.R.id.txt_Height;
import static com.Diatrack.R.id.txt_InsulinSen;
import static com.Diatrack.R.id.txt_MinBloodSugar;
import static com.Diatrack.R.id.txt_Username;
import static com.Diatrack.R.id.ProfilePicture;
import static com.Diatrack.R.id.txt_Weight;

import com.Diatrack.Activities.LoginActivity;
import com.Diatrack.R;

import org.w3c.dom.Text;

import java.io.InputStream;
import java.net.URL;

public class Profile extends AppCompatActivity {
    LoginActivity loginActivity = new LoginActivity();
    TextView usersname;
    String userImage;
    ImageView userImageView;
    int InsulinSens;
    int maxBlood;
    int minBlood;
    int weight;
    int height;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        usersname = findViewById(txt_Username);
        userImageView =findViewById(ProfilePicture);
        height = txt_Height;
        InsulinSens = txt_InsulinSen;
        maxBlood = txt_MinBloodSugar;
        minBlood = txt_MinBloodSugar;
        weight = txt_Weight;

        usersname.setText(loginActivity.UsersName);
        userImage = loginActivity.UsersPhoto;
            try {
                InputStream is = (InputStream) new URL(userImage).getContent();
                Drawable d = Drawable.createFromStream(is, "src name");
                userImageView.setImageDrawable(d);
            } catch (Exception e) {
            }
        }
    }


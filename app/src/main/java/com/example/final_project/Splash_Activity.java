package com.example.final_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.final_project.User.Login_User_Activity;

public class Splash_Activity extends AppCompatActivity {
    private final int SPLASH_DISPLAY_DURATION = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getSupportActionBar().hide();

        new Handler().postDelayed(() -> {
            Intent mainIntent = new Intent(Splash_Activity.this, Login_User_Activity.class);
            Splash_Activity.this.startActivity(mainIntent);
            Splash_Activity.this.finish();
        }, SPLASH_DISPLAY_DURATION);
    }
}
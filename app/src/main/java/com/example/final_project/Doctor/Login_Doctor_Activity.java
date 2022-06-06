package com.example.final_project.Doctor;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.final_project.R;
import com.google.firebase.auth.FirebaseAuth;

public class Login_Doctor_Activity extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_driver);
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Clinic App");
        firebaseAuth = FirebaseAuth.getInstance();

        final Button button = findViewById(R.id.BtnLoginDr);
        final TextView btnRegister = findViewById(R.id.btn_register);
        final EditText email = findViewById(R.id.EdEmail);
        final EditText passWord = findViewById(R.id.EdPassword);
        progressBar = findViewById(R.id.Progress);
        progressBar.setVisibility(View.GONE);

        button.setOnClickListener(v -> {
            if (TextUtils.isEmpty(email.getText().toString()) && TextUtils.isEmpty(passWord.getText().toString())) {
                Toast.makeText(Login_Doctor_Activity.this, "يرجى إدخال البيانات ", Toast.LENGTH_SHORT).show();
            } else {
                progressBar.setVisibility(View.VISIBLE);
                firebaseAuth.signInWithEmailAndPassword(email.getText().toString(), passWord.getText().toString())
                        .addOnCompleteListener(task -> {
                            progressBar.setVisibility(View.GONE);
                            if (task.isSuccessful()) {
                                Intent intent = new Intent(Login_Doctor_Activity.this, Doctor_Activity.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(Login_Doctor_Activity.this, "يرجى التأكد من صحة البيانات", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login_Doctor_Activity.this, Signup_Driver_Activity.class);
                startActivity(intent);
            }
        });
    }
}

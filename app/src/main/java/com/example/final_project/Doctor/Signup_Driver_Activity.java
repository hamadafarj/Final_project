package com.example.final_project.Doctor;

import androidx.annotation.NonNull;
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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Signup_Driver_Activity extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_driver);
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Clinic App");
        firebaseAuth = FirebaseAuth.getInstance();

        final TextView btnLogin = findViewById(R.id.btn_login);
        final Button button = findViewById(R.id.BtnLoginDr);
        final EditText email = findViewById(R.id.EdEmail);
        final EditText passWord = findViewById(R.id.EdPassword);
        final EditText rePassword = findViewById(R.id.EdRePassword);
        progressBar = findViewById(R.id.Progress);
        progressBar.setVisibility(View.GONE);

        button.setOnClickListener(v -> {
            if (TextUtils.isEmpty(email.getText().toString()) && TextUtils.isEmpty(passWord.getText().toString()) && TextUtils.isEmpty(rePassword.getText().toString())) {
                Toast.makeText(Signup_Driver_Activity.this, "يرجى إدخال البيانات ", Toast.LENGTH_SHORT).show();
            } else {
                if (passWord.getText().toString().equals(rePassword.getText().toString())) {
                    progressBar.setVisibility(View.VISIBLE);
                    Task<AuthResult> reasult = firebaseAuth.createUserWithEmailAndPassword(email.getText().toString(), passWord.getText().toString());
                    reasult.addOnSuccessListener(authResult -> {
                        progressBar.setVisibility(View.GONE);
                        Intent intent = new Intent(Signup_Driver_Activity.this, Doctor_Activity.class);
                        startActivity(intent);
                    });
                    reasult.addOnFailureListener(FailureListener);
                } else
                    Toast.makeText(Signup_Driver_Activity.this, "يرجى التأكد من مطابقة كلمة السر", Toast.LENGTH_SHORT).show();

            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Signup_Driver_Activity.this, Login_Doctor_Activity.class);
                startActivity(intent);
            }
        });

    }

    private OnFailureListener FailureListener = new OnFailureListener() {
        @Override
        public void onFailure(@NonNull Exception e) {
            progressBar.setVisibility(View.GONE);
            Toast.makeText(getApplicationContext(), "يرجى التأكد من صحة البيانات" + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
        }
    };
}



//                            .addOnCompleteListener(task -> {
//                                progressBar.setVisibility(View.GONE);
//                                if (task.isSuccessful()) {
//
//                                } else {
//                                    Toast.makeText(Signup_Driver_Activity.this, "يرجى التأكد من صحة البيانات", Toast.LENGTH_SHORT).show();
//                                }
//                            });
package com.example.final_project.User;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.final_project.Doctor.Doctor_Activity;
import com.example.final_project.Doctor.Login_Doctor_Activity;
import com.example.final_project.R;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class Login_User_Activity extends AppCompatActivity {

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private FirebaseAuth mAuth;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private String mVerificationId;
    private String PhoneNumber;
    ProgressDialog Loading;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Clinic App");
        setContentView(R.layout.activity_login_user);

        final Button button = findViewById(R.id.BtnLoginDr);
        final Button button2 = findViewById(R.id.BtnLoginConfirm);
        final TextView loginDr = findViewById(R.id.loginDr);
        final TextView textView = findViewById(R.id.editText2);
        final TextView textView2 = findViewById(R.id.textView3);
        final EditText mobile = findViewById(R.id.mobile);
        final EditText confirmMobile = findViewById(R.id.confirm_mobile);

        mAuth = FirebaseAuth.getInstance();
        Loading = new ProgressDialog(this);

        button.setOnClickListener(v -> {
            PhoneNumber = mobile.getText().toString();
            if (TextUtils.isEmpty(PhoneNumber)) {
                Toast.makeText(Login_User_Activity.this, "أدخل رقم الجوال بشكل صحيح ", Toast.LENGTH_SHORT).show();
            } else {
                Loading.setTitle("رمز التفعيل");
                Loading.setMessage("جاري ارسال رمز التفعيل ");
                Loading.setCanceledOnTouchOutside(false);
                Loading.show();

                mobile.setVisibility(View.GONE);
                textView.setVisibility(View.GONE);
                textView2.setVisibility(View.GONE);
                button.setVisibility(View.GONE);

                button2.setVisibility(View.VISIBLE);
                confirmMobile.setVisibility(View.VISIBLE);

                PhoneAuthProvider.getInstance().verifyPhoneNumber("+970" + PhoneNumber, 60, TimeUnit.SECONDS, Login_User_Activity.this, mCallbacks);
            }
        });

        button2.setOnClickListener(v -> {
            String code = confirmMobile.getText().toString();
            if (TextUtils.isEmpty(code) || code.length() < 6) {
                Toast.makeText(Login_User_Activity.this, "enter the code you hae it", Toast.LENGTH_SHORT).show();
                Loading.dismiss();
            } else {
                Loading.setTitle("رمز التفعيل");
                Loading.setMessage("جاري التأكد من صحة الرمز المدخل ");
                Loading.setCanceledOnTouchOutside(false);
                Loading.show();
                verifyCode(code);
            }
        });


        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                String code = phoneAuthCredential.getSmsCode();
                if (code != null) {
                    verifyCode(code);

                }
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                Loading.dismiss();
                Log.e("ttt", e.getLocalizedMessage());
                Log.e("ttt", e.getMessage());

                Toast.makeText(Login_User_Activity.this, "الرقم المدخل غير صحيح او محظور لفترة قصيرة", Toast.LENGTH_SHORT).show();

                mobile.setVisibility(View.VISIBLE);
                textView.setVisibility(View.VISIBLE);
                textView2.setVisibility(View.VISIBLE);
                button.setVisibility(View.VISIBLE);


                button2.setVisibility(View.GONE);
                confirmMobile.setVisibility(View.GONE);

            }

            public void onCodeSent(String verificationId, PhoneAuthProvider.ForceResendingToken token) {
                mVerificationId = verificationId;
                mResendToken = token;
                Loading.dismiss();
                Toast.makeText(Login_User_Activity.this, "تم ارسال الكود ..", Toast.LENGTH_SHORT).show();

                mobile.setVisibility(View.GONE);
                textView.setVisibility(View.GONE);
                textView2.setVisibility(View.GONE);
                button.setVisibility(View.GONE);
                button2.setVisibility(View.VISIBLE);
                confirmMobile.setVisibility(View.VISIBLE);
            }
        };

        loginDr.setOnClickListener(v -> {
            Intent intent = new Intent(Login_User_Activity.this, Login_Doctor_Activity.class);
            startActivity(intent);
        });
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        Loading.dismiss();
                        SendUSerTotTheNextActivity();
                    } else {
                        String massage = task.getException().toString();
                        Toast.makeText(Login_User_Activity.this, "رمز التفيعل المدخل غير صحيح يرجى ادخال الرمز بشكل صحيح", Toast.LENGTH_SHORT).show();
                        Loading.dismiss();
                    }
                });
    }
    private void SendUSerTotTheNextActivity() {
        Intent intent = new Intent(Login_User_Activity.this, User_Activity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
    private void verifyCode(String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, code);
        signInWithPhoneAuthCredential(credential);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (FirebaseAuth.getInstance().getCurrentUser()!= null) {
            int n = 0;
            if (FirebaseAuth.getInstance().getCurrentUser().getEmail().isEmpty()) {
                n = 2;
            }
            if (!FirebaseAuth.getInstance().getCurrentUser().getEmail().isEmpty()){
                n = 1;
            }
            switch (n){
                case 1:
                    Intent intent = new Intent(Login_User_Activity.this, Doctor_Activity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    break;
                case 2:
                    Intent intent1 = new Intent(Login_User_Activity.this, User_Activity.class);
                    intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent1);
                    break;
            }
        }
    }
}
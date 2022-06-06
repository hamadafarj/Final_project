package com.example.final_project.User;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.final_project.R;

public class User_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.logout);
        actionBar.setTitle("Clinic App");
        setContentView(R.layout.activity_user);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Toast.makeText(User_Activity.this, "رمز التفيعل المدخل غير صحيح يرجى ادخال الرمز بشكل صحيح", Toast.LENGTH_SHORT).show();
//                Intent i = new Intent(MainActivity.this,SecondActivity.class);
//                startActivity(i);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
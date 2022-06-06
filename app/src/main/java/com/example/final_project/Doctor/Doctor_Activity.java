package com.example.final_project.Doctor;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import com.example.final_project.R;
import com.example.final_project.User.Login_User_Activity;


public class Doctor_Activity extends AppCompatActivity {

//    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
//    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
//    private String email = firebaseAuth.getCurrentUser().getEmail();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.logout);
        actionBar.setTitle("Clinic App");
        setContentView(R.layout.activity_driver);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                showPopup();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void showPopup() {
        AlertDialog.Builder alert = new AlertDialog.Builder(Doctor_Activity.this);
        alert.setMessage("هل انت متاكد انك تريد الخروج ؟")
                .setPositiveButton("خروج", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        logout();
                    }
                }).setNegativeButton("الغاء", null);
        AlertDialog alert1 = alert.create();
        alert1.show();
    }
    private void logout() {
        startActivity(new Intent(Doctor_Activity.this, Login_User_Activity.class));
        finish();
    }
}
package com.example.signuploginrealtime;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class ProfileActivity extends AppCompatActivity {

   TextView profileName, profileEmail, profileStudentID, profilePassword;
   Button editProfile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        profileName = findViewById(R.id.profileName);
        profileEmail = findViewById(R.id.profileEmail);
        profileStudentID = findViewById(R.id.login_Student_ID);
        profilePassword = findViewById(R.id.profilePassword);
        editProfile = findViewById(R.id.editButton);

        showUSerData();

        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                passUserData();
            }
        });

    }

    public void showUSerData(){
        Intent intent = getIntent();

        String nameUser= intent.getStringExtra("name");
        String emailUser = intent.getStringExtra("email");
        String studentIDUser = intent.getStringExtra("student_ID");
        String passwordUser = intent.getStringExtra("password");

        profileName.setText(nameUser);
        profileEmail.setText(emailUser);
        profileStudentID.setText(studentIDUser);
        profilePassword.setText(passwordUser);

    }

    public void passUserData(){
        String userUsername = profileStudentID.getText().toString().trim();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
        Query checkUserDatabase = reference.orderByChild("studentID").equalTo(userUsername);

        checkUserDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.exists()){

                    String namefromDB = snapshot.child(userUsername).child("name").getValue(String.class);
                    String emailfromDB = snapshot.child(userUsername).child("email").getValue(String.class);
                    String studentIDfromDB = snapshot.child(userUsername).child("studentID").getValue(String.class);
                    String passwordfromDB = snapshot.child(userUsername).child("password").getValue(String.class);


                    Intent intent = new Intent(ProfileActivity.this, ProfileEditActivity.class);

                    intent.putExtra("name", namefromDB);
                    intent.putExtra("email", emailfromDB);
                    intent.putExtra("studentID",studentIDfromDB);
                    intent.putExtra("password",passwordfromDB);

                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
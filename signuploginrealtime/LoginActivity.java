package com.example.signuploginrealtime;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    EditText login_student_id, loginPassword;
    Button loginButton;
    TextView signupRedirectText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login_student_id = findViewById(R.id.login_Student_ID);
        loginPassword = findViewById(R.id.login_password);
        signupRedirectText = findViewById(R.id.signupRedirectText);
        loginButton = findViewById(R.id.login_button);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!validateStudentID() | !validatePassword()){

                }
                else {
                    checkUser();
                }
            }
        });

        signupRedirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });
    }
    public Boolean validateStudentID(){
        String val = login_student_id.getText().toString();

        if(val.isEmpty()){
            login_student_id.setError("Username cannot be empty");
            return false;
        }
        else{
            login_student_id.setError(null);
            return true;
        }
    }

    public Boolean validatePassword(){
        String val = loginPassword.getText().toString();

        if(val.isEmpty()){
            loginPassword.setError("Username cannot be empty");
            return false;
        }
        else{
            loginPassword.setError(null);
            return true;
        }
    }
    public void checkUser(){
        String userStudentID = login_student_id.getText().toString().trim();
        String userPassword = loginPassword.getText().toString().trim();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
        Query checkUserDatabase = reference.orderByChild("studentID").equalTo(userStudentID);

        checkUserDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    login_student_id.setError(null);
                    String passwordFromDB = snapshot.child(userStudentID).child("password").getValue(String.class);

                    if(passwordFromDB.equals(userPassword)){
                        login_student_id.setError(null);


                        //Pass the data using Intent
                        String nameFromDB = snapshot.child(userStudentID).child("name").getValue(String.class);
                        String emailFromDB = snapshot.child(userStudentID).child("email").getValue(String.class);
                        String studentIDFromDB = snapshot.child(userStudentID).child("studentID").getValue(String.class);



                        Intent tintent = new Intent(LoginActivity.this,ProfileActivity.class);

                        tintent.putExtra("name", nameFromDB);
                        tintent.putExtra("email",emailFromDB);
                        tintent.putExtra("studentID", studentIDFromDB);
                        tintent.putExtra("password",passwordFromDB);
                        startActivity(tintent);
                    } else {
                        loginPassword.setError("Invalid password");
                        login_student_id.requestFocus();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
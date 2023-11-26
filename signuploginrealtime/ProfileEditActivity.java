package com.example.signuploginrealtime;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import android.os.Bundle;

public class ProfileEditActivity extends AppCompatActivity {


    EditText editname, editemail, editstudentID, editPassword;
    Button saveButton;
    String nameUser, emailUser, studentIDUser, passwordUser;

    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_edit);

        reference = FirebaseDatabase.getInstance().getReference("users");

        editname = findViewById(R.id.editname);
        editemail = findViewById(R.id.editemail);
        editstudentID = findViewById(R.id.editStudent_ID);
        saveButton = findViewById(R.id.savebutton);

        showData();

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isNameChanges() || isEmailChanges() || isPasswordChanges()){
                    Toast.makeText(ProfileEditActivity.this, "Saved", Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(ProfileEditActivity.this, "No Changes Were Made", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    public boolean isNameChanges(){

        if(!nameUser.equals(editname.getText().toString())){
            reference.child(studentIDUser).child("name").setValue(editname.getText().toString());
            nameUser = editname.getText().toString();
            return true;
        }
        else{
            return false;
        }
    }

    public boolean isEmailChanges(){

        if(!emailUser.equals(editemail.getText().toString())){
            reference.child(studentIDUser).child("email").setValue(editemail.getText().toString());
            emailUser = editemail.getText().toString();
            return true;
        }
        else{
            return false;
        }
    }

    public boolean isPasswordChanges(){

        if(!passwordUser.equals(editPassword.getText().toString())){
            reference.child(studentIDUser).child("password").setValue(editPassword.getText().toString());
            passwordUser = editPassword.getText().toString();
            return true;
        }
        else{
            return false;
        }
    }

    public void showData(){
        Intent intent = getIntent();

        nameUser = intent.getStringExtra("name");
        emailUser = intent.getStringExtra("email");
        passwordUser = intent.getStringExtra("password");

        editname.setText(nameUser);
        editemail.setText(emailUser);
        editPassword.setText(passwordUser);
    }
}

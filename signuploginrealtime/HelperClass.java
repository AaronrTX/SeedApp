package com.example.signuploginrealtime;

public class HelperClass {
    String name,email,studentID, password;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public HelperClass(String name, String email, String studentID, String password) {
        this.name = name;
        this.email = email;
        this.studentID = studentID;
        this.password = password;
    }

    public HelperClass(){

    }
}

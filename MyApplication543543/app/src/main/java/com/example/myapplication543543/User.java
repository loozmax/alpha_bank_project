package com.example.myapplication543543;

public class User {
    public String fullName, age, email, phone,id;

    public User() {

    }

    public User(String fullName, String age, String email, String phone) {
        this.fullName = fullName;
        this.age = age;
        this.email = email;
        this.phone = phone;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}

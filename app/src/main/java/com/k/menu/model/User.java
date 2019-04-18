package com.k.menu.model;

import android.util.Patterns;

import com.k.menu.Signin;

/**
 * Created by Karthik on 26-12-2017.
 */

public class User {

    private String Phone;
    private String Name;
    private String Password;

    public User() {
    }

    public User(String phone, String name, String password) {
        Phone = phone;
        Name = name;
        Password = password;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }
}

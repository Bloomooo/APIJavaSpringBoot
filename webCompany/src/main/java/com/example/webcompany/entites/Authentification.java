package com.example.webcompany.entites;

public class Authentification {
    public String email;
    public String password;
    public boolean isFirstTime;

    public Authentification() {

    }

    public String getEmail() {
        return this.email;
    }

    public String getPassword() {
        return this.password;
    }

    public boolean isFirstTime() {
        return this.isFirstTime;
    }

}

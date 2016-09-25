package com.chickinnick.earnie.model;


import android.net.Uri;

public class User {

    private int earnieValue;

    private String name;
    private String surName;
    private String email;

    private Uri imageUri;

    public User(String firstName, String lastName, String email, Uri profilePictureUri) {
        this.name = firstName;
        this.surName = lastName;
        this.email = email;
        this.imageUri = profilePictureUri;
    }

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

    public String getSurName() {
        return surName;
    }

    public void setSurName(String surName) {
        this.surName = surName;
    }

    public Uri getImageUri() {
        return imageUri;
    }

    public void setImageUri(Uri imageUri) {
        this.imageUri = imageUri;
    }

    public int getEarnieValue() {
        return earnieValue;
    }

    public void setEarnieValue(int earnieValue) {
        this.earnieValue = earnieValue;
    }

    public void incrementEarnie() {
        ++earnieValue;
    }
}
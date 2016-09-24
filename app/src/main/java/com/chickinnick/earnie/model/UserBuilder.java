package com.chickinnick.earnie.model;

import android.net.Uri;

public class UserBuilder {
    private String firstName;
    private String lastName;
    private String email;
    private Uri profilePictureUri;

    public UserBuilder setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public UserBuilder setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public UserBuilder setEmail(String email) {
        this.email = email;
        return this;
    }

    public UserBuilder setProfilePictureUri(Uri profilePictureUri) {
        this.profilePictureUri = profilePictureUri;
        return this;
    }

    public User createUser() {
        return new User(firstName, lastName, email, profilePictureUri);
    }
}
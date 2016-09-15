package com.chickinnick.earnie.enter;

public interface FragmentActionListener {
    void onGoSignUp();

    void onSignUp();

    void onLogIn(String email, String password);

    void onLogInFB();

    void onLogInGoogle();

    void onGoReadTerms();
}
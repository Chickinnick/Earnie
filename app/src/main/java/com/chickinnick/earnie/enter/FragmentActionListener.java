package com.chickinnick.earnie.enter;

import com.chickinnick.earnie.model.User;

public interface FragmentActionListener {
    void onGoSignUp();

    void onSignUp(User user);

    void onLogIn(String email, String password);

    void onLogInFB();

    void onLogInGoogle();

    void onGoReadTerms();

    void onForgotPassword();
}
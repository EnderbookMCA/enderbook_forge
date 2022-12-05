package com.enderbook.forge.util;

import javax.swing.JOptionPane;

import okhttp3.Request;
import tudbut.tools.encryption.Key;

public class LoginManager {

    public final String user;
    private final Key tokenKey = new Key();
    private String token;

    private LoginManager(String user) {
        this.user = user;
    }

    public static LoginManager beginLogin(String user) {
        return new LoginManager(user);
    }

    public LoginManager getTokenFromUser() {
        this.token = tokenKey.encryptString( // we want to store the token encrypted so that it is invisible in the
                                             // config we save to disk
                JOptionPane.showInputDialog("Please paste your Enderbook token here to log in:"));
        return this;
    }

    // never give out the token. people could decrypt it anyways, but we want to
    // make it a little harder.
    public Request.Builder insertToken(Request.Builder builder) {
        return builder.addHeader("Authorization", "Bearer " + tokenKey.decryptString(token));
    }
}
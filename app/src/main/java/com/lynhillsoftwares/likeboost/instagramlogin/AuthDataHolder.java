package com.lynhillsoftwares.likeboost.instagramlogin;

/**
 * Created by Sushil Chhetri on 07,May,2021
 */
public class AuthDataHolder {
    private static final AuthDataHolder instance = new AuthDataHolder();

    public AuthData facebookAuthData;
    public AuthData googleAuthData;
    public AuthData twitterAuthData;
    public AuthData instagramAuthData;

    private AuthDataHolder() {
    }

    public static AuthDataHolder getInstance() {
        return instance;
    }
}
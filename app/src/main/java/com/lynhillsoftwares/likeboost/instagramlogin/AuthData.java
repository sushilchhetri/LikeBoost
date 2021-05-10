package com.lynhillsoftwares.likeboost.instagramlogin;
import com.lynhillsoftwares.likeboost.instagramlogin.callback.AuthCallback;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sushil Chhetri on 07,May,2021
 */
public class AuthData {
    private List<String> scopes;
    private AuthCallback callback;

    public AuthData(List<String> scopes, AuthCallback callback) {
        this.scopes = new ArrayList<>(scopes);
        this.callback = callback;
    }

    public List<String> getScopes() {
        return scopes;
    }

    AuthCallback getCallback() {
        return callback;
    }

    void clearCallback() {
        callback = null;
    }
}

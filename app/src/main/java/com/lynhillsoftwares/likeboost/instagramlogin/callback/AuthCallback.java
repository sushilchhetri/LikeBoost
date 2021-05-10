package com.lynhillsoftwares.likeboost.instagramlogin.callback;


import com.lynhillsoftwares.likeboost.instagramlogin.pojo.SocialUser;

/**
 * Created by Sushil Chhetri on 07,May,2021
 */
public interface AuthCallback {
    void onSuccess(SocialUser socialUser);

    void onError(Throwable error);

    void onCancel();
}

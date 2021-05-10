package com.lynhillsoftwares.likeboost.instagramlogin;

import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;


import com.lynhillsoftwares.likeboost.instagramlogin.callback.AuthCallback;

import java.util.List;

/**
 * Created by Sushil Chhetri on 07,May,2021
 */
public class SimpleAuth {

    public static final String TAG = SimpleAuth.class.getSimpleName();

    public static void connectInstagram(Context context, List<String> scopes, AuthCallback listener){
        Log.e(TAG, "connectInstagram: "+scopes );
        AuthDataHolder.getInstance().instagramAuthData = new AuthData(scopes,listener);
        instaAuthActivity.start(context);
    }

    public static void disconnectInstagram(Context context){
        AuthDataHolder.getInstance().instagramAuthData = null;
        clearCookies(context);
    }

    public static void clearCookies(Context context) {
        if (Build.VERSION.SDK_INT >= 21) {
            CookieManager.getInstance().removeAllCookies(null);
        } else {
            CookieSyncManager.createInstance(context.getApplicationContext());
            CookieManager.getInstance().removeAllCookie();
        }
    }
}

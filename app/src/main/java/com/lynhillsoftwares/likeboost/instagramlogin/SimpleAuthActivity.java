package com.lynhillsoftwares.likeboost.instagramlogin;

import android.os.Bundle;
import android.view.Window;

import androidx.appcompat.app.AppCompatActivity;

import com.lynhillsoftwares.likeboost.instagramlogin.pojo.SocialUser;


/**
 * Created by Sushil Chhetri on 07,May,2021
 */
public abstract class SimpleAuthActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
    }

    protected void handCancel() {
        getAuthData().getCallback().onCancel();
        finish();
    }

    protected void handleError(Throwable error) {
        getAuthData().getCallback().onError(error);
        finish();
    }

    protected void handleSuccess(SocialUser user) {
        getAuthData().getCallback().onSuccess(user);
        finish();
    }

    protected abstract AuthData getAuthData();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getAuthData().clearCallback();
    }

}

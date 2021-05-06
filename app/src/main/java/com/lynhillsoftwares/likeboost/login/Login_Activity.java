package com.lynhillsoftwares.likeboost.login;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.jaychang.sa.AuthCallback;
import com.jaychang.sa.SocialUser;
import com.jaychang.sa.instagram.SimpleAuth;
import com.lynhillsoftwares.likeboost.databinding.ActivityLoginBinding;
import com.lynhillsoftwares.likeboost.ui.activity.BaseActivity;

import java.util.Arrays;
import java.util.List;

public class Login_Activity extends BaseActivity {

    private static final String TAG = Login_Activity.class.getSimpleName();
    /*TODO ViewBinding*/
    private ActivityLoginBinding vb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        vb = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(vb.getRoot());

    }



    /*TODO init facebook login button click*/
    public void login(View view) {

        connectToInstagram();

    }

    void connectToInstagram() {

        List<String> scopes = Arrays.asList("Email");

        SimpleAuth.connectInstagram(scopes, new AuthCallback() {
            @Override
            public void onSuccess(SocialUser socialUser) {

                Log.e(TAG, "userId:" + socialUser.userId);
                Log.e(TAG, "email:" + socialUser.email);
                Log.e(TAG, "accessToken:" + socialUser.accessToken);

            }

            @Override
            public void onError(Throwable error) {
                Log.d(TAG, error.getMessage());
            }

            @Override
            public void onCancel() {
                Log.d(TAG, "Canceled");
            }
        });
    }


}
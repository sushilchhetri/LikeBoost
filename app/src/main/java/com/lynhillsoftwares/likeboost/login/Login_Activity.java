package com.lynhillsoftwares.likeboost.login;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;


import androidx.appcompat.app.AlertDialog;

import com.lynhillsoftwares.likeboost.databinding.ActivityLoginBinding;
import com.lynhillsoftwares.likeboost.instagramlogin.SimpleAuth;
import com.lynhillsoftwares.likeboost.instagramlogin.callback.AuthCallback;
import com.lynhillsoftwares.likeboost.instagramlogin.pojo.SocialUser;
import com.lynhillsoftwares.likeboost.ui.activity.BaseActivity;
import com.lynhillsoftwares.likeboost.ui.activity.HomeActivity;
import com.lynhillsoftwares.likeboost.utils.Constant;

import java.util.Arrays;
import java.util.List;

import io.paperdb.Paper;

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



    /*TODO Connect To Instagram */
    void connectToInstagram() {

        List<String> scopes = Arrays.asList("user_profile", "user_media");

        SimpleAuth.connectInstagram(this, scopes, new AuthCallback() {
            @Override
            public void onSuccess(SocialUser socialUser) {
                Log.e(TAG, "onSuccess:@@@@@@@@@@@@@@@@ " + socialUser);

                /*TODO save UserDetails in paper*/
                saveUserDetails(socialUser);

                /*TODO onsuccessfully fetching data move to Login Activity*/
                HomeActivity.startActivity(Login_Activity.this);

            }

            @Override
            public void onError(Throwable error) {
                Log.e(TAG, error.getMessage() + "   error*************");
            }

            @Override
            public void onCancel() {
                Log.d(TAG, "Canceled");
            }
        });


    }


    /*TODO save UserDetails in paper*/
    private void saveUserDetails(SocialUser socialUser) {

        Paper.book().write(Constant.LOGINUSER,socialUser);

    }


}
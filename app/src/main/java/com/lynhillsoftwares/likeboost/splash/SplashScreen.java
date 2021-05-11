package com.lynhillsoftwares.likeboost.splash;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.lynhillsoftwares.likeboost.R;
import com.lynhillsoftwares.likeboost.databinding.ActivitySplashScreenBinding;
import com.lynhillsoftwares.likeboost.loadingActivity.LoadingActivity;
import com.lynhillsoftwares.likeboost.login.Login_Activity;
import com.lynhillsoftwares.likeboost.ui.activity.BaseActivity;
import com.lynhillsoftwares.likeboost.utils.Constant;

import io.paperdb.Paper;

public class SplashScreen extends BaseActivity {

    private static final String TAG = SplashScreen.class.getSimpleName();
    private static final int SPLASH_DELAY = 2000;

    /*TODO View binding */
    private ActivitySplashScreenBinding vb;
    private Animation fromsmall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fromsmall = AnimationUtils.loadAnimation(this, R.anim.fromsmall);

        vb = ActivitySplashScreenBinding.inflate(getLayoutInflater());
        setContentView(vb.getRoot());
        /*TODO TO make status bar white*/
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        getWindow().setStatusBarColor(Color.WHITE);

        vb.imgFL.setAlpha(1);
        vb.imgFL.startAnimation(fromsmall);


        /*TODO Check for login User*/
        checkForLoginUser();


    }

    /*TODO Check for login User*/
    private void checkForLoginUser() {

        new Handler(getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {

                if(Paper.book().read(Constant.LOGINUSER) != null){

                   LoadingActivity.startActivity(SplashScreen.this);
                   finish();

                }else{

                    Login_Activity.startActivity(SplashScreen.this);
                    finish();
                }


            }
        }, SPLASH_DELAY);

    }
}
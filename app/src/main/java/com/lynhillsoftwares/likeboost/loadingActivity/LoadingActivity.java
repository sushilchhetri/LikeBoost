package com.lynhillsoftwares.likeboost.loadingActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.lynhillsoftwares.likeboost.R;
import com.lynhillsoftwares.likeboost.databinding.ActivityLoadingBinding;
import com.lynhillsoftwares.likeboost.login.Login_Activity;
import com.lynhillsoftwares.likeboost.splash.SplashScreen;
import com.lynhillsoftwares.likeboost.ui.activity.BaseActivity;
import com.lynhillsoftwares.likeboost.ui.activity.HomeActivity;
import com.lynhillsoftwares.likeboost.utils.Constant;

import io.paperdb.Paper;

public class LoadingActivity extends BaseActivity {


    /*TODO View Binding*/
    private ActivityLoadingBinding vb;
    private static final int SPLASH_DELAY = 2000;

    /*TODO start activity*/
    public static void startActivity(Activity activity){
        Intent intent = new Intent(activity,LoadingActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        vb = ActivityLoadingBinding.inflate(getLayoutInflater());
        setContentView(vb.getRoot());
        /*TODO TO make status bar white*/
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        getWindow().setStatusBarColor(Color.WHITE);


        rippleStart();
        new Handler(getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {

                /*TODO start HomeActivity Activity*/
                HomeActivity.startActivity(LoadingActivity.this);
                finish();

            }
        }, SPLASH_DELAY);
    }

    /*TODO RippleStart*/
    private void rippleStart() {
        vb.iconripple.startRippleAnimation();
    }

    /*TODO RippleStop*/
    private void rippleStop() {
        vb.iconripple.stopRippleAnimation();
    }

}
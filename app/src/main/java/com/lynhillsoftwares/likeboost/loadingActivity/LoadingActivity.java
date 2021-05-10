package com.lynhillsoftwares.likeboost.loadingActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.lynhillsoftwares.likeboost.R;
import com.lynhillsoftwares.likeboost.databinding.ActivityLoadingBinding;
import com.lynhillsoftwares.likeboost.ui.activity.BaseActivity;

public class LoadingActivity extends BaseActivity {


    /*TODO View Binding*/
    private ActivityLoadingBinding vb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        vb = ActivityLoadingBinding.inflate(getLayoutInflater());
        setContentView(vb.getRoot());

        rippleStart();
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